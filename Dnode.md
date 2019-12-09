## DataNodes Config files

#### core-site.xml
```

<configuration>
       <property>
          <name>fs.defaultFS</name>
          <value>hdfs://172.31.11.21:9000</value>
      </property>
</configuration>

```

#### hdfs-site.xml
```
<configuration>

  <property>
    <name>dfs.replication</name>
    <value>1</value>
  </property>
  <property>
	  <name>dfs.datanode.data.dir </name>
	  <value>/home/ubuntu/hadoop/hadoop_data/datanode </value>
  </property>

</configuration>
```
#### yarn-site.xml
```
<configuration>

     <property>
        <name>yarn.nodemanager.aux-services</name>
        <value>mapreduce_shuffle</value>
    </property>

    <property>
      <name>yarn.application.classpath</name>
      <value>/home/ubuntu/hadoop/etc/hadoop, /home/ubuntu/hadoop/share/hadoop/common/*, /home/ubuntu/hadoop/share/hadoop/common/lib/*, /home/ubuntu/hadoop/share/hadoop/hdfs/*, /home/ubuntu/hadoop/share/hadoop/hdfs/lib/*, /home/ubuntu/hadoop/share/hadoop/mapreduce/*, /home/ubuntu/hadoop/share/hadoop/mapreduce/lib/*, /home/ubuntu/hadoop/share/hadoop/yarn/*, /home/ubuntu/hadoop/share/hadoop/yarn/lib/*</value>
    </property>


  <property>
    <description>
      Number of seconds after an application finishes before the nodemanager's
      DeletionService will delete the application's localized file directory
      and log directory.

      To diagnose Yarn application problems, set this property's value large
      enough (for example, to 600 = 10 minutes) to permit examination of these
      directories. After changing the property's value, you must restart the
      nodemanager in order for it to have an effect.

      The roots of Yarn applications' work directories is configurable with
      the yarn.nodemanager.local-dirs property (see below), and the roots
      of the Yarn applications' log directories is configurable with the
      yarn.nodemanager.log-dirs property (see also below).
    </description>
    <name>yarn.nodemanager.delete.debug-delay-sec</name>
    <value>600</value>
  </property>

    <property>
      <name>yarn.log-aggregation-enable</name>
      <value>true</value> 
    </property>

    <property>
      <name>yarn.log-aggregation.retain-seconds</name>
      <value>900000</value> 
    </property> 

    <property>
      <name>yarn.nodemanager.vmem-check-enabled</name>
      <value>false</value>
    </property>


  <property>
    <description>The hostname of the RM.</description>
    <name>yarn.resourcemanager.hostname</name>
    <value>0.0.0.0</value>
  </property>    
  
  <property>
    <description>The address of the applications manager interface in the RM.</description>
    <name>yarn.resourcemanager.address</name>
    <value>172.31.11.21:8032</value>
  </property>

   <property>
    <description>
      The actual address the server will bind to. If this optional address is
      set, the RPC and webapp servers will bind to this address and the port specified in
      yarn.resourcemanager.address and yarn.resourcemanager.webapp.address, respectively. This
      is most useful for making RM listen to all interfaces by setting to 0.0.0.0.
    </description>
    <name>yarn.resourcemanager.bind-host</name>
    <value></value>
  </property>

   <property>
    <description>The address of the scheduler interface.</description>
    <name>yarn.resourcemanager.scheduler.address</name>
    <value>172.31.11.21:8030</value>
  </property>

  <property>
    <name>yarn.resourcemanager.resource-tracker.address</name>
    <value>172.31.11.21:8031</value>
  </property>

   <property>
    <description>Are acls enabled.</description>
    <name>yarn.acl.enable</name>
    <value>false</value>
  </property>

   <property>
    <description>The address of the RM admin interface.</description>
    <name>yarn.resourcemanager.admin.address</name>
    <value>172.31.11.21:8033</value>
  </property>

<property>
    <description>Maximum time to wait to establish connection to
    ResourceManager.</description>
    <name>yarn.resourcemanager.connect.max-wait.ms</name>
    <value>900000</value>
  </property>

  <property>
    <description>How often to try connecting to the
    ResourceManager.</description>
    <name>yarn.resourcemanager.connect.retry-interval.ms</name>
    <value>30000</value>
  </property>

    <property>
    <description>How often to check that containers are still alive. </description>
    <name>yarn.resourcemanager.container.liveness-monitor.interval-ms</name>
    <value>600000</value>
  </property>

    <property>
    <description>The class to use as the resource scheduler.</description>
    <name>yarn.resourcemanager.scheduler.class</name>
    <value>org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.CapacityScheduler</value>
  </property>


  <property>
    <description>The minimum allocation for every container request at the RM,
    in MBs. Memory requests lower than this will throw a
    InvalidResourceRequestException.</description>
    <name>yarn.scheduler.minimum-allocation-mb</name>
    <value>1024</value>
  </property>

  <property>
    <description>The maximum allocation for every container request at the RM,
    in MBs. Memory requests higher than this will throw a
    InvalidResourceRequestException.</description>
    <name>yarn.scheduler.maximum-allocation-mb</name>
    <value>3192</value>
  </property>

</configuration>


```
#### mapred-site.xml
```
  <configuration>

  <property>
    <name>mapred.job.tracker</name>
    <value>172.31.11.21:54311</value>
  </property>	

  <property>
    <name>mapreduce.framework.name</name>
    <value>yarn</value>
  </property>	


</configuration>
```
