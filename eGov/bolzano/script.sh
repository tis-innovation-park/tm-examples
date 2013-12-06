#!/bin/sh

exit_on_failure() 
{
    RET_VAL=$1
    COMMENT=$2

    if [ "$RET_VAL" = "0" ]
    then
	echo "ERROR: $RET_VAL"
	echo $COMMENT
	exit $RET_VAL
    fi
}

tm-vmm --client-copy-file selenium-server-standalone-2.37.0.jar $CLIENT_NAME:~/selenium-server-standalone-2.37.0.jar
exit_on_failure $? "Copying selenium-server jar to $CLIENT_NAME"

tm-vmm --client-exec $CLIENT_NAME "killall java; DISPLAY=:0 nohup java -jar selenium-server-standalone-2.37.0.jar > out 2> err < /dev/null &"
exit_on_failure $? "Start Selenium on $CLIENT_NAME"

sleep 15

ant -Dtm.seleniumDriver=RemoteWebDriver -Dtm.seleniumUrl=http://${CLIENT_IP}:4444/wd/hub
exit_on_failure $? "Executing ant build file"

