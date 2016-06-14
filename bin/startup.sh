#!/bin/sh  
#============ get the file name ===========
sudo service ufw stop
cd ..
path=`pwd`
folder=${path}"/lib"
main_jar=":monitor.jar"
all_jar=""
monitor_port=9524
for file in ${folder}/*; do
    temp_file=`basename $file`
    all_jar="${all_jar}:${folder}/${temp_file}"
done
main_class="org.code4j.codecat.monitor.run.Main"
cd "bin"
if [ "$1" = "" ];then
        java -cp $all_jar$main_jar $main_class $monitor_port
else
        java -cp $all_jar$main_jar $main_class $1
fi

