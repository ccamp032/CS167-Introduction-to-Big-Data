# Lab 3

## Student information
* Full name: Christian Campos
* E-mail: ccamp032@ucr.edu
* UCR NetID: ccamp032
* Student ID: 862080812

## Questions

* (Q1) What do you think the line job.setJarByClass(Filter.class); does?

   I think the method informs the Hadoop which jar file to send to the nodes to
    perform map and reduce by specifying the class.  <br> In this case, a Filter class. <br>

* (Q2) What is the effect of the line job.setNumReduceTasks(0);?

    Zero means that there's no reducer will execute. <br> 
    It's a map phase so it's map only job. <br>

* (Q3) Where does the main function run? (Driver node, Master node, or a slave node).
     I think the main function runs on the master node  and slave node. <br>
* (Q4) How many lines do you see in the output?
    27972 lines <br>
* (Q5) How many files are produced in the output (Local File System)?

    4 files in filter_output.tsv directory : <br>
     1) ._SUCCESS.crc <br>
     2) part-m-00000.crc <br>
     3) _SUCCESS <br>
     4) part-m-00000 <br>
    
* (Q6) Explain this number based on the input file size and default block size.

    The mapper task is run in the Filter program so there's only one output file, part-m-0000. <br>
    The part-m-0000 now contains 27972 records all with a response code of 200. <br>

* (Q7) How many files are produced in the output? (HDFS)

     2 files: <br> 
     1) _SUCCESS <br> 
     2) part-m-00000 <br>

* (Q8) Explain this number based on the input file size and default block size.

   The mapper task is run in the Filter program so there's only one output file, part-m-0000. <br>
   The part-m-0000 now contains 27972 records all with a response code of 200. <br>

* (Q9) How many files are produced in the output directory and how many lines are there in each file? 
      3 files: <br> 
      1) _SUCCESS <br> 
      2) part-r-00000 <br>
      3) part-r-00001 <br>
      _SUCCESS and part-r-00001: no lines  <br>
      part-r-0000: <br>
      200	481974462 <br>
      302	26005 <br>
      304	0 <br> 
      404	0 <br>
       
* (Q10) Explain these numbers based on the number of reducers and number of response 
        codes in the input file. <br>
      The records went to one of the reducers in the aggregation program and that why is part-r-00001 no lines or output
<br>  The output line shows the response code of the records and total sum of the bytes of each record corresponding to the same response code. <br>         
* (Q11) How many files are produced in the output directory and how many lines are 
        there in each file? <br>
     3 files:_SUCCESS, part-r-00000, and part-r-00001. <br>
     _SUCCESS has no lines or output <br>
     part-r-00000 5 lines: <br>
     200	37585778 <br>
     302	3682049 <br>
     304	0 <br> 
     404	0 <br>
     500	0 <br>
     part-r-00001 - 2 lines: <br>
     403	0 <br>
     501	0 <br>
     
* (Q12) Explain these numbers based on the number of reducers and number of response codes 
        in the input file. <br>
     There's two reducers in the aggregation program so two outputs are created in the two files: part-r-00000 and part-r-00001
      <br> The output line shows the response code of the records and total sum of the bytes of each record corresponding  to the same response code. <br>   
* (Q13) How many files are produced in the output directory and how many lines are there in each file?
     <br> 3 files : _SUCCESS, part-r-00000, and part-r-00001. <br>
    _SUCCESS and part-r-00001 has no lines of output. <br>
    part-r-00000 - 1 line: <br>
    200	37551809
    
* (Q14) Explain these numbers based on the number of reducers and number of response codes in the input file.
    
    There's only one output after because the aggregation on the output of filter contains only records with response code 200.
    <br> It's like a join condition. <br>
    All the records only went to one of the reducers, this shows why file part-r-00001 has no output. <br>
    As a result, the total 37551809 bytes is based on the records with the response code 200.