# Lab 4

## Student information
* Full name: Christian Campos
* E-mail: ccamp032@ucr.edu
* UCR NetID: ccamp032
* Student ID: 862080812

## Questions
(Q1) Do you think it will use your local cluster? Why or why not?
```
I think the local cluster was used because no applications appeared in the spark interface server.
``` 
(Q2) Does the application use the cluster that you started? How did you find out?
```
Yeah,the application ran on the cluster I started because there's one completed applications 
in the spark web interface and one under the worker node under the finished executors.
```
(Q3) What is the Spark master printed on the standard output on IntelliJ IDEA?
<br>
``` 
Using Spark master 'local[*]' 
Number of lines in the log file 30970 
``` 
(Q4) What is the Spark master printed on the standard output on the terminal?

```
Using Spark master 'spark://127.0.0.1:7077'
Using Spark's default log4j profile: org/apache/spark/log4j-defaults.properties
Number of lines in the log file 30970
```
(Q5) For the previous command that prints the number of matching lines, list all the processed input splits.
``` 
20/04/23 23:47:51 INFO HadoopRDD: Input split: file:/mnt/c/Users/campo/Documents/CWC/UCR/2019-2020 Year/Spring 2020/CS167/workspace/ccamp032_lab4/nasa_19950801.tsv:0+1169610
20/04/23 23:47:51 INFO HadoopRDD: Input split: file:/mnt/c/Users/campo/Documents/CWC/UCR/2019-2020 Year/Spring 2020/CS167/workspace/ccamp032_lab4/nasa_19950801.tsv:1169610+1169610
```

(Q6) For the previous command that counts the lines and prints the output, how many splits were generated? <br>
``` 
There's four input splits:
20/04/23 23:58:34 INFO HadoopRDD: Input split: file:/mnt/c/Users/campo/Documents/CWC/UCR/2019-2020 Year/Spring 2020/CS167/workspace/ccamp032_lab4/nasa_19950801.tsv:0+1169610
20/04/23 23:58:34 INFO HadoopRDD: Input split: file:/mnt/c/Users/campo/Documents/CWC/UCR/2019-2020 Year/Spring 2020/CS167/workspace/ccamp032_lab4/nasa_19950801.tsv:1169610+1169610
20/04/23 23:58:35 INFO HadoopRDD: Input split: file:/mnt/c/Users/campo/Documents/CWC/UCR/2019-2020 Year/Spring 2020/CS167/workspace/ccamp032_lab4/nasa_19950801.tsv:0+1169610
20/04/23 23:58:35 INFO HadoopRDD: Input split: file:/mnt/c/Users/campo/Documents/CWC/UCR/2019-2020 Year/Spring 2020/CS167/workspace/ccamp032_lab4/nasa_19950801.tsv:1169610+1169610
```
(Q7) Compare this number to the one you got earlier. 
<br>
``` 
The input splits double.
Q5: we get two input file splits and Q6: we get four input file splits.
```
(Q8) Explain why we get these numbers. 
The input file is read twice.
``` 
20/04/24 00:12:31 INFO HadoopRDD: Input split: file:/mnt/c/Users/campo/Documents/CWC/UCR/2019-2020 Year/Spring 2020/CS167/workspace/ccamp032_lab4/nasa_19950801.tsv:1169610+1169610
20/04/24 00:12:31 INFO HadoopRDD: Input split: file:/mnt/c/Users/campo/Documents/CWC/UCR/2019-2020 Year/Spring 2020/CS167/workspace/ccamp032_lab4/nasa_19950801.tsv:0+1169610
```
(Q9) What can you do to the current code to ensure that the file is read only once? <br>
 ```   
JavaRDD<String> logFile = spark.textFile(inputfile).cache(); 
```
