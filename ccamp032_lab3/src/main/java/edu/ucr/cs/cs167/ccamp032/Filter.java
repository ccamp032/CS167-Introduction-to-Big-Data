package edu.ucr.cs.cs167.ccamp032;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;


/**
 * Filter log file by response code
 */
public class Filter {
    public static class TokenizerMapper
            extends Mapper<LongWritable, Text, NullWritable, Text> {

        String responCode; // variable used to get response code from configurations

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            super.setup(context);
            // TODO add additional setup to your map task, if needed.
            responCode = context.getConfiguration().get("responCode"); // get response code from configurations
        } // end of setup function

        public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            if (key.get() == 0)
                return; // Skip header line
            String[] parts = value.toString().split("\t");
            String responseCode = parts[5];

            // TODO Filter by response code
            Configuration configuration = context.getConfiguration();
            if (responseCode.equals(responCode)){
                context.write(NullWritable.get(), value);
            }


        } // end of map function
    } // end of TokenizerMapper class

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        // TODO pass the desiredResponse code to the MapReduce program
        // Needed to put it here because returned null when placed below
        String desiredResponse = args[2];
        conf.set("responCode", desiredResponse); // add desired response code to the job configuration

        Job job = Job.getInstance(conf, "filter");
        job.setJarByClass(Filter.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setNumReduceTasks(0);
        job.setInputFormatClass(TextInputFormat.class);
        Path input = new Path(args[0]);
        FileInputFormat.addInputPath(job, input);
        Path output = new Path(args[1]);
        FileOutputFormat.setOutputPath(job, output);


        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
