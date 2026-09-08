
Simple GUI Docker  Panel
============================================================   


![enter image description here](https://github.com/demogitjava/demodatabase/blob/master/GUI-ServerPanel/ServerGUI-Panel.jpg?raw=true)


for docker swarm 
dmz config on ipfire to 10.255.255.1

create swarm with 
docker swarm init

delete network 
docker network rm docker_gwbridge
docker network rm ingress 


docker gwbridge

    docker network create --subnet 10.255.255.0/24 --opt com.docker.network.bridge.name=docker_gwbridge --opt com.docker.network.bridge.enable_icc=true --scope global --opt com.docker.network.bridge.enable_ip_masquerade=true docker_gwbridge

add interface to docker_gwbridge 

    root@demogitjava:~# brctl addif docker_gwbridge vlan0
    root@demogitjava:~# brctl show
    bridge name     bridge id               STP enabled     interfaces
    docker_gwbridge         8000.024222a8b2fb       no              vlan0



create ingress network docker

    docker network create --driver overlay --ingress --subnet 10.255.255.0/24 --scope global --gateway 10.255.255.1 --opt com.docker.network.driver.mtu=1500 ingress


    mainpanel
    network config
    root@demogitjava:~# docker network inspect ingress
    [
        {
            "Name": "ingress",
            "Id": "iwizhgp20glnkq2dtm0rgkyiq",
            "Created": "2026-09-04T08:31:47.021051136Z",
            "Scope": "swarm",
            "Driver": "overlay",
            "EnableIPv6": false,
            "IPAM": {
                "Driver": "default",
                "Options": null,
                "Config": [
                    {
                        "Subnet": "10.255.255.0/24",
                        "Gateway": "10.255.255.1"
                    }
                ]
            },
            "Internal": false,
            "Attachable": false,
            "Ingress": true,
            "ConfigFrom": {
                "Network": ""
            },
            "ConfigOnly": false,
            "Containers": null,
            "Options": {
                "com.docker.network.driver.overlay.vxlanid_list": "4098"
            },
            "Labels": null
        }
    ]

    docker_gwbridge /24 subnet 
     root@demogitjava:~# route -n
    Kernel IP routing table
    Destination     Gateway         Genmask         Flags Metric Ref    Use Iface
    0.0.0.0         10.255.255.1    0.0.0.0         UG    0      0        0 eth0
    10.255.255.0    0.0.0.0         255.255.255.0   U     0      0        0 docker_gwbridge
    192.168.10.0    0.0.0.0         255.255.255.0   U     0      0        0 wg0
    root@demogitjava:~# 

    root@demogitjava:~# 
    root@demogitjava:~# service
    Usage: service <service> [command]
    /etc/init.d/boot                   enabled         stopped
    /etc/init.d/cron                   enabled         stopped
    /etc/init.d/dnsmasq               disabled         stopped
    /etc/init.d/dockerd                enabled         stopped
    /etc/init.d/done                   enabled         stopped
    /etc/init.d/dropbear               enabled         stopped
    /etc/init.d/firewall               enabled         stopped
    /etc/init.d/gpio_switch            enabled         stopped
    /etc/init.d/led                   disabled         stopped
    /etc/init.d/log                   disabled         stopped
    /etc/init.d/network               disabled         stopped
    /etc/init.d/odhcpd                disabled         stopped
    /etc/init.d/packet_steering        enabled         stopped
    /etc/init.d/rpcd                   enabled         running
    /etc/init.d/sysctl                 enabled         stopped
    /etc/init.d/sysfixtime             enabled         stopped
    /etc/init.d/sysntpd                enabled         stopped
    /etc/init.d/system                 enabled         stopped
    /etc/init.d/ttyd                  disabled         running
    /etc/init.d/ucitrack               enabled         stopped
    /etc/init.d/uhttpd                disabled         running
    /etc/init.d/umount                 enabled         stopped
    /etc/init.d/urandom_seed           enabled         stopped
    /etc/init.d/urngd                  enabled         running
    root@demogitjava:~# 


the docker socket file has to on path

the url is changed to tcp for wireguard vpn

 
    on java class dockerclient line 83
    dockerClient = DockerClientBuilder.getInstance("tcp://192.168.10.56:2375").build();
   
openwrt as hostplastform install with gparted 
    http://demogitjava.ddns.net:8000/openwrt/openwrt_installwithgparted


for hostplatform is used openwrt 23.05  backup
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




