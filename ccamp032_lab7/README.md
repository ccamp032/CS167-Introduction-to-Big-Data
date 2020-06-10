# Lab 7

## Student information
* Full name: Christian Campos
* E-mail: ccamp032@ucr.edu
* UCR NetID: ccamp032
* Student ID: 862080812

## Questions
* (Q1) What are the top five crime types?
```
    Theft, Battery, Criminal damage, Narcotics, Assault
```
* (Q2) Compare the sizes of the CSV file and the resulting Parquet file? What do you notice? Explain.    
```
    The CSV file is 1.6 GB in size. 
    The Parquet file is 435 MB. 
    The resulting parquet file is much smaller and that it consists of a directory with 13 separate parquet files.  
```    

* (Q3) How many times do you see the schema the output? How does this relate to the number of files in the output directory? 
```
    The schema output appeared 13 times. This number is equal to the amount of files that appear in the output directory,
    13 files appear in the output directory.
```
* (Q4) How does the output look like? How many files were generated?
```
    The output  using partitionBy() is a parquet directory that consists :labeled District=1-25, labeled District=31 and 
    labeled District=_HIVE_DEFAULT_PARTITION\_. 
    There are no district  13 or 23 folders.
    This gives us:23 folders with 13 files each, one with 2 files,and one with 4 files of a total of 305 files were generated.
```
* (Q5) Explain an efficient way to run this query on a column store.
```
    spark.sql("SELECT * FROM Crimes WHERE Case_Number='" + caseNumber + "'").show()
```
    
    I think an efficient way to run this query on a column store partitioned by the Case_Number
    column because it increases the performance of the query that retrieves the desired Case_Number record.
    

* (Q6) Which of the three input files you think will be processed faster using this operation?
```
    I think the partitioned parquet file runs faster on the stats-district operation because  
    partitioned parquet contents seem to be organized by district.
```

| Command         |     Time on CSV    | Time on non-partitioned Parquet | Time on partitioned Parquet |
|-----------------|:------------------:|:-------------------------------:|:---------------------------:|
| top-crime-types |  16.7165058        |            12.99178             |      14.872463600000001     |
| find            |  27.7449364        |           11.8509276            |      9.4502088              |
| stats           |  22.539028000000002|            17.3762082           |      17.7802212             | 
| stats-district  |  15.5002747        |            11.1073441           |      18.936381100000002     |