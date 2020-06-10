#!/usr/bin/env sh
mvn clean package


spark-submit --class edu.ucr.cs.cs167.ccamp032.Filter --master local[2] target/ccamp032_lab4-1.0-SNAPSHOT.jar nasa_19950630.22-19950728.12.tsv filter_output 302

spark-submit --class edu.ucr.cs.cs167.ccamp032.Aggregation --master local[2] target/ccamp032_lab4-1.0-SNAPSHOT.jar nasa_19950630.22-19950728.12.tsv aggregation_output 302
