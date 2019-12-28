package com.share1024.service;

import com.share1024.mapreduce.job.LogMapreduceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author : yesheng
 * @Description :
 * @Date : 2018-12-17
 */
@Service
public class JobService {

    @Autowired
    private LogMapreduceService logJobService;

    @PostConstruct
    public void test(){
        logJobService.execute();
    }
}
