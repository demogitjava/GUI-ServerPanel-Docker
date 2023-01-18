
Simple GUI Docker Client Panel
============================================================

used operating system in CENTOS Linux7

required is docker on the maschine
https://www.docker.com/

The used java jdk is 
https://www.graalvm.org/downloads/
in this case i use graalvm jdk 17

to run the modules over gui panel 
create a folder 

/root/IdeaProjects

------------------------------------------------
NGINX PROXY
to use nginx proxy you have to edit the 
default.conf on your docker container 
on path /etc/nginx/conf.d/default.conf
in the module demodatabase is a file with 
default.conf to edit it.

There is a simple 
http forward on a docker container 
with ip 192.168.178.4 if you have a 
docker network created.

The docker container with the ip 
192.168.178.4 is the landingpage container 
with ISO 8859-1 and the config in german.


On the module landingpage you have to start the 
module on command line with 

-p 127.0.0.1:80:80 --dns=192.168.178.3 --dns=8.8.8.8 --dns=8.8.4.4 
--dns-search=demogitjava.de 
--name=de_landingpage 
--hostname=demogitjava.ddns.net 
--add-host=demogitjava.ddns.net:217.160.255.254 
--network=192.168.178.0 

------------------------------------------------






for centos7 run:
yum install docker
sudo systemctl daemon-reload
sudo systemctl restart docker


This Docker GUI Client run the 
java modules with terminal commands.




