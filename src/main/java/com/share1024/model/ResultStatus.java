package com.share1024.model;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author : yesheng
 * @Description : 处理结果
 * @Date : 2018-12-17
 */
public class ResultStatus implements WritableComparable<ResultStatus>, Writable, DBWritable {

    /**
     * 请求时间格式 yyyy-MM-dd HH
     */
    private String requestTime;

    /**
     * 请求地址
     */
    private String url;

    /**
     * 请求总数
     */
    private int sum;

    private int sum_500;

    private int sum_200;

    private int sum_502;

    private int sum_400;

    private int sum_404;

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getSum() {
        return sum;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public int getSum_500() {
        return sum_500;
    }

    public void setSum_500(int sum_500) {
        this.sum_500 = sum_500;
    }

    public int getSum_200() {
        return sum_200;
    }

    public void setSum_200(int sum_200) {
        this.sum_200 = sum_200;
    }

    public int getSum_502() {
        return sum_502;
    }

    public void setSum_502(int sum_502) {
        this.sum_502 = sum_502;
    }

    public int getSum_400() {
        return sum_400;
    }

    public void setSum_400(int sum_400) {
        this.sum_400 = sum_400;
    }

    public int getSum_404() {
        return sum_404;
    }

    public void setSum_404(int sum_404) {
        this.sum_404 = sum_404;
    }

    @Override
    public int compareTo(ResultStatus o) {
        return this.requestTime.compareTo(o.requestTime);
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(this.requestTime);
        dataOutput.writeUTF(this.url);
        dataOutput.writeInt(this.sum);
        dataOutput.writeInt(this.sum_200);
        dataOutput.writeInt(this.sum_400);
        dataOutput.writeInt(this.sum_404);
        dataOutput.writeInt(this.sum_500);
        dataOutput.writeInt(this.sum_502);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.requestTime = dataInput.readUTF();
        this.url = dataInput.readUTF();
        this.sum = dataInput.readInt();
        this.sum_200 = dataInput.readInt();
        this.sum_400 = dataInput.readInt();
        this.sum_404 = dataInput.readInt();
        this.sum_500 = dataInput.readInt();
        this.sum_502 = dataInput.readInt();
    }

    @Override
    public void write(PreparedStatement statement) throws SQLException {
        statement.setString(1,this.requestTime);
        statement.setInt(2,this.sum);
        statement.setInt(3,this.sum_500);
        statement.setInt(4,this.sum_400);
        statement.setInt(5,this.sum_404);
        statement.setInt(6,this.sum_502);
        statement.setString(7,this.url);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {

        this.requestTime = resultSet.getString(1);
        this.sum = resultSet.getInt(2);
        this.sum_500 = resultSet.getInt(3);
        this.sum_400 = resultSet.getInt(4);
        this.sum_404 = resultSet.getInt(5);
        this.sum_502 = resultSet.getInt(6);
        this.url = resultSet.getString(7);
    }
}
