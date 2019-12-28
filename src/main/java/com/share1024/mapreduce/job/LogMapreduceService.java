package com.share1024.mapreduce.job;

import com.share1024.mapreduce.mapper.PVMapper;
import com.share1024.mapreduce.reducer.PVReducer;
import com.share1024.service.HdfsService;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author : yesheng
 * @Description : 普通的mapreduce
 * @Date : 2019-01-14
 */
@Service
public class LogMapreduceService extends AbstractJobService{


    private static final String JOB_NAME = "pv_map";

    @Autowired
    private HdfsService hdfsService;

    private static final String path="/output";

    @Override
    public void execute() {
        init();
        try {
            Configuration configuration = new Configuration();
            Job job = Job.getInstance(configuration,JOB_NAME);
            job.setJarByClass(LogMapreduceService.class);
            job.setJobName("tomcat ip log job");
            FileInputFormat.addInputPath(job,new Path(getInputPath()));
            FileOutputFormat.setOutputPath(job,new Path(hadoopProperties.getSystemUri()+path));
            job.setMapperClass(PVMapper.class);
            job.setReducerClass(PVReducer.class);
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            job.submit();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void init(){
        hdfsService.deletePath(path);
    }
}
