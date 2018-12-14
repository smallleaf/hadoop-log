package com.share1024.model;


/**
 * @author : yesheng
 * @Description : 日志实体类，日志格式为
 * 78.101.219.93 10.10.18.93 - - [22/Jan/2018:00:00:00 +0800] GET /zcamera-api/api/v2/message/newCount?accountId= HTTP/1.1 200 79 -- 0
 *
 * @Date : 2018/1/23
 */
public class LogBean {


    /**
     * 真实请求的用户ip
     */
    private String realIp;

    /**
     * 请求来源的主机
     */
    private String remoteHost;

    /**
     * 请求时间
     */
    private long dateTime;

    /**
     * 请求类型
     */
    private String requestType;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 响应码
     */
    private int responseCode;

    /**
     * 返回的数据长度
     */
    private int sendLength;

    /**
     * 响应时间
     */
    private long responseTime;


}
