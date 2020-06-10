package edu.ucr.cs.cs167.ccamp032;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.SparkConf;

public class App {
    public static void main( String[] args ) {
        final String inputfile = args[0];

        //JavaSparkContext spark = new JavaSparkContext("local[*]", "CS167-Lab4");

        //JavaSparkContext spark = new JavaSparkContext("spark://127.0.0.1:7077", "CS167-Lab4");
        SparkConf conf = new SparkConf();
        if (!conf.contains("spark.master"))
            conf.setMaster("local[*]");
        System.out.printf("Using Spark master '%s'\n", conf.get("spark.master"));
        conf.setAppName("lab4");

        JavaSparkContext spark = new JavaSparkContext(conf);
        try {
            JavaRDD<String> logFile = spark.textFile(inputfile);
            System.out.printf("Number of lines in the log file %d\n", logFile.count());
        } finally {
            spark.close();
        }


    }
}