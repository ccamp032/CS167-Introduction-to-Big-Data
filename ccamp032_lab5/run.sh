#!/usr/bin/env sh

mvn clean package


spark-submit target/*_lab5-1.0-SNAPSHOT.jar count-all nasa_19950630.22-19950728.12.tsv

spark-submit target/*_lab5-1.0-SNAPSHOT.jar code-filter nasa_19950630.22-19950728.12.tsv 302

spark-submit target/*_lab5-1.0-SNAPSHOT.jar time-filter nasa_19950630.22-19950728.12.tsv 804955673 805590159

spark-submit target/*_lab5-1.0-SNAPSHOT.jar count-by-code nasa_19950630.22-19950728.12.tsv

spark-submit target/*_lab5-1.0-SNAPSHOT.jar sum-bytes-by-code nasa_19950630.22-19950728.12.tsv

spark-submit target/*_lab5-1.0-SNAPSHOT.jar avg-bytes-by-code nasa_19950630.22-19950728.12.tsv

spark-submit target/*_lab5-1.0-SNAPSHOT.jar top-host nasa_19950630.22-19950728.12.tsv

spark-submit target/*_lab5-1.0-SNAPSHOT.jar comparison nasa_19950630.22-19950728.12.tsv 805383872
