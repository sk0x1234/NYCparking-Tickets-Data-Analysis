package Q1;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.DoubleWritable;
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
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class TopArea
{
  public static void main(String[] args)
    throws IOException, InterruptedException, ClassNotFoundException
  {
    Configuration conf = new Configuration();
    Job job1 = Job.getInstance(conf);
    job1.setJarByClass(TopArea.class);
    job1.setJobName("Job1");
    
    FileInputFormat.addInputPath(job1, new Path(args[0]));
    if (hdfs.exists("temp_output"))
      hdfs.delete("temp_output", true);
    FileOutputFormat.setOutputPath(job1, new Path("temp_output"));
    
    job1.setMapperClass(Mapper1.class);
    job1.setReducerClass(Reducer1.class);
    job1.setMapOutputKeyClass(Text.class);
    job1.setMapOutputValueClass(IntWritable.class);
    job1.setOutputFormatClass(TextOutputFormat.class);
    job1.setOutputKeyClass(Text.class);
    job1.setOutputValueClass(Text.class);
    job1.waitForCompletion(true);
    
    Configuration conf1 = new Configuration();
    Job job2 = Job.getInstance(conf1);
    job2.setJarByClass(TopArea.class);
    job2.setJobName("Job2");
    
    job2.setSortComparatorClass(comparator.class);
    FileInputFormat.addInputPath(job2, new Path("temp_output"));
    FileOutputFormat.setOutputPath(job2, new Path(args[1]));
    job2.setMapperClass(Mapper2.class);
    job2.setReducerClass(Reducer2.class);
    
    job2.setOutputKeyClass(DoubleWritable.class);
    job2.setOutputValueClass(Text.class);
    System.exit(job2.waitForCompletion(true) ? 0 : 1);
  }
  
  public static class Mapper1
    extends Mapper<LongWritable, Text, Text, IntWritable>
  {
    public void map(LongWritable keys, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
      throws IOException, InterruptedException
    {
      String line = value.toString();
      String[] tokens = line.split(",");
      String violationLocation = tokens[13];
      if ((!violationLocation.equals("")) && (!violationLocation.equals("Violation Location"))) {
        context.write(new Text(tokens[13]), new IntWritable(1));
      }
    }
  }
  
  public static class Reducer1
    extends Reducer<Text, IntWritable, Text, DoubleWritable>
  {
    private DoubleWritable count = new DoubleWritable();
    
    public void reduce(Text area, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, DoubleWritable>.Context context)
      throws IOException, InterruptedException
    {
      int sum = 0;
      for (IntWritable value : values) {
        sum += value.get();
      }
      this.count.set(sum);
      context.write(area, this.count);
    }
  }
  
  public static class Mapper2
    extends Mapper<Object, Text, DoubleWritable, Text>
  {
    private String[] tokens;
    private DoubleWritable count = new DoubleWritable();
    
    public void map(Object key, Text value, Mapper<Object, Text, DoubleWritable, Text>.Context context)
      throws IOException, InterruptedException
    {
      StringTokenizer st = new StringTokenizer(value.toString(), "\n");
      while (st.hasMoreTokens())
      {
        this.tokens = st.nextToken().toString().split("\t");
        this.count.set(Double.parseDouble(this.tokens[1].trim()));
        context.write(this.count, new Text(this.tokens[0]));
      }
    }
  }
  
  public static class Reducer2
    extends Reducer<DoubleWritable, Text, Text, DoubleWritable>
  {
    private int i = 0;
    
    protected void reduce(DoubleWritable key, Iterable<Text> values, Reducer<DoubleWritable, Text, Text, DoubleWritable>.Context context)
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
  
  public static class comparator
    extends WritableComparator
  {
    protected comparator()
    {
    	super(DoubleWritable.class, true);
    }
    
    public int compare(WritableComparable first, WritableComparable second)
    {
      DoubleWritable one = (DoubleWritable)first;
      DoubleWritable two = (DoubleWritable)second;
      return one.compareTo(two) * -1;
    }
  }
}
