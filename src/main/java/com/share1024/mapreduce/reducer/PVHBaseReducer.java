package com.share1024.mapreduce.reducer;

import com.share1024.model.LogBean;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;
import java.util.Iterator;

/**
 * @author : yesheng
 * @Description :
 * @Date : 2019-01-04
 */
public class PVHBaseReducer extends TableReducer<Text, LogBean, NullWritable> {

    @Override
    protected void reduce(Text key, Iterable<LogBean> values, Context context) throws IOException, InterruptedException {

        Iterator<LogBean> iterator = values.iterator();
        while(iterator.hasNext()){
            LogBean logBean = iterator.next();
            String rowKey = getRowKey(logBean.getRequestUrl(),logBean.getResponseTime(),logBean.getRealIp(),logBean.getResponseCode());
            Put put = new Put(Bytes.toBytes(rowKey));
            put.addColumn(Bytes.toBytes("data"),Bytes.toBytes("requestIp"),Bytes.toBytes(logBean.getRealIp()));
            put.addColumn(Bytes.toBytes("data"),Bytes.toBytes("url"),Bytes.toBytes(logBean.getRequestUrl()));
            put.addColumn(Bytes.toBytes("data"),Bytes.toBytes("responseCode"),Bytes.toBytes(logBean.getResponseCode()));
            put.addColumn(Bytes.toBytes("data"),Bytes.toBytes("responseTime"),Bytes.toBytes(logBean.getResponseTime()));
            context.write(NullWritable.get(), put);
        }
    }

    /**
     * hbase rowkey  url:requestIp:responseCode:responseTime
     * @param url
     * @param responseTime
     * @param requestIp
     * @param responseCode
     * @return
     */
    private String getRowKey(String url,long responseTime,String requestIp,int responseCode) {
        return String.join(":",url,requestIp,responseCode+"",responseTime+"");
    }

}
