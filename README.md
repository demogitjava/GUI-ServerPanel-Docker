
Simple GUI Docker  Panel
============================================================   


![enter image description here](https://github.com/demogitjava/demodatabase/blob/master/GUI-ServerPanel/ServerGUI-Panel.jpg?raw=true)

the docker socket file has to on path

    /var/run/docker.sock


alternative start jar application with custome look and fell from commandline
Files are available
http://demogitjava.ddns.net:8000/java-ide/
copy jar and theme files to root 

for snow theme

> QT_QPA_PLATFORM=minimal -J-Dnimrodlf.themeFile=/root/Snow.theme --cp:p
> /root/nimrodlf-1.2d.jar Guiserverpanel-0.0.1-SNAPSHOT.jar

for DarkTabaco

> QT_QPA_PLATFORM=minimal -J-Dnimrodlf.themeFile=/root/DarkTabaco.theme
> --cp:p /root/nimrodlf-1.2d.jar Guiserverpanel-0.0.1-SNAPSHOT.jar



compile project with

> mvn package

used java jdk is 
open jdk 11
