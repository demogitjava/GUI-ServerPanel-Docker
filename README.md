
Simple GUI Docker  Panel
============================================================   


![enter image description here](https://github.com/demogitjava/demodatabase/blob/master/GUI-ServerPanel/ServerGUI-Panel.jpg?raw=true)

the docker socket file has to on path

    /var/run/docker.sock



edit Timezones on openwrt 

jgsoftwares/openwrt23.05landingpage:java11       "/bin/ash /root/runl?"   20 hours ago   Up 20 hours               openwrtlandingpage                                           
jgsoftwares/openwrt23.05derbydb:10-14-02         "/bin/ash /root/star?"   20 hours ago   Up 20 hours               openwrtderbydb                                               
jgsoftwares/openwrt23.05lanserver:11             "/bin/ash /root/LanS?"   23 hours ago   Up 23 hours               openwrtlanserver                                             
jgsoftwares/openwrtsshgnomex11:fluxboxnetbeans   "/bin/ash"               24 hours ago   Up 24 hours               openwrtx11ssh                                                
jgsoftwares/openwrt23.05:nftbridgelayer2ext4     "/bin/ash"               2 days ago     Up 2 days                 openwrt2305host  

update openwrt timezone
opkg update && opkg install zoneinfo-all

/etc/TZ
CET-1CEST,M3.5.0,M10.5.0/3

restart containers

check time with command 
date


used jdk is openjdk11

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
