package edu.ucr.cs.cs167.ccamp032

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object App {

  def main(args : Array[String]) {
    val command: String = args(0)
    val inputfile: String = args(1)

    val thetime: Int = 2
    val ResponseCode: Int = 5
    val ByteCode: Int = 6

    val conf = new SparkConf
    if (!conf.contains("spark.master"))
      conf.setMaster("local[*]")
   // println(s"Using Spark master '${conf.get("spark.master")}'")
    conf.setAppName("lab5")
    val sparkContext = new SparkContext(conf)
    try {
      val inputRDD: RDD[String] = sparkContext.textFile(inputfile)
      val inFilter :RDD[String] = inputRDD.filter(s => !(s.contains("host\tlogname")))
      val matchingLines: RDD[Array[String]] = inputRDD.map(line => line.split("\t")).filter(parts => !parts(0).equals("host"))
     // matchingLines.take(20).foreach(println)

      val t1 = System.nanoTime
      command match {
        case "count-all" =>
          val totalCount = inputRDD.count()
          println(" Total count for file '" + inputfile + " is " + totalCount)
        case "code-filter" =>
          val desiredCode: String = args(2)
          val code = inFilter.filter(s => s.split("\t")(ResponseCode) == desiredCode)
          val total_count = code.count()
          println(" Total count for file '" + inputfile +" with response code " + desiredCode + " is " + total_count)
        case "time-filter" =>
          val startTime: Long = args(2).toLong
          val endTime : Long = args(3).toLong
          val code = inFilter.filter(s => s.split("\t")(thetime).toLong >= startTime  &&  s.split("\t")(thetime).toLong <= endTime )
          val total_count = code.count()
          println(" Total count for file '" + inputfile +" in time range [" + startTime + ", " + endTime + "] is " + total_count)
        case "count-by-code" =>
          val codeTup =  matchingLines.map(line => (line(ResponseCode), 1))
          val codeCountMap = codeTup.countByKey()
          println(s"Number of lines per code for the file \nCode,Count")
          codeCountMap foreach(x => println(x._1 + "," + x._2))
        case "sum-bytes-by-code" =>
          val codesMap = matchingLines.map(codes => (codes(ResponseCode), codes(ByteCode).toLong)).reduceByKey((b1,b2) => (b1+b2)).collect()
          println("Number of bytes per code for the file \n" + inputfile)
          println("Code,Sum(bytes)")
          codesMap.foreach(codes => println( codes._1 + "," + codes._2))
        case "avg-bytes-by-code" =>
          //getting the number of response codes for count
         val count = matchingLines.map(line => (line(ResponseCode), 1)).countByKey()
          //getting the sum of all the bytes for each response code
          val sumBytes = matchingLines.map(line => (line(ResponseCode),line(ByteCode).toLong)).reduceByKey((accum, n) => (accum + n)).collect()
          //computing the avg
          println("Average bytes per code for the file "+inputfile)
          println("Code,Avg(bytes)")
          sumBytes.foreach(x => println(x._1 + ","+x._2.toDouble / count(x._1).toDouble))
        case "top-host" =>
          val codesMap = matchingLines.map(codes => (codes(0), 1)).reduceByKey((b1,b2) => (b1+b2)).sortBy(_._2, false).collect()
          println("Top host in the file by number of entries" +  inputfile)
          println("Host: " + codesMap(0)._1 +" \nNumber of entries: " + codesMap(0)._2)
        case "comparison" =>
          val time: Long = args(2).toLong
          val before = matchingLines.filter(parts => (parts(thetime).toLong < time))
          val after = matchingLines.filter(parts => (parts(thetime).toLong > time))
          val num_before_and_after = before.map(parts => (parts(ResponseCode), 1)).countByKey().toSeq ++ after.map(parts => (parts(ResponseCode), 1)).countByKey().toSeq
          val merged = num_before_and_after.groupBy(_._1)
          val cleaned = merged.mapValues(_.map(_._2).toList)
          println(s"Comparison of the number of lines per code before and after ${time} on file ${inputfile}\nCode,Count before,Count after")
          cleaned.foreach(parts => println(s"${parts._1},${parts._2(0)},${parts._2(1)}"))
        // entries that happened before that time, and once more for the lines that happened at or after
        // that time. Print them side-by-side in a tabular form.
      }
      val t2 = System.nanoTime
      println(s"Command '${command}' on file '${inputfile}' finished in ${(t2-t1)*1E-9} seconds")
    } finally {
      sparkContext.stop
    }
  }
}