package edu.ucr.cs.cs167.ccamp032;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.io.OutputStreamWriter;
import java.io.StringWriter;

public class Aggregation {
    public static void main(String[] args) throws IOException {
        final String inputfile = args[0];
        final String outputfile = args[1];
        BufferedWriter outputCode = null;
        outputCode = new BufferedWriter(new FileWriter(outputfile));
        //JavaSparkContext spark = new JavaSparkContext("local[*]", "CS167-Lab4");

        //JavaSparkContext spark = new JavaSparkContext("spark://127.0.0.1:7077", "CS167-Lab4");
        SparkConf conf = new SparkConf();
        if (!conf.contains("spark.master"))
            conf.setMaster("local[*]");
        System.out.printf("Using Spark master '%s'\n", conf.get("spark.master"));
        conf.setAppName("lab4");
        JavaSparkContext spark = new JavaSparkContext(conf);
        try {

            JavaRDD<String> logFile = spark.textFile(inputfile).cache();
            JavaPairRDD<String,Integer> linesByCode = logFile.mapToPair(new PairFunction<String, String, Integer>() {
                   @Override
                   public Tuple2<String, Integer> call(String s){
                        String code = s.split("\t")[5];
                        return new Tuple2<String,Integer>(code,1);
                }
                });

            Map<String,Long> countByCode = linesByCode.countByKey();
            //countByCode.forEach((key, value) -> System.out.println(key + ":" + value));
            //String codeData = "";
            String codeData = "";

            for(Map.Entry<String,Long> entry : countByCode.entrySet()){
            codeData = "Code " + "'"+ entry.getKey() + "'" + " : number of entries " + entry.getValue() ;
            System.out.println(codeData);
            //System.out.println("Done !!");
            //JavaRDD<String> logFile = spark.textFile(inputfile);
            //System.out.printf("Number of lines in the log file %d\n", logFile.count());
           // outputCodesData = new BufferedWriter(new FileWriter(outputfile));
            outputCode.write(codeData);
            outputCode.newLine();
            }

        } finally {
            outputCode.flush();
            outputCode.close();
            spark.close();
        }
    }
}
