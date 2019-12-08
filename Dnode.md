## DataNodes Config files

#### core-site.xml
```

 <property>
         <name>fs.defaultFS</name>
         <value>hdfs://172.31.8.160:9000</value>
  </property>

```

#### 
```
<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<!--
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. See accompanying LICENSE file.
-->

<!-- Put site-specific property overrides in this file. -->

<configuration>
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

</configuration>
<?xml version="1.0"?>
<!--
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. See accompanying LICENSE file.
-->
<configuration>

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

</configuration>
<?xml version="1.0" encoding="UTF-8"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<!--
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License. See accompanying LICENSE file.
-->

<!-- Put site-specific property overrides in this file. -->

<configuration>
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

</configuration>



```
