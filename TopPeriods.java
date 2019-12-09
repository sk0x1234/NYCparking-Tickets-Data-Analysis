package Q2;

import java.io.IOException;
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

public class TopPeriods
{
  public static void main(String[] args)
    throws IOException, ClassNotFoundException, InterruptedException
  {
    Configuration config1 = new Configuration();
    Job job1 = Job.getInstance(config1, "TopPeriods_Job1");
    job1.setJarByClass(TopPeriods.class);
    job1.setMapperClass(Mapper1.class);
    job1.setReducerClass(Reducer1.class);
    job1.setMapOutputKeyClass(Text.class);
    job1.setMapOutputValueClass(IntWritable.class);
    FileInputFormat.addInputPath(job1, new Path(args[0])); 
    if (hdfs.exists("temp_output"))
      hdfs.delete("temp_output", true);
    FileOutputFormat.setOutputPath(job1, new Path("temp_output"));
    job1.waitForCompletion(true);
    
    Configuration config2 = new Configuration();
    Job job2 = Job.getInstance(config2, "TopPeriods_Job2");
    job2.setJarByClass(TopPeriods.class);
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
    public void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
      throws IOException, InterruptedException
    {
      String line = value.toString();
      String[] tokens = line.split(",");
      String violationTime = tokens[19];
      if (violationTime.matches("^\\d{4}[AP]$"))
      {
        int hour = Integer.parseInt(violationTime.substring(0, 2));
        int amPm = violationTime.charAt(4);
        hour = amPm == 80 ? hour + 12 : hour;
        hour = hour == 24 ? 0 : hour;
        String range = String.format("%02d", new Object[] { Integer.valueOf(hour) }) + ":00 - " + String.format("%02d", new Object[] { Integer.valueOf(hour + 1) }) + ":00";
        context.write(new Text(range), new IntWritable(1));
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
    private int i = 0;
    
    protected void reduce(IntWritable key, Iterable<Text> values, Reducer<IntWritable, Text, Text, IntWritable>.Context context)
      throws IOException, InterruptedException
    {
      for (Text value : values)
      {
        if (this.i >= 5) {
          break;
        }
        context.write(value, key);
        this.i += 1;
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
