#!/bin/bash

mvn -q package
if [ "$?" -ne "0" ]; then
   echo "Build failed!"
   exit 1
fi

command -V pv 2> /dev/null
if [ "$?" -ne "0" ]; then
   echo "Performance test skipped, becuase of lack of 'pv' unix command!"
   exit 1
fi


version=`mvn help:evaluate -Dexpression=project.version | grep -v INFO | xargs`
jar_path="target/cgrep-$version.jar"

cgrep="java -jar $jar_path"
randbytes="java -cp $jar_path org.cgrep.util.RandomBytesTool"

seed="123"
size="1G"

echo "Using '$jar_path' JAR"
echo "Generating $size of random text for each test"

test() {
  ($randbytes $seed $size | pv -f -a -s $size | $cgrep > /dev/null $*) 2>&1
}

report="performance-$version.txt"


echo "Performance test started!"

echo "TEST1" | tee -a $report
test !EVERYTHING! | tee -a $report
echo "TEST2" | tee -a $report
test -f/usr/share/dict/words | tee -a $report

echo "DONE"
