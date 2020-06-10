package edu.ucr.cs.cs167.ccamp032

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.SparkConf

object App {

  def main(args : Array[String]) {
    val operation: String = args(0)
    val inputfile: String = args(1)

    val conf = new SparkConf
    if (!conf.contains("spark.master"))
      conf.setMaster("local[*]")
   // println(s"Using Spark master '${conf.get("spark.master")}'")

    val spark = SparkSession
      .builder()
      .appName("CS167 Lab7")
      .config(conf)
      .getOrCreate()

    try {
      import spark.implicits._

      var input = spark.emptyDataFrame
      val inputFileExt = inputfile.split("\\.")(1)

      if( inputFileExt == "csv" ) {
        input = spark.read.format("csv")
          .option("sep", ",")
          .option("inferSchema", "true")
          .option("header", "true")
          .load(path = inputfile)
          .withColumnRenamed("Case Number", "Case_Number")
          .withColumnRenamed("Primary Type", "Primary_Type")
          .withColumnRenamed("Location Description", "Location_Description")
          .withColumnRenamed("Community Area", "Community_Area")
          .withColumnRenamed("FBI Code", "FBI_Code")
          .withColumnRenamed("X Coordinate", "X_Coordinate")
          .withColumnRenamed("Y Coordinate", "Y_Coordinate")
          .withColumnRenamed("Updated On", "Updated_On")

      }
      else if( inputFileExt == "parquet" ) {
        input = spark.read.parquet(inputfile)
      }

      input.createTempView("ChicagoCrimes")

      //input.show()
      //input.printSchema()

      val t1 = System.nanoTime
      operation match {
        case "write-parquet" =>
          val outputfile: String = args(2)
          input.write.parquet(outputfile)
        case "write-parquet-partitioned" =>
          val outputfile: String = args(2)
          input.write.partitionBy("District").parquet(outputfile)
        case "top-crime-types" =>
          spark.sql("SELECT Primary_Type, COUNT(Primary_Type) AS Crime_Count FROM ChicagoCrimes GROUP BY Primary_Type ORDER BY Crime_Count DESC LIMIT 5").show()
        case "find" =>
          val casenumber: String = args(2)
          val query = "SELECT * FROM ChicagoCrimes WHERE Case_Number='" + casenumber + "'"
          spark.sql(query).show()
        case "stats" =>
          val query = "SELECT District, SUM(int(Arrest)) as Total_Arrests, SUM(int(Domestic)) AS Total_Domestic, AVG(Beat) AS Average_Beat FROM ChicagoCrimes GROUP BY District ORDER BY District ASC"
          spark.sql(query).write.mode("overwrite").parquet("stats.parquet")
        // Save the output to a new parquet file named "stats.parquet"
        case "stats-district"  =>
          val district: String = args(2)
          val query = "SELECT District, SUM(int(Arrest)) as Total_Arrests, SUM(int(Domestic)) AS Total_Domestic, AVG(Beat) AS Average_Beat FROM ChicagoCrimes WHERE District='" + district + "' GROUP BY District"
          spark.sql(query).show()
        // Write the result to the standard output
      }

      val t2 = System.nanoTime
      println(s"Operation $operation on file '$inputfile' finished in ${(t2-t1)*1E-9} seconds")
    } finally {
      spark.stop
    }
  }
}