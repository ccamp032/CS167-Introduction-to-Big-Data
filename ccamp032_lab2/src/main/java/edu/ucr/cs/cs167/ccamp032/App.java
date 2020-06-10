package edu.ucr.cs.cs167.ccamp032;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import java.io.IOException;

public class App
{

        public static void main( String[] args ) throws Exception {
            if (args.length != 2) {
                System.out.println("Invalid amount of Arguments");
                System.exit(0);
            }

            Configuration conf = new Configuration();
            try {

                // Hadoop DFS Path - Input & Output file
                Path inputPath = new Path(args[0]);
                Path outputPath  = new Path(args[1]);
                FileSystem in_fs = inputPath.getFileSystem(conf);
                FileSystem out_fs = outputPath .getFileSystem(conf);

                // Verification
                if (!in_fs.isFile(inputPath)) {
                    System.out.println("Input file not found");
                    throw new IOException("Input file not found");
                }
                if (out_fs.isFile(outputPath )) {
                    System.out.println("Output file already exists");
                    throw new IOException("Output file already exists");
                }

                // open and read from file
                FSDataInputStream in_stream = in_fs.open(inputPath);
                // Create file to write
                FSDataOutputStream out_stream = out_fs.create(outputPath);

                byte buffer[] = new byte[4096];
                long b_count = 0;
                long starting_Time = System.nanoTime();
                double elapsed_Time_Second = 0;
                try {
                    int bytesRead = 0;
                    while ((bytesRead = in_stream.read(buffer)) > 0) {
                        out_stream.write(buffer, 0, bytesRead);
                        b_count += bytesRead;
                    }
                } catch (IOException e) {
                    System.out.println("Error while copying file");
                } finally {
                    in_stream.close();
                    out_stream.close();
                }
                long running_Time = System.nanoTime() - starting_Time;
                elapsed_Time_Second = (double) running_Time / 1000000000;
                System.out.println("Copied " + b_count + " bytes from " + args[0] + " to "
                        + args[1] + " in " + elapsed_Time_Second + " seconds");

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

    } // end of main
} // end of class App