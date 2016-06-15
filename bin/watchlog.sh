level="$1"
if [ "$1" = "" ];then
        $level="info"
tail -F ../logs/codecat-$level.log
