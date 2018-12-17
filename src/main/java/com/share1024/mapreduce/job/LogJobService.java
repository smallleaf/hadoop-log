package com.share1024.mapreduce.job;

import com.share1024.config.HadoopProperties;
import com.share1024.mapreduce.mapper.PVMapper;
import com.share1024.mapreduce.reducer.PVReducer;
import com.share1024.model.LogBean;
import com.share1024.model.ResultStatus;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBOutputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : yesheng
 * @Description :
 * @Date : 2018-12-17
 */
@Service
public class LogJobService {

    private static final String JOB_NAME = "pv_statistic";

    private String driverClass;

    private String dbUrl;

    private String userName;

    private String passwd;

    @Autowired
    private HadoopProperties hadoopProperties;

    public void execute(){

        try {
            Configuration configuration = new Configuration();
            DBConfiguration.configureDB(configuration,driverClass,dbUrl,userName,passwd);
            Job job = Job.getInstance(configuration,JOB_NAME);
            job.setJarByClass(LogJobService.class);
            job.setMapperClass(PVMapper.class);
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(LogBean.class);
            job.setCombinerClass(PVReducer.class);
            job.setReducerClass(PVReducer.class);
            job.setOutputKeyClass(ResultStatus.class);
            job.setOutputValueClass(ResultStatus.class);
            job.setOutputFormatClass(DBOutputFormat.class);
            DBOutputFormat.setOutput(job,"log_pv","request_time","num","request_500","request_200",
                    "request_400","request_404","request_201","request_502");
            FileInputFormat.addInputPath(job, new Path(getInputPath()));
            job.submit();

        }catch (Exception e){

        }

    }

    private String getInputPath(){
        String suffix = "";
        if(hadoopProperties.isHasSuffix()){
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat(hadoopProperties.getHdfsSuffixRex());
            suffix = "/"+ dateFormat.format(date);
        }
        return hadoopProperties.getHdfsUrlPre() + suffix;
    }

}
