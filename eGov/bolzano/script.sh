tm-vmm --client-copy-file selenium-server-standalone-2.37.0.jar $CLIENT_NAME:~/selenium-server-standalone-2.37.0.jar
tm-vmm --client-exec $CLIENT_NAME "killall java; DISPLAY=:0 nohup java -jar selenium-server-standalone-2.37.0.jar > out 2> err < /dev/null &"
sleep 15
ant -Dtm.seleniumDriver=RemoteWebDriver -Dtm.seleniumUrl=http://localhost:4444/wd/hub
