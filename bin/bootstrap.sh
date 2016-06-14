#!/bin/sh  
#============ get the file name ===========  

cd ..
path=`pwd`
folder=${path}"/lib"
main_jar=":bootstrap.jar"
all_jar=""  
server_port=20000
for file in ${folder}/*; do  
    temp_file=`basename $file`  
    all_jar="${all_jar}:${folder}/${temp_file}"
done
main_class="org.code4j.codecat.realserver.run.ServerLauncher"
cd "bin"
if [ "$1" = "" ];then
	echo "no param"
	java -cp $all_jar$main_jar $main_class $server_port
else 
	echo "got param!!"
	java -cp $all_jar$main_jar $main_class $1
fi
