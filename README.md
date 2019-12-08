# NYCparking-Tickets-Data-Analysis



## Nnode 


#### core-site.xml
```
<configuration>
 <property>
         <name>fs.defaultFS</name>
         <value>hdfs://172.31.8.160:9000</value>
  </property>

</configuration>
```

#### mapred-site.xml
```

<configuration>

     <property>
       <name>mapreduce.framework.name</name>
       <value>yarn</value>
     </property>
	
</configuration>

```
#### yarn-site.xml
```
<property>
   <name>yarn.nodemanager.aux-services</name>
   <value>mapreduce_shuffle</value>
 </property>

```

#### hdfs-site.xml
```
<property>
    <name>dfs.replication</name>
     <value>2</value>
</property>
    <property>
        <name>dfs.namenode.name.dir</name>
	      <value>/home/ubuntu/hadoop/hadoop_data/hdfs/namenode</value>
     </property>
     
      <property>
        <name>dfs.datanode.data.dir</name>
	       <value>/home/ubuntu/hadoop/hadoop_data/hdfs/datanode</value>
     </property>

```





