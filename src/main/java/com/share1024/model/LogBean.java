package com.share1024.model;


import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author : yesheng
 * @Description : 日志实体类，日志格式为
 * 78.101.219.93 10.10.18.93 - - [22/Jan/2018:00:00:00 +0800] GET /zcamera-api/api/v2/message/newCount?accountId= HTTP/1.1 200 79 -- 0
 *
 * @Date : 2018/1/23
 */
public class LogBean implements Writable {


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

    /**
     * 是否有效
     */
    private boolean valid;

    public LogBean(String line) {
        String[] array = line.split(" ");
        if(array.length == 13){
            this.realIp = array[0];
            this.remoteHost = array[1];

            try{
                SimpleDateFormat dateFormat = new SimpleDateFormat("[dd/MMM/yyyy:HH:mm:ss z]", Locale.ENGLISH);
                this.dateTime = dateFormat.parse(array[4]+" "+array[5]).getTime();
                this.requestType = array[6];
                this.requestUrl = array[7];
                if(this.requestUrl.contains("?")){
                    this.requestUrl = this.requestUrl.split("[?]")[0];
                }
                this.responseCode = Integer.valueOf(array[9]);
                this.sendLength = Integer.valueOf(array[10]);
                this.responseTime = Long.valueOf(array[12]);
            }catch (Exception e){
                this.valid = false;
            }
        }else{
            this.valid = false;
        }
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getRealIp() {
        return realIp;
    }

    public void setRealIp(String realIp) {
        this.realIp = realIp;
    }

    public String getRemoteHost() {
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost) {
        this.remoteHost = remoteHost;
    }

    public long getDateTime() {
        return dateTime;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public int getSendLength() {
        return sendLength;
    }

    public void setSendLength(int sendLength) {
        this.sendLength = sendLength;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(realIp);
        dataOutput.writeUTF(remoteHost);
        dataOutput.writeLong(dateTime);
        dataOutput.writeUTF(requestType);
        dataOutput.writeUTF(requestUrl);
        dataOutput.writeInt(responseCode);
        dataOutput.writeInt(sendLength);
        dataOutput.writeLong(responseTime);
        dataOutput.writeBoolean(valid);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.realIp = dataInput.readUTF();
        this.remoteHost = dataInput.readUTF();
        this.dateTime = dataInput.readLong();
        this.requestType = dataInput.readUTF();
        this.requestUrl = dataInput.readUTF();
        this.responseCode = dataInput.readInt();
        this.sendLength = dataInput.readInt();
        this.responseTime = dataInput.readLong();
        this.valid = dataInput.readBoolean();
    }
}
