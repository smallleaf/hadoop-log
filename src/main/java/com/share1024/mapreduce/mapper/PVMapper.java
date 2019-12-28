package com.share1024.mapreduce.mapper;

import com.share1024.model.LogBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : yesheng
 * @Description :  统计每小时，key为请求地址+"_"+小时时间，value为解析的日志信息
 * @Date : 2018-12-17
 */
public class PVMapper extends Mapper<LongWritable,Text,Text, LogBean> {


    private static final String HOUR_FOMRT = "yyyy-MM-dd HH";

    SimpleDateFormat dateFormat = new SimpleDateFormat(HOUR_FOMRT);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //取出每一行数据
        String[] lines = value.toString().split("\n");
        for (String line : lines) {
            LogBean logBean = new LogBean(line);
            if(logBean.isValid()){
                context.write(getMapKey(logBean.getRequestUrl(),new Date(logBean.getDateTime())),logBean);
            }
        }
    }

    private Text getMapKey(String requestUrl,Date requestDate){
        return new Text(String.join("_",requestUrl,dateFormat.format(requestDate)));
    }
}
