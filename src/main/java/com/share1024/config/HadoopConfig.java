package com.share1024.config;

import org.apache.hadoop.fs.FileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.hadoop.config.annotation.EnableHadoop;
import org.springframework.data.hadoop.config.annotation.SpringHadoopConfigurerAdapter;
import org.springframework.data.hadoop.config.annotation.builders.HadoopConfigConfigurer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

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


    @Bean
    public FileSystem fileSystem() throws URISyntaxException, IOException {
        System.setProperty("HADOOP_USER_NAME","yesheng") ;
        return FileSystem.get(new URI(hadoopProperties.getSystemUri()),new org.apache.hadoop.conf.Configuration());
    }
}
