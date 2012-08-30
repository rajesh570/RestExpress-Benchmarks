#! /bin/bash
#
cat /dev/null > $4
ulimit -n 65535

for i in {100..2000..100}
do
	httperf --hog --timeout 5 --client 0/1 --server $1 --port $2 --wsesslog 10000,$3,mongoBenchTomcat.log --rate $i   >> $4

	portCount=`netstat -tna | grep $2 | wc -l`
	echo "Current Port Count:" $portCount
	
	if [ "$portCount" -ge "20000" ]; then
		echo "Pausing 666666 minutes for connections to bleed off..."
		sleep 6m
	fi
done
