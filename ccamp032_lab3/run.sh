#!/usr/bin/env sh
mvn clean package

yarn jar target/ccamp032_lab3-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.ccamp032.Filter file://$PWD/nasa_19950630.22-19950728.12.tsv file://$PWD/filter_output1.tsv 200
yarn jar target/ccamp032_lab3-1.0-SNAPSHOT.jar edu.ucr.cs.cs167.ccamp032.Aggregation file://$PWD/filter_output1.tsv file://$PWD/aggregation_output1.tsv
