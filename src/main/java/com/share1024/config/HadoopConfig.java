package com.share1024.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.config.annotation.EnableHadoop;
import org.springframework.data.hadoop.config.annotation.SpringHadoopConfigurerAdapter;
import org.springframework.data.hadoop.config.annotation.builders.HadoopConfigConfigurer;

/**
 * @author : yesheng
 * @Description :
 * @Date : 2018-12-17
 */
@Configuration
@EnableHadoop
public class HadoopConfig extends SpringHadoopConfigurerAdapter {


    @Autowired
    private HadoopProperties hadoopProperties;

    @Override
    public void configure(HadoopConfigConfigurer config) throws Exception {
        config.fileSystemUri(hadoopProperties.getSystemUri());
    }
}
