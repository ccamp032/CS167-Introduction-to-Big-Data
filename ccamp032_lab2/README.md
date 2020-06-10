# Lab 2

<br/>

## Student Information
- <u>Name</u>: Christian Campos
- <u>Email</u>: ccamp032@ucr.edu
- <u>NetID</u>: ccamp032
- <u>SID</u>: 862080812

<br/>

## Lab Questions

(Q1) Verify the file size and record the running time. <br>
``` Copied 2271210910 bytes from AREAWATER.csv to AREAWATER_COPY.csv in 15.0942247 seconds ``` <br>
(Q2) Record the running time of the copy command. <br>
command: time cp AREAWATER.csv AREAWATER_COPY.csv   <br>
* output:
```
real    0m4.287s
user    0m0.000s
sys     0m1.734s 
```
(Q3) How do the two numbers compare? 
``` 
The linux copy command is faster than copy time in intellij. 
I think it's because how linux copy and time command function differently than in intellji. 
 ```
(Q4) Does the program run after you change the default file system to HDFS? <br>
      What is the error message, if any, that you get?
      
     ``` r AREAWATER.csv AREAWATER_COPY.csv
         Input file not found
         java.io.IOException: Input file not found
                 at edu.ucr.cs.cs167.ccamp032.App.main(App.java:30)
                 at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
                 at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
                 at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
                 at java.lang.reflect.Method.invoke(Method.java:498)
                 at org.apache.hadoop.util.RunJar.run(RunJar.java:244)
                 at org.apache.hadoop.util.RunJar.main(RunJar.java:158)
         
         real    0m1.899s
         user    0m2.203s
         sys     0m0.656s  ```
(Q5) Use your program to test the following cases and record the running time for each case.
  <br>  1.Copy a file from local file system to HDFS
  ```
Copied 2271210910 bytes from file:///mnt/c/Users/campo/Documents/CWC/UCR/2019-2020 Year/Spring 2020/CS167/workspace/ccamp032_lab2/AREAWATER.csv to hdfs:///user/ccamp032/AREAWATER_COPY.csv in 10.6875188 seconds
```
  2.Copy a file from HDFS to local file system.
  ```
Copied 2271210910 bytes from hdfs:///user/ccamp032/AREAWATER_COPY.csv to file:///mnt/c/Users/campo/Documents/CWC/UCR/2019-2020 Year/Spring 2020/CS167/workspace/ccamp032_lab2/AREAWATER_COPY2.csv in 12.2382245 seconds
  ```
  3.Copy a file from HDFS to HDFS.
  ```
  Copied 2271210910 bytes from hdfs:///user/ccamp032/AREAWATER_COPY.csv to hdfs:///user/ccamp032/AREAWATER_COPY2.csv in 10.1752795 seconds 
  ```
* Run time for 5 cases 

 | Cases  | running time (Seconds)        | 
    | -------|:-------------:| 
    | Case 1 (Q1)            | 15.0942247 |
    | Case 2 (Q2)            | 4.287s     |
    | Case 3 (Q5 i.)         | 10.6875188 |
    | Case 4 (Q5 ii.)        | 12.2382245 |
    | Case 5 (Q5 iii.)       | 10.1752795 |