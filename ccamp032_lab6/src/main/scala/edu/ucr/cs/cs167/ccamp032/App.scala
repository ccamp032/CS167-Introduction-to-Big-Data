package edu.ucr.cs.cs167.ccamp032

import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf

object App {

  def main(args : Array[String]) {
    val conf = new SparkConf
    if (!conf.contains("spark.master"))
      conf.setMaster("local[*]")
 //   println(s"Using Spark master '${conf.get("spark.master")}'")

    val command: String = args(0)
    val inputfile: String = args(1)

    val spark = SparkSession
      .builder()
      .appName("CS167 Lab6")
      .config(conf)
      .getOrCreate()

    try {
      val input = spark.read.format("csv")
        .option("sep", "\t")
        .option("inferSchema", "true")
        .option("header", "true")
        //.load("nasa_19950801.tsv")
        .load( path = inputfile)

      import spark.implicits._

      //input.show()
      // input.printSchema()

      val t1 = System.nanoTime
      command match {
        case "count-all" =>
          println(s"Total count for file '${inputfile}' is ${input.count()}")
        case "code-filter" =>
          val desiredCode: Int = args(2).toInt
          val code = input.filter($"response" === desiredCode)
          val total_count = code.count()
          println(s"Total count for file '${inputfile}' with response code ${desiredCode} is ${total_count}")
        case "time-filter" =>
          val start: Int = args(2).toInt
          val end: Int = args(3).toInt
          val timeRange =  input.filter($"time".between(start,end))
          println(s"Total count for file '${inputfile}' in time range [${start},${end}] is ${timeRange.count()}")
        case "count-by-code" =>
          println(s"Number of lines per code for the file ${inputfile}")
          input.groupBy($"response").count().show()
        case "sum-bytes-by-code" =>
          println(s"Total bytes per code for the file ${inputfile}")
          input.groupBy($"response").sum("bytes").show()
        case "avg-bytes-by-code" =>
          println(s"Total bytes per code for the file ${inputfile}")
          input.groupBy($"response").avg("bytes").show()
        case "top-host" =>
          val host_filter = input.groupBy($"host").count()
          val get_top = host_filter.orderBy($"count".desc).first()
          println(s"Top host in the file ${inputfile} by number of entries\nHost: ${get_top.getAs("host")}\nNumber of entries: ${get_top.getAs("count")}")
        case "comparison" =>
          // entries that happened before that time, and once more for the lines that happened at or after
          // that time. Print them side-by-side in a tabular form.
          val time: Int = args(2).toInt
          val before = input.filter($"time" < time).groupBy($"response").count().withColumnRenamed("count", "count_before")
          val after = input.filter($"time" >= time).groupBy($"response").count().withColumnRenamed("count", "count_after")
          println(s"Comparison of the number of lines per code before and after ${time} on file ${inputfile}")
          before.join(after, "response").show()
      }
      val t2 = System.nanoTime
      println(s"Command '${command}' on file '${inputfile}' finished in ${(t2-t1)*1E-9} seconds")

    } finally {
      spark.stop
    }
  }
}