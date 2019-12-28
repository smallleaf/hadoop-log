package com.share1024.mapreduce.reducer;

import com.share1024.model.LogBean;
import com.share1024.model.ResultStatus;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import javax.xml.soap.Text;
import java.io.IOException;
import java.util.Iterator;

/**
 * @author : yesheng
 * @Description :
 * @Date : 2018-12-17
 */
public class PVMysqlReducer extends Reducer<Text, LogBean, ResultStatus, NullWritable> {

    @Override
    protected void reduce(Text key, Iterable<LogBean> values, Context context) throws IOException, InterruptedException {

        int sum = 0;

        int sum_500 = 0;

        int sum_200 = 0;

        int sum_502 = 0;

        int sum_400 = 0;

        int sum_404 = 0;

        Iterator<LogBean> iterator = values.iterator();

        while(iterator.hasNext()){
            LogBean logBean = iterator.next();
            sum++;
            if(logBean.isValid()){
                if(500 == logBean.getResponseCode()){
                    sum_500 ++;
                }else if(502 == logBean.getResponseCode()){
                    sum_502 ++;
                }else if(200 == logBean.getResponseCode()){
                    sum_200 ++;
                }else if(400 == logBean.getResponseCode()){
                    sum_400++;
                }else if(404 == logBean.getResponseCode()){
                    sum_404 ++;
                }
            }
        }
        ResultStatus rs = new ResultStatus();
        rs.setSum(sum);
        rs.setSum_200(sum_200);
        rs.setSum_400(sum_400);
        rs.setSum_404(sum_404);
        rs.setSum_500(sum_500);
        rs.setSum_502(sum_502);

        String[] urlRequest = key.toString().split("_");
        rs.setUrl(urlRequest[0]);
        rs.setRequestTime(urlRequest[1]);

        /**
         * 输出最终是在mysql ，
         */
        context.write(rs,null);

    }


}
