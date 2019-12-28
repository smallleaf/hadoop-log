package com.share1024.config;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : yesheng
 * @Description : hbase配置
 * @Date : 2019-01-03
 */
@Configuration
public class HBaseConfig {

    @Autowired
    private HBaseProperties hBaseProperties;

    @Bean
    public org.apache.hadoop.conf.Configuration hBaseConfiguration(){
        org.apache.hadoop.conf.Configuration hBaseConfiguration = HBaseConfiguration.create();
        hBaseConfiguration.set(HConstants.ZOOKEEPER_QUORUM,hBaseProperties.getZkQuorum());
        hBaseConfiguration.set(HConstants.ZOOKEEPER_ZNODE_PARENT,hBaseProperties.getZnodeParent());
        hBaseConfiguration.set(HConstants.ZOOKEEPER_CLIENT_PORT,String.valueOf(hBaseProperties.getZkPort()));
        return hBaseConfiguration;
    }



}
