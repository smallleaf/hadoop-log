package com.share1024.mapreduce.job;

import com.share1024.mapreduce.mapper.PVMapper;
import com.share1024.mapreduce.reducer.PVReducer;
import com.share1024.model.ResultStatus;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Service;

/**
 * @author : yesheng
 * @Description :
 * @Date : 2018-12-17
 */
@Service
public class LogMysqlJobService extends AbstractJobService {

    private static final String JOB_NAME = "pv_statistic";

    @Autowired
    private DataSourceProperties dataSourceProperties;



    @Override
    public void execute(){

        try {
            Configuration configuration = new Configuration();
            DBConfiguration.configureDB(configuration,dataSourceProperties.getDriverClassName(),dataSourceProperties.getUrl(),dataSourceProperties.getName()
                    ,dataSourceProperties.getPassword());
            Job job = Job.getInstance(configuration,JOB_NAME);
            job.setJarByClass(LogMysqlJobService.class);
            job.setMapperClass(PVMapper.class);
            job.setCombinerClass(PVReducer.class);
            job.setReducerClass(PVReducer.class);
            job.setOutputKeyClass(ResultStatus.class);
            job.setOutputValueClass(ResultStatus.class);
            job.setOutputFormatClass(DBOutputFormat.class);
            DBOutputFormat.setOutput(job,"log_pv","requestTime","sum","sum_500","sum_400",
                    "sum_404","sum_502","url");
            FileInputFormat.addInputPath(job, new Path(getInputPath()));
            job.submit();

        }catch (Exception e){
            e.printStackTrace();
        }

    }


}
