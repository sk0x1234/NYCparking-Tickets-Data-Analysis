## DataNodes Config files

#### core-site.xml
```

 <property>
         <name>fs.defaultFS</name>
         <value>hdfs://172.31.8.160:9000</value>
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
#### yarn-site.xml
```
	<property>
	   <name>yarn.nodemanager.aux-services</name>
	   <value>mapreduce_shuffle</value>
	 </property>

	<property>
		<name>yarn.resourcemanager.address</name>
		<value>172.31.8.160:8032</value>
	</property>
	<property>
		<name>yarn.resourcemanager.scheduler.address</name>
		<value>172.32.8.160:8030</value>
	</property>
	<property>
		<name>yarn.resourcemanager.resource-tracker.address</name>
		<value>172.32.8.160:8031</value>
	</property>

```
#### mapred-site.xml
```
     <property>
       <name>mapreduce.jobtracker.address</name>
       <value>172.31.8.160:54311</value>         // Nnode IP
     </property>

     <property>
       <name>mapreduce.framework.name</name>
       <value>yarn</value>
     </property>

```
