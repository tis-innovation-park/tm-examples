#!/bin/sh

exit_on_failure() 
{
    RET_VAL=$1
    COMMENT=$2

    if [ "$RET_VAL" != "0" ]
    then
	echo "ERROR: $RET_VAL"
	echo $COMMENT
	exit $RET_VAL
    fi
}

DATE=`date +%Y-%m-%d`
XML_FILE="$CLIENT_NAME-$DATE.xml"

echo "<target name=\"$CLIENT_NAME\">" >> $XML_FILE
echo -e "\t<start-time>`date "+%Y-%m-%d %H:%I:%S"`</start-time>" >> $XML_FILE
echo -e "\t<client-start>$CLIENT_STARTED</client-start>" >> $XML_FILE

if [ "$CLIENT_STARTED" = "0" ]; then
    echo -e "\t<client-up>$CLIENT_UP</client-up>" >> $XML_FILE
    echo -e "\t<client-ssh>$CLIENT_SSH_UP</client-ssh>" >> $XML_FILE
    if [ "$CLIENT_SSH_UP" = "0" ]; then
        echo -e "\t<test>" >> $XML_FILE
        echo -e "\t\t<name>Bolzano</name>" >> $XML_FILE

        tm-vmm --client-copy-file selenium-server-standalone-2.37.0.jar $CLIENT_NAME:~/selenium-server-standalone-2.37.0.jar
        exit_on_failure $? "Copying selenium-server jar to $CLIENT_NAME"

        tm-vmm --client-exec $CLIENT_NAME "killall java; DISPLAY=:0 nohup java -jar selenium-server-standalone-2.37.0.jar > out 2> err < /dev/null &"
        exit_on_failure $? "Start Selenium on $CLIENT_NAME"

        sleep 15
        exit_on_failure $? "Computer is not tired today"

        ant -Dtm.seleniumDriver=RemoteWebDriver -Dtm.seleniumUrl=http://localhost:4444/wd/hub -Dtm.testngOutput=test_results/$CLIENT_NAME-$DATE
        exit_on_failure $? "Executing ant build file"

        if [ -e "test_results/$CLIENT_NAME-$DATE/testng-failed.xml" ]; then
            echo -e "\t\t<result>1</result>" >> $XML_FILE
        else
            echo -e "\t\t<result>0</result>" >> $XML_FILE
        fi

        echo -e "\t\t<logfile>`readlink -f test_results/$CLIENT_NAME-$DATE/index.html`</logfile>" >> $XML_FILE
        echo -e "\t</test>" >> $XML_FILE

        echo -e "\t<client-stop>$?</client-stop>" >> $XML_FILE
        echo -e "\t<stop-time>`date "+%Y-%m-%d %H:%I:%S"`</stop-time>" >> $XML_FILE
    fi

    tm-vmm --stop-client $CLIENT_NAME

    echo "</target>" >> $XML_FILE
fi


