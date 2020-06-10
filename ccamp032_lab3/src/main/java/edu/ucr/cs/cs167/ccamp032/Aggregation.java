package edu.ucr.cs.cs167.ccamp032;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;


public class Aggregation {
    public static class TokenizerMapper extends Mapper<LongWritable, Text, IntWritable, IntWritable> {

        private final static IntWritable one = new IntWritable(1);
        private IntWritable outKey = new IntWritable();
        private IntWritable outVal = new IntWritable();

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            if (key.get() == 0)
                return;
            String[] parts = value.toString().split("\t");
            int responseCode = Integer.parseInt(parts[5]);
            int bytes = Integer.parseInt(parts[6]);
            // TODO write <responseCode, bytes> to the output
            outKey.set(responseCode);
            outVal.set(bytes);
            context.write(outKey, outVal); // produce tuple with output key and out put value in bytes

        }
    } // end of TokenizerMapper

    public static class IntSumReducer
            extends Reducer<IntWritable, IntWritable, IntWritable, IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(IntWritable key, Iterable<IntWritable> values,
                           Context context) throws IOException, InterruptedException {
            // TODO write <key, sum(values)> to the output
            int sum =0;
            for (IntWritable val : values){
                sum += val.get();
            }
            result.set(sum);
            context.write(key, result);
        }
    } // end of IntSumReducer class

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "aggregation");
        job.setJarByClass(Aggregation.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setMapOutputKeyClass(IntWritable.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);
        job.setInputFormatClass(TextInputFormat.class);
        job.setNumReduceTasks(2);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    } // end of main
}
