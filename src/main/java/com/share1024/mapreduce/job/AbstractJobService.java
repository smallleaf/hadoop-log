package com.share1024.mapreduce.job;

import com.share1024.config.HadoopProperties;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : yesheng
 * @Description :
 * @Date : 2019-01-14
 */
public abstract class AbstractJobService {

    @Autowired
    protected HadoopProperties hadoopProperties;


    public abstract void execute();

    public String getInputPath(){
        String suffix = "";
        if(hadoopProperties.isHasSuffix()){
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat(hadoopProperties.getHdfsSuffixRex());
            suffix = "/"+ dateFormat.format(date);
        }
        return hadoopProperties.getHdfsUrlPre() + suffix;
    }
}
