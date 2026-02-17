
Simple GUI Docker  Panel
============================================================   


![enter image description here](https://github.com/demogitjava/demodatabase/blob/master/GUI-ServerPanel/ServerGUI-Panel.jpg?raw=true)

the docker socket file has to on path

    the url is changed to tcp for wireguard vpn

    ----------------
    on java class dockerclient line 83
    dockerClient = DockerClientBuilder.getInstance("tcp://192.168.10.56:2375").build();
    
    --------------
   
    openwrt as hostplastform install with gparted 
    http://demogitjava.ddns.net:8000/openwrt/openwrt_installwithgparted



    for hostplatform is used openwrt 23.05 
    backup
    http://demogitjava.ddns.net:8000/backup-demogitjava.ddns.net-2025-11-06.tar.gz
    username root jj78mvpr52k1

   
    
 
    runs over wireguard with localhost on remote maschine with
    127.0.0.1/24 dev lo
   
    starting and stopping the container works
    


    /var/run/docker.sock
    

used jdk is openjdk11
http://demogitjava.ddns.net:8000/javajdk/openjdk-11.0.2_linux-x64_bin.tar.gz



---------------------

login over the host platform required 
on the hostplatform - openwrt the /etc/resolv.conf add nameservers

/etc/resolv.conf

nameserver 95.85.95.85
nameserver 2.56.220.2

---------------------



edit Timezones on openwrt 
jgsoftwares/ipfire:cloud                          "/bin/bash"              3 days ago     Up 47 seconds            ipfiredmz                         

-> as compose 
jgsoftwares/ipfire:cloud                          "/bin/bash"              3 days ago     Up 38 seconds            ipfire     

jgsoftwares/openwrt23.05landingpage:java11       "/bin/ash /root/runl?"   20 hours ago   Up 20 hours               openwrtlandingpage                                           
jgsoftwares/openwrt23.05derbydb:10-14-02         "/bin/ash /root/star?"   20 hours ago   Up 20 hours               openwrtderbydb                                               
jgsoftwares/openwrt23.05lanserver:11             "/bin/ash /root/LanS?"   23 hours ago   Up 23 hours               openwrtlanserver                                             
jgsoftwares/openwrtsshgnomex11:fluxboxnetbeans   "/bin/ash"               24 hours ago   Up 24 hours               openwrtx11ssh                                                
jgsoftwares/openwrt23.05:nftbridgelayer2ext4     "/bin/ash"               2 days ago     Up 2 days                 openwrt2305host  

update openwrt timezone
opkg update && opkg install zoneinfo-all

/etc/TZ
CET-1CEST,M3.5.0,M10.5.0/3


for http file server setup date
jgsoftwares/openwrthttpfileserver:latest         "/bin/ash"               2 hours ago    Up 48 minutes             openwrthttpfileserver  

setup timeserver
opkg install alpine-repositories
apk add --allow-untrusted tzdata
ln -s /usr/share/zoneinfo/Europe/Berlin /etc/localtime



run sandboxes in a docker container if openwrt2305host running
simple example to run a nsjail sandbox on an openwrt container 
-> from openwrt2305host
docker exec -it openwrtlandingpage /bin/ash
opkg install alpine-repositories
apk add --allow-untrusted nsjail
nsjail join=java


alternatives you can install nsjail over the alpine packages to start a sandbox in a container
apk add --allow-untrusted nsjail

show process with 
ps -a 
or 
ps -w

run process in a sandbox 
nsjail join=yourpid
or
nsjail join=java

 

restart containers

check time with command 
date



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
