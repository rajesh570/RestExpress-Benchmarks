#! /bin/bash
#
awk -f parse.awk $1 | tr "\n" "," | sed -e "s/--rate=//g" | awk '{gsub(/ eol,/,"\n");print}' > tmp1.csv
cat headers.txt tmp1.csv
rm tmp1.csv
