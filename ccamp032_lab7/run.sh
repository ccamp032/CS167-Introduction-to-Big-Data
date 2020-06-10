#!/usr/bin/env sh
mvn clean package

#Convert CSV to non-partitioned Parquet file
spark-submit target/*.jar write-parquet Crimes_-_2001_to_present.csv cc.parquet

#Convert CSV to partitioned Parquet file
spark-submit target/*.jar write-parquet-partitioned Crimes_-_2001_to_present.csv cc-p.parquet

#Run the top-crime-types operation on the three files.
spark-submit target/*.jar top-crime-types Crimes_-_2001_to_present.csv
spark-submit target/*.jar top-crime-types cc.parquet
spark-submit target/*.jar top-crime-types cc-p.parquet

#Run the find operation with Case Number HY413923 on the three files.
spark-submit target/*.jar find Crimes_-_2001_to_present.csv HY413923
spark-submit target/*.jar find cc.parquet HY413923
spark-submit target/*.jar find cc-p.parquet HY413923

#Run the stats operation on the three files.
spark-submit target/*.jar stats Crimes_-_2001_to_present.csv
spark-submit target/*.jar stats cc.parquet
spark-submit target/*.jar stats cc-p.parquet

#Run the stats-district operation with District ID 8 on the three files.
spark-submit target/*.jar stats-district Crimes_-_2001_to_present.csv 8
spark-submit target/*.jar stats-district cc.parquet 8
spark-submit target/*.jar stats-district cc-p.parquet 8
