package com.share1024.mapreduce.job;

import com.share1024.config.HadoopProperties;
import com.share1024.mapreduce.mapper.PVMapper;
import com.share1024.mapreduce.reducer.PVHBaseReducer;
import com.share1024.model.LogBean;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.mapreduce.TableOutputFormat;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : yesheng
 * @Description :
 * @Date : 2018-12-17
 */
@Service
public class LogHbaseJobService  extends AbstractJobService{

    private static final String JOB_NAME = "pv_hbase";

    private static final String LOG_TABLE = "t_log";

    @Autowired
    private HadoopProperties hadoopProperties;

    @Override
    public void execute(){

        try {
            Configuration configuration = new Configuration();
            Job job = Job.getInstance(configuration,JOB_NAME);
            configuration.set(TableOutputFormat.OUTPUT_TABLE,LOG_TABLE);
            job.setJarByClass(LogHbaseJobService.class);
            job.setMapperClass(PVMapper.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(LogBean.class);
            job.setReducerClass(PVHBaseReducer.class);
            job.setOutputKeyClass(NullWritable.class);
            job.setOutputFormatClass(TableOutputFormat.class);
            FileInputFormat.addInputPath(job, new Path(getInputPath()));
            job.submit();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
