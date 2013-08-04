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

cgrep="java -jar $jar_path -v "
randbytes="java -cp $jar_path org.cgrep.util.RandomBytesTool"

seed="123"
size="1G"

echo "Using '$jar_path' JAR"
echo "Generating $size of random text for each test"

test() {
  echo "Cmdline: cgrep $*"
  ($randbytes $seed $size | pv -f -a -s $size | $cgrep > /dev/null $*) 2>&1
}

report="performance-$version.txt"


echo "Performance test started!"

(\
echo "TEST match everything" \
 && test - \
 && echo "TEST first column match /usr/share/dict/words" \
 && test -f/usr/share/dict/words \
) | tee $report
echo "DONE"
