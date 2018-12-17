package com.share1024.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : yesheng
 * @Description :
 * @Date : 2018-12-17
 */
@Component
@ConfigurationProperties("hadoop")
public class HadoopProperties {

    private String hdfsUrlPre;

    private String systemUri;

    /**
     * 是否有后缀
     */
    private boolean hasSuffix = false;

    private String hdfsSuffixRex = "%Y/%m/%D/%H";


    public boolean isHasSuffix() {
        return hasSuffix;
    }

    public void setHasSuffix(boolean hasSuffix) {
        this.hasSuffix = hasSuffix;
    }

    public String getSystemUri() {
        return systemUri;
    }

    public void setSystemUri(String systemUri) {
        this.systemUri = systemUri;
    }

    public String getHdfsUrlPre() {
        return hdfsUrlPre;
    }

    public void setHdfsUrlPre(String hdfsUrlPre) {
        this.hdfsUrlPre = hdfsUrlPre;
    }

    public String getHdfsSuffixRex() {
        return hdfsSuffixRex;
    }

    public void setHdfsSuffixRex(String hdfsSuffixRex) {
        this.hdfsSuffixRex = hdfsSuffixRex;
    }
}
