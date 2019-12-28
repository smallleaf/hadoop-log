#!/bin/bash

if [ -n "$1" -a "$1" = "daemon" ];then
   nohup java -classpath "../lib/*" -Xmx256m -Xms256m -Xmn10m -XX:PermSize=12m -XX:MaxPermSize=12m -Dcom.sun.management.jmxremote -Dcom.sun
   .management.jmxremote.authenticate=false -Xloggc:/data/logs/launcherzthemestore/log/gc.log -XX:+UseConcMarkSweepGC   -XX:+PrintGCDetails
   -XX:+PrintHeapAtGC  -XX:+PrintGCDateStamps  -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=12345  com.share1024
   .App >../logs/hadoop_log.log 2>&1 &
else
    java -classpath "../lib/*" -Xmx256m -Xms256m -Xmn10m -XX:PermSize=12m -XX:MaxPermSize=12m -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.authenticate=false -Xloggc:/data/logs/launcherzthemestore/log/gc.log -XX:+UseConcMarkSweepGC   -XX:+PrintGCDetails -XX:+PrintHeapAtGC  -XX:+PrintGCDateStamps  -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=12345  com.share1024.App
fi




