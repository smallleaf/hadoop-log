## hadoop日志请求分析系统
需求：从大量的日志文件中，分析请求情况。
这里设计的几个问题：
1.日志如何收集？：这里是多台机器，并且需要定时去将这些日志存入hdfs，这里用到flume来做日志收集。
2.考虑到不需要实时的统计情况，考虑采用hadoop来做日志分析。
3.对于分析的结果，存入mysql，对于原日志信息，存入hbase。
4.并且提供接口可以查询
5.完全基于springboot。

### 1.日志分析
日志来源于tomcat的请求日志，日志格式为
```
<Valve className="org.apache.catalina.valves.AccessLogValve"
                 directory="/data/logs/constellation_core"
                 prefix="access."
                 suffix=".log"
                 pattern="%{X-Real-IP}i %h %l %u %t %r %s %b -- %D" resolveHosts="false"
                 fileDateFormat="yyyy-MM-dd"/>
 %{X-Real-IP}i %h %l %u %t %r %s %b -- %D
```
.         %a - 远端IP地址
·        %A - 本地IP地址
·        %b - 发送的字节数，不包括HTTP头，如果为0，使用"－"
·        %B - 发送的字节数，不包括HTTP头
·        %h - 远端主机名(如果resolveHost=false，远端的IP地址）
·        %H - 请求协议
·        %l - 从identd返回的远端逻辑用户名（总是返回 '-'）
·        %m - 请求的方法（GET，POST，等）
·        %p - 收到请求的本地端口号
·        %q - 查询字符串(如果存在，以 '?'开始)
·        %r - 请求的第一行，包含了请求的方法和URI
·        %s - 响应的状态码
·        %S - 用户的session ID
·        %t - 日志和时间，使用通常的Log格式
·        %u - 认证以后的远端用户（如果存在的话，否则为'-'）
·        %U - 请求的URI路径
·        %v - 本地服务器的名称
·        %D - 处理请求的时间，以毫秒为单位
·        %T - 处理请求的时间，以秒为单位

请求日志如下：
```
172.58.62.249 10.10.34.233 - - [12/Dec/2018:23:55:36 +0800] POST /api/v1/match/constellation HTTP/1.1 200 2393 -- 2
```
最终日志的模型
```
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
```

### 2.日志统计分析

#### 2.1
每个页面的统计，我们可以按照小时来统计，总的统计可以在每小时的基础上进行统计。
统计项目最终为：
ResultStats:
请求页面，每小时的ip，每小时的请求统计量，每小时500，200，400，404的请求统计
key：请求地址+"_"+每小时时间
这个mapreduce过程就为
Map过程: key:请求地址+"__"+每小时时间,value:LogBean
Reduce过程：Key:requestUrl,value:ResultStats

### 3.架构设计

#### 3.1 数据收集
![在这里插入图片描述](https://img-blog.csdnimg.cn/2018121420120333.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3UwMTAzOTkwMDk=,size_16,color_FFFFFF,t_70)

数据收集规则，数据是最终放在hdfs，是按照
/data/log/yyyyMMdd/hh.log的形式。

#### 3.1 数据解析与存储



