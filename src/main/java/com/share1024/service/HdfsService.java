package com.share1024.service;

import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @author : yesheng
 * @Description : hdfs文件处理
 * @Date : 2019-01-14
 */
@Service
public class HdfsService {

    private Logger logger = LoggerFactory.getLogger(HdfsService.class);

    @Autowired
    private FileSystem fileSystem;

    /**
     * 检测是否存在路径
     * @param path
     * @return
     */
    public boolean existPath(String path){
        try {
           return fileSystem.exists(new Path(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean deletePath(String path){
        try {
            return fileSystem.delete(new Path(path),true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

}
