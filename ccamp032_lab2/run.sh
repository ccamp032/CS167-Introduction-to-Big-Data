#!/usr/bin/env sh
mvn clean package

hadoop jar target/ccamp032_lab2-1.0-SNAPSHOT.jar file:///$PWD/AREAWATER.csv file:///$PWD/AREAWATER_COPY.csv

cp AREAWATER.csv AREAWATER_COPY3.csv

hadoop jar target/ccamp032_lab2-1.0-SNAPSHOT.jar file:///$PWD/AREAWATER.csv hdfs:///user/ccamp032/AREAWATER_COPY.csv

hadoop jar target/ccamp032_lab2-1.0-SNAPSHOT.jar hdfs:///user/ccamp032/AREAWATER_COPY.csv file:///$PWD/AREAWATER_COPY2.csv

hadoop jar target/ccamp032_lab2-1.0-SNAPSHOT.jar hdfs:///user/ccamp032/AREAWATER_COPY.csv hdfs:///user/ccamp032/AREAWATER_COPY2.csv
