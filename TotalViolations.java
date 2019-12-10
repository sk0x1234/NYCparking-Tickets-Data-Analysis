package Q4;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class TotalViolations
{
  public static void main(String[] args)
    throws IOException, ClassNotFoundException, InterruptedException
  {
    Configuration config1 = new Configuration();
    Job job1 = Job.getInstance(config1, "Total_Violations_Job1");
    job1.setJarByClass(TotalViolations.class);
    job1.setMapperClass(Mapper1.class);
    job1.setReducerClass(Reducer1.class);
    job1.setMapOutputKeyClass(Text.class);
    job1.setMapOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job1, new Path(args[0]));
    if (hdfs.exists("temp_output"))
      hdfs.delete("temp_output", true);
    FileOutputFormat.setOutputPath(job1, new Path("/user/ubuntu/temp_output"));
    job1.waitForCompletion(true);
    
    Configuration config2 = new Configuration();
    Job job2 = Job.getInstance(config2, "Total_Violations_Job2");
    job2.setJarByClass(TotalViolations.class);
    job2.setMapperClass(Mapper2.class);
    job2.setReducerClass(Reducer2.class);
    job2.setSortComparatorClass(TempComparator.class);
    job2.setMapOutputKeyClass(IntWritable.class);
    job2.setMapOutputValueClass(Text.class);
    FileInputFormat.addInputPath(job2, new Path("temp_output"));
    FileOutputFormat.setOutputPath(job2, new Path(args[1]));
    System.exit(job2.waitForCompletion(true) ? 0 : 1);
  }
  
  public static class Mapper1
    extends Mapper<LongWritable, Text, Text, IntWritable>
  {
    public void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context c)
      throws IOException, InterruptedException
    {
      String line = value.toString();
      String[] tokens = line.split(",");
      
      String issueDate = tokens[4];
      if (issueDate.matches("^\\d{2}/\\d{2}/\\d{4}$"))
      {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy").withLocale(Locale.US);
        LocalDate date = LocalDate.parse(issueDate, formatter);
        if ((date.getYear() >= 2013) && (date.getYear() <= 2017))
        {
          String month = date.getMonth().getDisplayName(TextStyle.SHORT, Locale.US) + " " + date.getYear();
          c.write(new Text(month), new IntWritable(1));
        }
      }
    }
  }
  
  public static class Reducer1
    extends Reducer<Text, IntWritable, Text, IntWritable>
  {
    public void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context c)
      throws IOException, InterruptedException
    {
      int totalCount = 0;
      for (IntWritable val : values) {
        totalCount++;
      }
      c.write(new Text(key), new IntWritable(totalCount));
    }
  }
  
  public static class Mapper2
    extends Mapper<Object, Text, IntWritable, Text>
  {
    private String[] tokens;
    private IntWritable count = new IntWritable();
    
    public void map(Object key, Text value, Mapper<Object, Text, IntWritable, Text>.Context context)
      throws IOException, InterruptedException
    {
      StringTokenizer st = new StringTokenizer(value.toString(), "\n");
      while (st.hasMoreTokens())
      {
        this.tokens = st.nextToken().toString().split("\t");
        this.count.set(Integer.parseInt(this.tokens[1].trim()));
        context.write(this.count, new Text(this.tokens[0]));
      }
    }
  }
  
  public static class Reducer2
    extends Reducer<IntWritable, Text, Text, IntWritable>
  {
    protected void reduce(IntWritable key, Iterable<Text> values, Reducer<IntWritable, Text, Text, IntWritable>.Context context)
      throws IOException, InterruptedException
    {
      for (Text value : values) {
        context.write(value, key);
      }
    }
  }
  
  public static class TempComparator
    extends WritableComparator
  {
    protected TempComparator()
    {
    	super(IntWritable.class, true);
    }
    
    public int compare(WritableComparable first, WritableComparable second)
    {
      IntWritable one = (IntWritable)first;
      IntWritable two = (IntWritable)second;
      return one.compareTo(two) * -1;
    }
  }
}
