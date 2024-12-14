
Simple GUI Docker  Panel
============================================================   
![image](https://github.com/demogitjava/demodatabase/blob/master/screenshotguiserverpanel.jpg?raw=true)


the used java jdk is - oracle open jdk 21
http://demogitjava.ddns.net:8000/Java_JDK/openjdk-21.0.2_linux-x64_bin.tar.gz

alternative start jar application with custome look and fell from commandline
Files are available
http://demogitjava.ddns.net:8000/java-ide/
copy jar and theme files to root 

for snow theme
QT_QPA_PLATFORM=minimal -J-Dnimrodlf.themeFile=/root/Snow.theme --cp:p /root/nimrodlf-1.2d.jar Guiserverpanel-0.0.1-SNAPSHOT.jar

for DarkTabaco
QT_QPA_PLATFORM=minimal -J-Dnimrodlf.themeFile=/root/DarkTabaco.theme --cp:p /root/nimrodlf-1.2d.jar Guiserverpanel-0.0.1-SNAPSHOT.jar

The xterm window is in the moment not available over process id
it stated a xterm window and copy and paste the command over
the xterm window to run container or set systemtime.
install xterm on a docker container with

yum install xterm

to paste the commands from the GUI Window

gui window -> Strg + c
xterm -> schift + insert


to install docker on your maschine 
dnf install -y dnf-utils zip unzip
dnf config-manager --add-repo=https://download.docker.com/linux/centos/docker-ce.repo?
dnf install -y docker-ce --nobest
if install sucess and docker is running the the connect over shell is available


This Docker GUI Client run the    
java modules with terminal commands.


compile project with
mvn package

used java jdk is 
open jdk 11

