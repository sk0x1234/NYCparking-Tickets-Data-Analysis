##CS643 Programming Assignment

- Copy the JAR files in the users home directory on name node (master node) using FTP Client.
- Upload the states input files in users home directory within input folder

### Commands Used

1. Create Directories
	
	hadoop fs -mkdir -p /user/ubuntu/input

2. Upload input folder to HDFS
	
	hadoop fs -put ~/input-kaggle/* /user/ubuntu/input/


//compiling jar 

Q1 :  javac  TopArea.java -cp $(hadoop classpath)
Q2 :  javac  TopPeriods.java -cp $(hadoop classpath)
Q3 : javac  TopViolationReasons.java -cp $(hadoop classpath)
Q4 :  javac  TotalViolations.java -cp $(hadoop classpath)

jar cf TotalViolations.jar TotalViolations.class
jar cf TopArea.jar TopArea.class

3. Running Jar Files

	hadoop jar ~/JAR/CS643_AP_Q1.jar edu.njit.cs643.project.q1.TopArea /user/ubuntu/input/ /user/ubuntu/output1
	hadoop jar ~/JAR/CS643_AP_Q2.jar edu.njit.cs643.project.q2.TopPeriods /user/ubuntu/input/ /user/ubuntu/output2
	hadoop jar ~/JAR/CS643_AP_Q3.jar edu.njit.cs643.project.q3.TopViolationReasons /user/ubuntu/cs643/input/ /user/ubuntu/output3
	hadoop jar ~/JAR/CS643_AP_Q4.jar edu.njit.cs643.project.q4.TotalViolations /user/ubuntu/input/ /user/ubuntu/output4

4. Check the output

	hadoop fs -cat /user/ubuntu/output1/part-r-00000
	hadoop fs -cat /user/ubuntu/output2/part-r-00000
	hadoop fs -cat /user/ubuntu/output3/part-r-00000
	hadoop fs -cat /user/ubuntu/output4/part-r-00000



