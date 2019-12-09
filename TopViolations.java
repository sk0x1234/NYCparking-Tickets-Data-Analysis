package Q3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
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

public class TopViolationReasons
{
  public static void main(String[] args)
    throws IOException, InterruptedException, ClassNotFoundException
  {
    Configuration conf = new Configuration();
    Job job1 = Job.getInstance(conf);
    job1.setJarByClass(TopViolationReasons.class);
    job1.setJobName("TopViolationReasons_Job1");
    
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
    job2.setJarByClass(TopViolationReasons.class);
    job2.setJobName("TopViolationReasons_Job2");
    
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
    Map<Integer, String> violationCodeMap = new HashMap<Integer , String > ();
    
    protected void setup(Mapper<LongWritable, Text, Text, IntWritable>.Context context)
      throws IOException, InterruptedException
    {
      ClassLoader classLoader = getClass().getClassLoader();
      InputStream inputStream = classLoader.getResourceAsStream("violationcodes.csv");
      BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
      String line = null;
      while ((line = bufferedReader.readLine()) != null)
      {
        String[] tokens = line.split(",");
        this.violationCodeMap.put(Integer.valueOf(Integer.parseInt(tokens[0])), tokens[1]);
      }
    }
    
    public void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context)
      throws IOException, InterruptedException
    {
      String line = value.toString();
      String[] tokens = line.split(",");
      String violationCode = tokens[5];
      if ((!violationCode.equals("")) && (!violationCode.equals("Violation Code")) && (this.violationCodeMap.containsKey(violationCode))) {
        context.write(new Text(violationCode), new IntWritable(1));
      }
    }
  }
  
  public static class Reducer1
    extends Reducer<Text, IntWritable, Text, DoubleWritable>
  {
    private DoubleWritable count = new DoubleWritable();
    
    public void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, DoubleWritable>.Context context)
      throws IOException, InterruptedException
    {
      int sum = 0;
      for (IntWritable value : values) {
        sum += value.get();
      }
      this.count.set(sum);
      context.write(key, this.count);
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
