package de.jgsoftwares.guiserverpanel.dao;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Network;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import java.io.BufferedReader;
import com.github.dockerjava.api.model.Capability;

import com.github.dockerjava.api.model.Isolation;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.core.command.PullImageResultCallback;
import de.jgsoftwares.guiserverpanel.frames.ConfigPanel;
import static de.jgsoftwares.guiserverpanel.frames.ConfigPanel.stcomboruntime;
import static de.jgsoftwares.guiserverpanel.frames.ConfigPanel.stinterfacename;
import static de.jgsoftwares.guiserverpanel.frames.ConfigPanel.styourdomainname;
import de.jgsoftwares.guiserverpanel.frames.LanServerTCP;
import de.jgsoftwares.guiserverpanel.frames.Landingpage;

import javax.swing.tree.DefaultMutableTreeNode;

import de.jgsoftwares.guiserverpanel.frames.MainPanel;
import de.jgsoftwares.guiserverpanel.frames.httpfileserver;
import java.io.File;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author hoscho
 */
public class dockerclient implements Idockerclient
{

    Process process;
    BufferedReader reader;

    // model for dockerclient for images and containers
    com.github.dockerjava.api.model.Image mdimage;
    com.github.dockerjava.api.model.Container mdcontainer;

    // copy jar to landingpage
    java.io.FileInputStream tarStream;
    
    
    PipedInputStream inputStream = null;
    PipedOutputStream outputStream = null;
   
    public static DockerClient dockerClient;
    //DockerClient dockerClient;

   
    
    // List of dockerimages
    // from /var/run/docker.sock
    public List<Image> dockerimages;
    public List<Container> dockercontainers;
    
    public dockerclient()
    {
       // over docker.sock
       //dockerClient = DockerClientBuilder.getInstance().build();  
       
        DockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
        .withDockerHost("tcp://192.168.10.56:2375")
        .withDockerTlsVerify(false)
        .build(); 
        
        
       // DockerClient dockerClient = DockerClientImpl.getInstance(dockerClientConfig);
       //dockerClient = DockerClientBuilder.getInstance("tcp://192.168.10.56:2375").build();
       setDockerClient(dockerClient, dockerClientConfig);

       dockerimages = dockerClient.listImagesCmd().exec();
       dockercontainers = dockerClient.listContainersCmd().exec();
    }
    
    
    // set Instance to 
    //dockerClient = DockerClientBuilder.getInstance("tcp://192.168.10.56:2375").build();
    public void setDockerClient(DockerClient dockerClient, DockerClientConfig dockerClientConfig) {
        
       // this.dockerClient = DockerClientBuilder.getInstance("tcp://192.168.10.56:2375").build();
       this.dockerClient = DockerClientBuilder.getInstance(dockerClientConfig).build();
    }
    
     
    // return Instance  
    //dockerClient = DockerClientBuilder.getInstance("tcp://192.168.10.56:2375").build();
    public void getDockerClient(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }
    
     

    /**
     *
     * @return
     */
    @Override
    public DockerClient getDockerClient() {
        return dockerClient;
    }
    
 
    /**
     *
     * @param url
     */
    @Override
    public void loadDockerimage(String url)
    {
        final String tarFilePath = "http://demogitjava.ddns.net:8000/openwrt/dockerimages/httpfileserver.tar";
      
        try (InputStream is = new FileInputStream(tarFilePath)) {
            dockerClient.loadImageCmd(is).exec();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(dockerclient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(dockerclient.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("Loaded");
        
    }
    
    /*
       restart docker container over the 
       jtree menu 
    */
    /**
     *
     * @param stdockerclient
     */
    @Override
    public void startcontainerdockerclient(String stdockerclient)
    {
        InspectContainerResponse container = dockerClient.inspectContainerCmd("" + stdockerclient.toString()).exec();
        dockerClient.restartContainerCmd(container.getId()).exec();
        //System.out.println("Container " + container.getName() + " restarted");
       
        
    }
    
    /**
     *
     * @param stdockerclient
     */
    @Override
    public void attachContainer(String stdockerclient)
    {

          // String stdockerclient is the container name 
          
          dockerClient.attachContainerCmd("" + stdockerclient.toString())
            .withStdErr(true)
            .withStdOut(true)
            .withFollowStream(true);
            
           //.exec(callback)
            //.awaitCompletion(15, TimeUnit.SECONDS);
 /*
            com.github.dockerjava.api.model.Container container = new com.github.dockerjava.api.model.Container();
            container.equals(stdockerclient);
        
            
            JTerminal jterm = new JTerminal("" + stdockerclient.toString(),120,40);
            jterm.setLocation(100,100);
	    jterm.setVisible(true);
		
            jterm.add(jterm, container);
            jterm.setSize(400, 600);
            jterm.setVisible(true);

          */   
    }
    
    /**
     *
     * @param stdockerclient
     */
    @Override
    public void stopcontainerdockerclient(String stdockerclient)
    {
        InspectContainerResponse container = dockerClient.inspectContainerCmd("" + stdockerclient.toString()).exec();
        dockerClient.stopContainerCmd(container.getId()).exec();
        //System.out.println("Container " + container.getName() + " stoped");
    }
    
    /**
      * @param stdockerclient
     */
    @Override
    public void restartcontainerdockerlclient(String stdockerclient)
    {
        InspectContainerResponse container = dockerClient.inspectContainerCmd("" + stdockerclient.toString()).exec();
        
        
        //ethtool -s eth0 speed 100 duplex half
        
        dockerClient.restartContainerCmd(container.getId()).exec();
        //System.out.println("Container " + container.getName() + " restarted");
    }
    
    /**
     * @param stdockerclient
     */
    @Override
    public void removecontainerdockerclient(String stdockerclient)
    {
        InspectContainerResponse container = dockerClient.inspectContainerCmd("" + stdockerclient.toString()).exec();
        dockerClient.removeContainerCmd(container.getId()).exec();
        //System.out.println("Container " + container.getName() + " removed");
    }
    
    
    /**
    *
    * @param stcontainername
    * @param stcontainerid
    * @return
    */
    @Override
    public String getMenuItem(String stcontainername, String stcontainerid)
    {
        // get containername 
        // get containerid
        
        // to restart the container
        
        return stcontainername + stcontainerid;
    }
     /**
     *
     * @return
     */
    @Override
    public List<Image> getDockerimages() {
        return dockerimages;
    }

     /**
     *
     * @return
     */
    @Override
    public List<Container> getDockercontainers() {
        return dockercontainers;
    }

   
    
    
     /**
     *
     */
    @Override
    public void listContainers()
    {

  
    }

    
    public void jtreemenu()
    {
        
    }
    
   
     /**
     *
     */
    @Override
    public void startdockerclient()
    {

        
        // Docker client
        // default unix:///var/run/docker.sock
        //dockerClient = DockerClientBuilder.getInstance().build();    	
        
        dockerimages = dockerClient.listImagesCmd().exec();
        
        dockercontainers = dockerClient.listContainersCmd().exec();
        
        // get size of images
        int imagesize = dockerimages.size();
        
        // get size of containers
        int containersize = dockercontainers.size();

        // list containers
        try {
                for(int i = 0; i < containersize; i++)
                {
                    mdcontainer = new com.github.dockerjava.api.model.Container();
                    mdcontainer = dockercontainers.get(i);
                    
                    String strcontainers = Arrays.toString(mdcontainer.getNames());
                    MainPanel.dockercontainers.add(new DefaultMutableTreeNode(strcontainers));
                }  
        } catch (Exception e) {
            System.out.print("Error " + e);
        }

        // list images
        try {
             for(int i = 0; i < imagesize; i++)
                {
                    mdimage = new com.github.dockerjava.api.model.Image();
                    mdimage = dockerimages.get(i);          
                    String strepotage = Arrays.toString(mdimage.getRepoTags());
                    MainPanel.dockerimages.add(new DefaultMutableTreeNode(strepotage));
                }
                
        } catch (Exception e) {
          System.out.print("Error " + e);
        }
     
    }
    
    
     /**
     *
     * @param struncontainer
     */
    @Override
    public void startlanservercontiner(String struncontainer)
    {
        
        String contsystem = de.jgsoftwares.guiserverpanel.frames.ConfigPanel.stcontainersystem;
        String stinstall = null;
        String sttime = null;
        String stimage = null;
        String stimagetag = null;
        String struncmdst = null;
        String stcontainername = null;
        String stshell = null;
        // simle container config 
        if (contsystem.equals("openwrt")) 
        {
	   stinstall = "apk add --allow-untrusted tzdata";
                // ln -s /usr/share/zoneinfo/Europe/Brussels /etc/localtime
           sttime = "echo CET-1CEST,M3.5.0,M10.5.0/3 > /etc/TZ";
           // "sh", "-c", "echo 'test:111' | chpasswd")
           // .withCmd("sh", "-c", "echo 'test:111' | chpasswd")

         
           stimage = "jgsoftwares/openwrt23.05lanserver";
           stimagetag = ConfigPanel.stjavaversion;
           stshell = "/bin/ash";
           struncmdst = "/root/LanServer.sh";
    
           stcontainername = "openwrtlanservertcp";
        } else if(contsystem.equals("oraclelinux")) {
                stinstall = "";
                // ln -s /usr/share/zoneinfo/Europe/Brussels /etc/localtime
                sttime = "timedatectl set-timezone Europe/Berlin";
                // jgsoftwares/oraclelinux_openjdk_landingpage:hostopenwrtext4 /bin/bash /root/runlandingpage.sh
                stimage = "jgsoftwares/oraclelinux_openjdk_lanservertcp";
                stimagetag = "hostopenwrtext4";
                stshell = "/bin/bash";
                struncmdst = "/root/LanServer.sh";
                stcontainername = "oraclelinuxlanserver";
        }   
        else if(contsystem.equals("alpinelinux")) {
                //do this code
                stinstall = "apk add tzdata";
                // ln -s /usr/share/zoneinfo/Europe/Brussels /etc/localtime
                sttime = "echo ln -s /usr/share/zoneinfo/Europe/Berlin > /etc/localtime";
                //docker pull jgsoftwares/alpinelinux_landingpage:edgehost
                stimage = "jgsoftwares/alpinelinux_lanserver_tcp";
                stimagetag = "11";
                stshell = "/bin/ash";
                struncmdst = "";
                stcontainername = "h2lanservertcp";
        }      
        
           try
        {
       
                //ExposedPort tcp8443 = ExposedPort.tcp(8443);   
                  // show java version
                // > 26 java version http3 with upd 
                String showjavaversion = ConfigPanel.stjavaversion;
                Integer intjavaversion = Integer.parseInt(showjavaversion);
                ExposedPort tcp8443 = null;
                if(intjavaversion > 24)
                {
                    tcp8443 = ExposedPort.tcp(8443);
                    System.out.print("landingpage is started with Java JDK" + showjavaversion + "\n");
                    System.out.print("to java > 25 tcp is required for http2" + "\n");
                    System.out.print("container is started with tcp config on port 80");
                }
                else
                {
                    tcp8443 = ExposedPort.udp(8443);
                    System.out.print("landingpage is started with Java > 25" + showjavaversion + "\n");
                    System.out.print("the landingpage with java version > 25 ist started with port udp over http on port 80 " + "\n");
                }
                
                
                
                Ports portBindings = new Ports();
                portBindings.bind(tcp8443, Ports.Binding.bindPort(8443));     
                // connect to network like eth0 or eth0.10
                Network network = dockerClient.inspectNetworkCmd().withNetworkId(stinterfacename).exec();
                
                HostConfig hostConfig = HostConfig.newHostConfig().withPortBindings(PortBinding.parse("8443:8443"));
                System.out.print("start Lanserver with hostConfig " + "\n");
                
                
                
                hostConfig.withNetworkMode(stinterfacename);
                hostConfig.getNetworkMode();
                System.out.print("on interface " + hostConfig.getNetworkMode() + "\n");
                
                
                //hostConfig.withCapAdd(com.github.dockerjava.api.model.Capability.NET_ADMIN)
                hostConfig.withCapAdd(Capability.NET_ADMIN);
                hostConfig.withCapAdd(Capability.NET_RAW);
                hostConfig.withCapAdd(Capability.SYS_ADMIN);
                hostConfig.getCapAdd();
                System.out.print("with Capability" + hostConfig.getCapAdd() + "\n");
                
                
                
                hostConfig.isUserDefinedNetwork();
                
                hostConfig.withPrivileged(Boolean.TRUE);
                hostConfig.getPrivileged();
                System.out.print("with privileged mode " + hostConfig.getPrivileged() + "\n");
                
                //Isolation.PROCESS.getValue();
                //String ipcmode = "private";
               
                 // isolation process
                hostConfig.withIsolation(Isolation.DEFAULT);
                hostConfig.getIsolation();
                System.out.print("with isolation on linux openwrt default only is supported " + hostConfig.getIsolation() + "\n");
                
                // ipc mode
                hostConfig.withIpcMode("host");
                hostConfig.getIpcMode();
                System.out.print("with ipc mode" + hostConfig.getIpcMode() + "\n");
                
                
                // cgroup host
                hostConfig.withCgroup("host");
                hostConfig.getCgroup();
                System.out.print("with cgroup " + hostConfig.getCgroup() + "\n");
                
              
             
                hostConfig.getBinds();
                hostConfig.getDevices();
                
                
                 // dns server config
                de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig publicdnsserverconfig = new de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig();
           
                String stdnsserver = ConfigPanel.stpubdnsserver;
                // returns string dnspulicserver ipdns1 ipdns2

                String stdns1 = null;
                String stdns2 = null;

                publicdnsserverconfig.publicdns(stdnsserver, stdns1, stdns2);

                stdns1 = publicdnsserverconfig.getStdns1();
                stdns2 = publicdnsserverconfig.getStdns2();
                
                
                // add dns String 
                String[] stdns = new String [] {stdns1,stdns2};
                hostConfig.withDns(stdns);
                hostConfig.getDns();
                System.out.print("with dns " + hostConfig.getDns() + "\n");
                
                
                hostConfig.getDnsSearch();
                hostConfig.withRuntime(stcomboruntime);
                System.out.print("with runtime " + hostConfig.getRuntime() + "\n");
              
                  // set kernel memory to max
                hostConfig.withKernelMemory(Long.MAX_VALUE);
                hostConfig.getKernelMemory();
                System.out.print("with kernel memory "+ hostConfig.getKernelMemory() + "\n");
                
                hostConfig.withMemory(Long.MAX_VALUE);
                //Isolation.HYPERV.getValue();   
                hostConfig.getMemoryReservation();
                hostConfig.getMemory();
                System.out.print("with memory " + hostConfig.getMemory() + "\n");
                
                hostConfig.withMemorySwap(Long.MAX_VALUE);
                hostConfig.getMemorySwap();
                System.out.print("with memory Swap " + hostConfig.getMemorySwap() + "\n");

                hostConfig.withCpuShares(0);
                hostConfig.getCpuShares();
                System.out.print("landingpage cpushare - firewall config to " + hostConfig.getCpuShares() + "\n");
                
                
              
                
           // dockerClient.pullImageCmd(stimage)
            //    .withTag(stimagetag)
             //   .exec(new PullImageResultCallback())
             //   .awaitCompletion(30, TimeUnit.SECONDS);
            
            // check image exist
              
            boolean imagenotexist = false;
            try
            {
                dockerClient.inspectContainerCmd(stcontainername).exec();
            } catch(NotFoundException e)
            {
                System.out.print("error search image " + e);
            }
            if(imagenotexist == true)
            {
                System.out.print("lanserver image exist" + "\n");
            }
            else
            {
                
                String stjavaversion = ConfigPanel.stjavaversion.toString();
                String stlanserverexist = "jgsoftwares/openwrt23.05lanserver:" + stjavaversion;
                dockerClient.pullImageCmd(stlanserverexist).exec(new PullImageResultCallback()).awaitSuccess();
            }
             
             
             
        
            switch(contsystem)
            {
                case "openwrt":
                {
                    
                    
                  //dockerClient = DockerClientBuilder.getInstance().build();    
                  getDockerClient(dockerClient);
                  
                  CreateContainerResponse container = dockerClient.createContainerCmd(stimage + ":" + stimagetag)
                 .withCmd("/bin/ash", "/root/LanServer.sh")
                 .withName("openwrtlanservertcp")
                 .withUser("root") 
                 .withHostConfig(hostConfig)
                 .withExposedPorts(tcp8443)
                // .withExposedPorts(tcp1527)
                 .withDomainName(styourdomainname)
                 //.withIpv4Address(stwanip)
                 .withStdinOpen(Boolean.TRUE)
                 .withAttachStderr(true)
                 .withAttachStdin(true)
                 .withAttachStdout(true)
                 .withWorkingDir("/root")
                 .exec();
                  
            
                 dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec(); 

                 // start container openwrtlanserver
                 dockerClient.startContainerCmd(container.getId()).exec();
               
                    /*
                
                clear files in the docker container 
                openwrt2305host
             */
             // cat /dev/null > /etc/hosts
             String clearhostsfile = "/etc/hosts";
             ExecCreateCmdResponse execlearhost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearhostsfile).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file /etc/hosts" + "\n");
             
             // cat /dev/null > /etc/sysctl.conf
             String clearsysctl = "/etc/sysctl.conf";
             ExecCreateCmdResponse execlearsysctl = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearsysctl).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearsysctl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file /etc/sysctl.conf" + "\n");    
             
             // resolv.conf
             // cat /dev/null > /etc/resolv.conf
             String clearresolvconf = "/etc/resolv.conf";
             ExecCreateCmdResponse execlearresolvconf = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearresolvconf).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearresolvconf.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file /etc/resolv.conf " + "\n");    
             
              // TZ
             // cat /dev/null > /etc/TZ
             String cleartimezone = "/etc/TZ";
             ExecCreateCmdResponse execleartimezone = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + cleartimezone).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execleartimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file /etc/TZ" + "\n");
             
             // clear /etc/hostname
             // cat /dev/null > /etc/hostname
             String clearhostname = "/etc/hostname";
             ExecCreateCmdResponse execlearhostname = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearhostname).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhostname.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file /etc/hostname" + "\n");
             /*
             
                write config to docker container 
                openwrtlandingpage
             
             */
             
             // add string with wanip and hostname
             String sthost = (String) ConfigPanel.stwanip + "  " + ConfigPanel.styourdomainname;
             ExecCreateCmdResponse execaddstringtohost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + sthost + " >> /etc/hosts").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtohost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/hosts" + sthost + "\n");
             
            // /etc/sysctl.conf
             // add string to /etc/sysctl.conf
             // net.ipv6.conf.all.disable_ipv6=0
             // net.ipv4.conf.all.src_valid_mark=0
             // net.ipv4.ip_forward=0   
             String noforward = "net.ipv4.ip_forward=0";
             ExecCreateCmdResponse execaddstringtosyscontl = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noforward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtosyscontl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/sysctl.conf" + noforward + "\n");
             
             
             String nosrcvalid = "net.ipv4.conf.all.src_valid_mark=0";
             ExecCreateCmdResponse execaddstringnosourcevalid = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + nosrcvalid + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnosourcevalid.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/sysctl.conf" + nosrcvalid + "\n");
             
             String noipv6forward = "net.ipv6.conf.all.disable_ipv6=0";
             ExecCreateCmdResponse execaddstringnoipv6forward = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noipv6forward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnoipv6forward.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/sysctl.conf" + noipv6forward + "\n");
             
             
             String nodynamicaddr = "net.ipv4.ip_dynaddr=0";
             ExecCreateCmdResponse execaddstringnodynamicaddr = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + nodynamicaddr + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnodynamicaddr.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/sysctl.conf " + nodynamicaddr + "\n");
             
             
             // get Timezone String 
             String sttimezonecountry = ConfigPanel.stcomboboxtimezone;
             
             //load timezoneconfig
             // write timezone string to /etc/TZ
            
             de.jgsoftwares.guiserverpanel.config.Timezoneconfig tmconfig = new de.jgsoftwares.guiserverpanel.config.Timezoneconfig();     
             String stgettimezone = tmconfig.timezoneopenwrt(sttimezonecountry, cleartimezone);
             ExecCreateCmdResponse execaddstringtotimezone = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + stgettimezone + " >> /etc/TZ").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtotimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add Stirng to /etc/TZ " + stgettimezone + "\n");

            // resolv.conf
             //String stdns1 = null;
             //String stdns2 = null;
             
           
             
         
             //nameserver dnsip1
             ExecCreateCmdResponse execaddstringpublicdnsip1 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "nameserver " + stdns1 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip1.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/resolv.conf " + stdns1 + "\n");
             
             //nameserver dnsip2
             ExecCreateCmdResponse execaddstringpublicdnsip2 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "nameserver " + stdns2 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip2.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/resolv.conf " + stdns2 + "\n");
             // add search with domainname
             ExecCreateCmdResponse execaddstringsearchdomain = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "search " + ConfigPanel.styourdomainname + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringsearchdomain.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/resolv.conf" + ConfigPanel.styourdomainname + "\n");
             
             // interface name
             ExecCreateCmdResponse execaddstringinterface = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "interface orange0 " + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringinterface.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/resovl.conf " + "interface orange0" + "\n");
             
             // dnsec
             ExecCreateCmdResponse execaddstringdnssearch = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "DNSSEC=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnssearch.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/resolv.conf" + "DNSSEC=yes" + "\n");

             // dnsovertls
             ExecCreateCmdResponse execaddstringdnsovertls = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "DNSOverTLS=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnsovertls.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/resolv.conf " + "DNSOverTLS=yes" + "\n");
             
             
             // /etc/hostname
             // add hostname for openwrt2305
             ExecCreateCmdResponse execaddstringhostname = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "openwrtlanserver" + " >> /etc/hostname").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringhostname.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/hostname " + "openwrtlanserver "+  "\n");
    
             // run command to update the date time
             // opkg update && opkg install zoneinfo-all
             ExecCreateCmdResponse execrunzoneinfo = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "opkg update && opkg install zoneinfo-all").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execrunzoneinfo.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("install packages to openwrtlanserver container  -  opkg update && opkg install zoneinfo-all " + "\n");
             
             // install iptables firewall package
             // create dir
             // /var/run -- for lock file for iptables
             // opkg install iptables-legacy
             ExecCreateCmdResponse execreatedir = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "mkdir /var/run/").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execreatedir.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("create a directory "+ " /var/run" + "\n");
             
             
             ExecCreateCmdResponse execinstalliptables = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "opkg install iptables-legacy").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execinstalliptables.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("install packages to openwrtlanserver cotnaienr opkg install iptables-legacy");
             
             // iptables save
             ExecCreateCmdResponse execiptablessave = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "iptables-legacy-save").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execiptablessave.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("run command in container optables-legacy-save " + "\n");
             
             
            
             String stjavaversion = ConfigPanel.stjavaversion.toString();
             // jgsoftwares/openwrt23.05lanserver     11 
             dockerClient.commitCmd(stcontainername).withRepository("jgsoftwares/openwrt23.05lanserver").withTag(stjavaversion).exec();
             System.out.print("local image commit" + "jgsoftwares/openwrt23.05lanserver:" + stjavaversion + "\n");
     
             
             
             
             
                 break;
                }
                
                case "oraclelinux":
                {
                     //dockerClient = DockerClientBuilder.getInstance().build(); 
                     getDockerClient(dockerClient);
                  CreateContainerResponse container = dockerClient.createContainerCmd(stimage + ":" + stimagetag)
                 .withCmd("/bin/bash", "/root/LanServer.sh")
                 .withName("oraclelinuxlanserver")
                 .withUser("root") 
                 .withHostConfig(hostConfig)
                 
                 .withExposedPorts(tcp8443)
                // .withExposedPorts(tcp1527)
                 .withDomainName(styourdomainname)
                 //.withIpv4Address(stwanip)
                 .withStdinOpen(Boolean.TRUE)
                 
                 .withWorkingDir("/root")
                 .exec();
                  
                   dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec();    
         dockerClient.startContainerCmd(container.getId()).exec();
           String noforward = "net.ipv4.ip_forward=0";
         ExecCreateCmdResponse execaddstringtohost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noforward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
         dockerClient.execStartCmd(execaddstringtohost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
        
                  break;
                   
                }
                
                case "alpinelinux":
                {
                    // dockerClient = DockerClientBuilder.getInstance().build(); 
                     getDockerClient(dockerClient);
                  CreateContainerResponse container = dockerClient.createContainerCmd(stimage + ":" + stimagetag)
                 //.withCmd("/bin/bash", "/root/LanServer.sh")
                 .withName("h2lanservertcp")
                 .withUser("root") 
                 .withHostConfig(hostConfig)
                 
                 .withExposedPorts(tcp8443)
                // .withExposedPorts(tcp1527)
                 .withDomainName(styourdomainname)
                 //.withIpv4Address(stwanip)
                 .withStdinOpen(Boolean.TRUE)
                 
                 .withWorkingDir("/root")
                 .exec();
                  
                   dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec();    
         dockerClient.startContainerCmd(container.getId()).exec();
           String noforward = "net.ipv4.ip_forward=0";
         ExecCreateCmdResponse execaddstringtohost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noforward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
         dockerClient.execStartCmd(execaddstringtohost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
        
                  break;
                   
                }
                
                
                
                default:
                    System.out.print("no system seletec" + "\n");
                    break;
            }
           
            /*
            dockerClient = DockerClientBuilder.getInstance().build();    
            CreateContainerResponse container = dockerClient.createContainerCmd("jgsoftwares/openwrt23.05lanserver:11")
                 .withCmd("/bin/ash", "/root/LanServer.sh")
                 .withName("openwrtlanserver")
                 .withUser("root") 
                 .withHostConfig(hostConfig)
                 
                 .withExposedPorts(tcp8443)
                // .withExposedPorts(tcp1527)
                 .withDomainName(styourdomainname)
                 //.withIpv4Address(stwanip)
                 .withStdinOpen(Boolean.TRUE)
                 
                 .withWorkingDir("/root")
                 .exec();
            
         dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec();    
         dockerClient.startContainerCmd(container.getId()).exec();
*/
        } catch(Exception e)
        {
            System.out.print("Fehler " + e);
        }
    }
    
    
    // lanserver tcp container 
    // function restart 
     /**
     *
     * @param strestartcontainer
     */
    @Override
    public void restartlanserver(String strestartcontainer)
    {    
        String stdockerclient = "oraclelinuxlanservertcp";
        de.jgsoftwares.guiserverpanel.dao.dockerclient daodockerclient = new de.jgsoftwares.guiserverpanel.dao.dockerclient();
        daodockerclient.restartcontainerdockerlclient(stdockerclient);
    }


    // lanserver tcp set systemtime
     /**
     *
     */
    @Override
    public void setsystemtimetolanservertcp()
    {
           try {
				process = Runtime.getRuntime().exec("xterm -hold ");
				
	
        } catch(IOException e)
        {
            System.out.print("Error " +e);
        }
    }

    // start xterm window for derby db command
     /**
     *
     * @param stderbydb
     */
    @Override
    public void startderbydb(String stderbydb)
    {
        
        /*
            start a derbydb 
          
        */
        try
        {
          
          
                ExposedPort tcp1527 = ExposedPort.tcp(1527);
                Ports portBindings = new Ports();
                portBindings.bind(tcp1527, Ports.Binding.bindPort(1527));
                
              
                Network network = dockerClient.inspectNetworkCmd().withNetworkId(stinterfacename).exec();

                
                      // dns server config
                de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig publicdnsserverconfig = new de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig();
           
                String stdnsserver = ConfigPanel.stpubdnsserver;
                // returns string dnspulicserver ipdns1 ipdns2

                String stdns1 = null;
                String stdns2 = null;

                publicdnsserverconfig.publicdns(stdnsserver, stdns1, stdns2);

                stdns1 = publicdnsserverconfig.getStdns1();
                stdns2 = publicdnsserverconfig.getStdns2();
                
                
                
                HostConfig hostConfig = HostConfig.newHostConfig().withPortBindings(PortBinding.parse("1527:1527"));
                
                System.out.print("start network config for container derbydb " + "\n");
                System.out.print("port of derbydb is by default set to 1527 " + "\n");
                
                // add container to host network
                // add container to host network
                hostConfig.withNetworkMode(stinterfacename).getKernelMemory();
                hostConfig.getNetworkMode();
                System.out.print("networkmode " + hostConfig.getNetworkMode());
                
                //hostConfig.withCapAdd(com.github.dockerjava.api.model.Capability.NET_ADMIN)
                hostConfig.withCapAdd(Capability.NET_ADMIN);
                hostConfig.withCapAdd(Capability.NET_RAW);
                hostConfig.withCapAdd(Capability.SYS_ADMIN);
                hostConfig.getCapAdd();
                System.out.print("Capability " + hostConfig.getCapAdd().toString() + "\n");
                
                hostConfig.isUserDefinedNetwork();
                
                hostConfig.withPrivileged(Boolean.TRUE);
                hostConfig.getPrivileged();
                System.out.print("Privileged Mode " + hostConfig.getPrivileged() + "\n");
                
                //Isolation.PROCESS.getValue();
                //Isolation.HYPERV.getValue();
                hostConfig.withMemory(Long.MAX_VALUE);
                //Isolation.HYPERV.getValue();   
                hostConfig.getMemoryReservation();
                hostConfig.getMemory();
                System.out.print("with memory " + hostConfig.getMemory() + "\n");
                
                //hostConfig.getBinds();
                //hostConfig.getDevices();
                
                     // add dns String 
                String[] stdns = new String [] {stdns1,stdns2};
                hostConfig.withDns(stdns);
                //hostConfig.withDns(stdns2);
                hostConfig.getDns();
                System.out.print("dns is set to " + hostConfig.getDns() + "\n");
                
                hostConfig.withDnsSearch(ConfigPanel.styourdomainname);
                hostConfig.getDnsSearch();    
                System.out.print("with dns search " + hostConfig.getDnsSearch() + "\n");
                
                hostConfig.withRuntime(stcomboruntime);
                hostConfig.getRuntime();
                System.out.print("with runtime " + hostConfig.getRuntime() + "\n");
               
                // set kernel memory to max
                hostConfig.withKernelMemory(Long.MAX_VALUE);
                hostConfig.getKernelMemory();
                System.out.print("with kernel memory "+ hostConfig.getKernelMemory() + "\n");
                
                 // isolation process
                hostConfig.withIsolation(Isolation.DEFAULT);
                hostConfig.getIsolation();
                System.out.print("start landingpage container with Isolation on linux openwrt only default is supported " + hostConfig.getIsolation() + "\n");
                
                // ipc mode
                hostConfig.withIpcMode("host");
                hostConfig.getIpcMode();  
                System.out.print("with IpcMode " + hostConfig.getIpcMode() + "\n");
                
                // cgroup host
                hostConfig.withCgroup("host");
                hostConfig.getCgroup();
                System.out.print("with Cgroup Mode " + hostConfig.getCgroup() + "\n");
                  
                  // set kernel memory to max
                hostConfig.withKernelMemory(Long.MAX_VALUE);
                hostConfig.getKernelMemory();
                System.out.print("used kernel memory " + hostConfig.getKernelMemory() + "\n");
                
                hostConfig.withCpuShares(0);
                hostConfig.getCpuShares();
                System.out.print("derbydb cpushare - firewall config to " + hostConfig.getCpuShares() + "\n");
                
                hostConfig.withMemorySwap(Long.MAX_VALUE);
                hostConfig.getMemorySwap();
                System.out.print("with memory Swap " + hostConfig.getMemorySwap() + "\n");
                
                String imageexist = null;
                String sttag = null;
                String stforcommit = null;
                // image exist
                imageexist = de.jgsoftwares.guiserverpanel.frames.ConfigPanel.stcontainersystem;
                if (imageexist.equals("openwrt")) 
                {
                    String stjavaversion = new String(ConfigPanel.stjavaversion.toString());
                   
                    switch(stjavaversion)
                    {
                        case "11":
                        {
                            System.out.print("Java version 11" + "\n");
                            imageexist = "jgsoftwares/openwrt23.05derbydb:10-14-02";
                            stforcommit = "jgsoftwares/openwrt23.05derbydb";
                            sttag = "10-14-02";
                            break;
                        }
                        case "17":
                        {
                            System.out.print("Java version is 17" + "\n");
                            imageexist = "jgsoftwares/openwrt23.05derbydb:10.16.1.1";
                            stforcommit = "jgsoftwares/openwrt23.05derbydb";
                            sttag = "10.16.1.1";
                            break;
                        }
                        case "21":
                        {
                            System.out.print("Java version is 21" + "\n");
                            imageexist = "jgsoftwares/openwrt23.05derbydb:10-17-01-21";
                            stforcommit = "jgsoftwares/openwrt23.05derbydb";
                            sttag = "10-17-01-21";
                            break;
                        }
                        case "25":
                        {
                            System.out.print("Java version is 25" + "\n");
                            imageexist = "jgsoftwares/openwrt23.05derbydb:10-17-01-25";
                            stforcommit = "jgsoftwares/openwrt23.05derbydb";
                            sttag = "10-17-01-25";
                            break;
                        }
                        case "27":
                        {
                            System.out.print("Java version is 27" + "\n");
                            imageexist = "jgsoftwares/openwrt23.05derbydb:10-17-01-27";
                            stforcommit = "jgsoftwares/openwrt23.05derbydb";
                            sttag = "10-17-01-27";
                            break;
                        }
                        default:
                            break;
                    }
                    
                     // check image exist
                    boolean imagenotexist = false;
                    try
                    {
                        dockerClient.inspectImageCmd(imageexist).exec();
                    } catch(NotFoundException e)
                    {
                        System.out.print("error search image " + e);
                    }
                    if(imagenotexist == true)
                    {
                        System.out.print("landingpage image exist" + "\n");
                    }
                    else
                    {

                        
                        String stderbydbversion = imageexist;
                        dockerClient.pullImageCmd(stderbydbversion).exec(new PullImageResultCallback()).awaitSuccess();
                        System.out.print("image not exist local pull image " + "\n");
                    }
                        System.out.print("pull image " + "\n");
                        dockerClient.pullImageCmd("jgsoftwares/openwrt23.05derbydb")
                                //.withTag("10-14-02")
                                .withTag(sttag)
                                .exec(new PullImageResultCallback())
                                .awaitCompletion(30, TimeUnit.SECONDS);
                        
                        
                        //  start container openwrt             
                        //dockerClient = DockerClientBuilder.getInstance().build();
                        getDockerClient(dockerClient);
                        System.out.print("start container " + "\n");

                        CreateContainerResponse container = dockerClient.createContainerCmd("jgsoftwares/openwrt23.05derbydb:" + sttag)
                             .withCmd("/bin/ash", "/root/startderbydb.sh")
                             .withName("openwrtderbydb")
                             .withHostConfig(hostConfig)

                            // .withExposedPorts(tcp1527)
                            // .withExposedPorts(tcp1527)
                             .withDomainName(styourdomainname)
                             .withAttachStderr(false)
                             .withAttachStdin(false)
                             .withAttachStdout(false)
                             //.withIpv4Address(stwanip)
                             //.withStdinOpen(Boolean.TRUE)
                             //.withWorkingDir("/root")
                             .exec();
                         dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec();     
                         dockerClient.startContainerCmd(container.getId()).exec();
            
                          // edit container settings 
             // /etc/TZ
             // /etc/hosts
             // /etc/resolv.conf
             // /etc/sysctl.conf
             //de.jgsoftwares.guiserverpanel.config.DockerContainerconfig dockercontainerconfig = new de.jgsoftwares.guiserverpanel.config.DockerContainerconfig();
             //dockercontainerconfig.openwrt2305host(stcomboruntime);
           
             
              /*
                
                clear files in the docker container 
                openwrt2305host
             */
             // cat /dev/null > /etc/hosts
             String clearhostsfile = "/etc/hosts";
             ExecCreateCmdResponse execlearhost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearhostsfile).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush host file " + "/dev/null > " + clearhostsfile);
             
             // cat /dev/null > /etc/sysctl.conf
             String clearsysctl = "/etc/sysctl.conf";
             ExecCreateCmdResponse execlearsysctl = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearsysctl).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearsysctl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file /dev/null > " + clearsysctl + "\n");
             
             // resolv.conf
             // cat /dev/null > /etc/resolv.conf
             String clearresolvconf = "/etc/resolv.conf";
             ExecCreateCmdResponse execlearresolvconf = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearresolvconf).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearresolvconf.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file resolv.conf" + "\n");
             
              // TZ
             // cat /dev/null > /etc/TZ
             String cleartimezone = "/etc/TZ";
             ExecCreateCmdResponse execleartimezone = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + cleartimezone).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execleartimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush flie /etc/TZ" + "\n");
             
             // clear /etc/hostname
             // cat /dev/null > /etc/hostname
             String clearhostname = "/etc/hostname";
             ExecCreateCmdResponse execlearhostname = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearhostname).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhostname.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file " + clearhostname + "\n"); 
             
             /*
             
                write config to docker container 
                openwrt2305host
             
             */
             
             // add string with wanip and hostname
             String sthost = (String) ConfigPanel.stwanip + "  " + ConfigPanel.styourdomainname;
             ExecCreateCmdResponse execaddstringtohost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + sthost + " >> /etc/hosts").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtohost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to file /etc/hosts" + sthost + "\n");
             
             
            // /etc/sysctl.conf
             // add string to /etc/sysctl.conf
             // net.ipv6.conf.all.disable_ipv6=0
             // net.ipv4.conf.all.src_valid_mark=0
             // net.ipv4.ip_forward=0   
             String noforward = "net.ipv4.ip_forward=0";
             ExecCreateCmdResponse execaddstringtosyscontl = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noforward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtosyscontl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/sysctl.conf" + noforward + "\n");
             
             
             String nosrcvalid = "net.ipv4.conf.all.src_valid_mark=0";
             ExecCreateCmdResponse execaddstringnosourcevalid = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + nosrcvalid + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnosourcevalid.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/sysctl.conf " + nosrcvalid  + "\n");
             
             String noipv6forward = "net.ipv6.conf.all.disable_ipv6=0";
             ExecCreateCmdResponse execaddstringnoipv6forward = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noipv6forward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnoipv6forward.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/sysctl.conf " + noipv6forward + "\n");
             
             String nodynamicaddr = "net.ipv4.ip_dynaddr=0";
             ExecCreateCmdResponse execaddstringnodynamicaddr = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + nodynamicaddr + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnodynamicaddr.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/sysctl.conf" + nodynamicaddr + "\n");
             
             
             // get Timezone String 
             String sttimezonecountry = ConfigPanel.stcomboboxtimezone;
             
             //load timezoneconfig
             // write timezone string to /etc/TZ
            
             de.jgsoftwares.guiserverpanel.config.Timezoneconfig tmconfig = new de.jgsoftwares.guiserverpanel.config.Timezoneconfig();     
             String stgettimezone = tmconfig.timezoneopenwrt(sttimezonecountry, cleartimezone);
             ExecCreateCmdResponse execaddstringtotimezone = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + stgettimezone + " >> /etc/TZ").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtotimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/TZ" + stgettimezone + "\n");

            // resolv.conf
             //String stdns1 = null;
             //String stdns2 = null;
             
           
             
             
         
             //nameserver dnsip1
             ExecCreateCmdResponse execaddstringpublicdnsip1 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "nameserver " + stdns1 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip1.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/resolv.conf " + stdns1 + "\n");
             
             //nameserver dnsip2
             ExecCreateCmdResponse execaddstringpublicdnsip2 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "nameserver " + stdns2 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip2.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/resolv.conf" + stdns2 + "\n");
             
             // add search with domainname
             ExecCreateCmdResponse execaddstringsearchdomain = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "search " + ConfigPanel.styourdomainname + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringsearchdomain.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/resolv.conf " + ConfigPanel.styourdomainname + "\n");
             
             
             // interface name
             ExecCreateCmdResponse execaddstringinterface = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "interface orange0 " + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringinterface.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/resolv.conf" + "interface orange0 " + "\n");
             
             
             // dnsec
             ExecCreateCmdResponse execaddstringdnssearch = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "DNSSEC=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnssearch.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/resolv.conf " + "DNSSEC=yes" + "\n");
             
             // dnsovertls
             ExecCreateCmdResponse execaddstringdnsovertls = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "DNSOverTLS=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnsovertls.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/resolv.conf" + "DNSOverTLS=yes" + "\n");
             
             // /etc/hostname
             // add hostname for derbydb openwrt
             ExecCreateCmdResponse execaddstringhostname = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "openwrtderbydb" + " >> /etc/hostname").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringhostname.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/hostname" + "openwrthostname" + "\n");
             
             
             // run command to update the date time
             // opkg update && opkg install zoneinfo-all
             ExecCreateCmdResponse execrunzoneinfo = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "opkg update && opkg install zoneinfo-all").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execrunzoneinfo.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("run command opkg update && opkg install zoneinfo-all" + "\n");
             
             // install iptables firewall package
            // create dir
            // /var/run -- for lock file for iptables
            // opkg install iptables-legacy
            ExecCreateCmdResponse execreatedir = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "mkdir /var/run/").withAttachStdout(true).withAttachStderr(true).exec();
            dockerClient.execStartCmd(execreatedir.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
            System.out.print("create a diretory /var/run " + "\n");    
            
            
            ExecCreateCmdResponse execinstalliptables = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "opkg install iptables-legacy").withAttachStdout(true).withAttachStderr(true).exec();
            dockerClient.execStartCmd(execinstalliptables.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
            System.out.print("install package iptables-legacy " + "\n");
            
            // iptables save
            ExecCreateCmdResponse execiptablessave = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "iptables-legacy-save").withAttachStdout(true).withAttachStderr(true).exec();
            dockerClient.execStartCmd(execiptablessave.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
            System.out.print("run command in container " + "iptables-legacy-save");
            System.out.print("restart container openwrt2305host to run iptables in memory" + "\n");
             
             // name for running container 
             // commit container local with setting in /etc
             // restart container settings
             String stcontainername = "openwrtderbydb";
             dockerClient.commitCmd(stcontainername).withRepository(stforcommit).withTag(sttag).exec();
             System.out.print("local image commit" + stforcommit + " " + sttag + "\n");
             
                }
                else if (imageexist.equals("oraclelinux")) 
                {
                    imageexist = "jgsoftwares/oraclelinux_openjdk_derbydb:openwrtext4";
                    //InspectImageResponse response = dockerClient.inspectImageCmd(stderbydb).exec();
                   // if(response.equals(imageexist))
                   // {
                     //   System.out.print("Image oracle - derbydb image exist");
                  //  }
                   // else
                   // {
                        System.out.print("pull image " + "\n");
                        dockerClient.pullImageCmd("jgsoftwares/oraclelinux_openjdk_derbydb")
                                .withTag("openwrtext4")
                                .exec(new PullImageResultCallback())
                                .awaitCompletion(30, TimeUnit.SECONDS);  
                        
                         //  start container openwrt             
                        //dockerClient = DockerClientBuilder.getInstance().build();
                         getDockerClient(dockerClient);
                        System.out.print("start container " + "\n");

                        // openwrtext4
                        CreateContainerResponse container = dockerClient.createContainerCmd("jgsoftwares/oraclelinux_openjdk_derbydb:openwrtext4")
                             .withCmd("/bin/bash", "/root/startderbydb.sh")
                             .withName("oraclelinuxderbydb")
                             .withHostConfig(hostConfig)

                            // .withExposedPorts(tcp1527)
                            // .withExposedPorts(tcp1527)
                             .withDomainName(styourdomainname)
                             .withAttachStderr(false)
                             .withAttachStdin(false)
                             .withAttachStdout(false)
                             //.withIpv4Address(stwanip)
                             //.withStdinOpen(Boolean.TRUE)
                             //.withWorkingDir("/root")
                             .exec();
                         dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec();     
                         dockerClient.startContainerCmd(container.getId()).exec();
                         
                          // edit container settings 
             // /etc/TZ
             // /etc/hosts
             // /etc/resolv.conf
             // /etc/sysctl.conf
             //de.jgsoftwares.guiserverpanel.config.DockerContainerconfig dockercontainerconfig = new de.jgsoftwares.guiserverpanel.config.DockerContainerconfig();
             //dockercontainerconfig.openwrt2305host(stcomboruntime);
           
             
              /*
                
                clear files in the docker container 
                openwrt2305host
             */
             // cat /dev/null > /etc/hosts
             String clearhostsfile = "/etc/hosts";
             ExecCreateCmdResponse execlearhost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearhostsfile).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
           
             // cat /dev/null > /etc/sysctl.conf
             String clearsysctl = "/etc/sysctl.conf";
             ExecCreateCmdResponse execlearsysctl = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearsysctl).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearsysctl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
                 
             // resolv.conf
             // cat /dev/null > /etc/resolv.conf
             String clearresolvconf = "/etc/resolv.conf";
             ExecCreateCmdResponse execlearresolvconf = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearresolvconf).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearresolvconf.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
                 
              // TZ
             // cat /dev/null > /etc/TZ
             String cleartimezone = "/etc/TZ";
             ExecCreateCmdResponse execleartimezone = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + cleartimezone).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execleartimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
              
             
             // clear /etc/hostname
             // cat /dev/null > /etc/hostname
             String clearhostname = "/etc/hostname";
             ExecCreateCmdResponse execlearhostname = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearhostname).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhostname.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
              
             /*
             
                write config to docker container 
                openwrt2305host
             
             */
             
             // add string with wanip and hostname
             String sthost = (String) ConfigPanel.stwanip + "  " + ConfigPanel.styourdomainname;
             ExecCreateCmdResponse execaddstringtohost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + sthost + " >> /etc/hosts").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtohost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             
            // /etc/sysctl.conf
             // add string to /etc/sysctl.conf
             // net.ipv6.conf.all.disable_ipv6=0
             // net.ipv4.conf.all.src_valid_mark=0
             // net.ipv4.ip_forward=0   
             String noforward = "net.ipv4.ip_forward=0";
             ExecCreateCmdResponse execaddstringtosyscontl = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noforward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtosyscontl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             String nosrcvalid = "net.ipv4.conf.all.src_valid_mark=0";
             ExecCreateCmdResponse execaddstringnosourcevalid = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + nosrcvalid + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnosourcevalid.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             String noipv6forward = "net.ipv6.conf.all.disable_ipv6=0";
             ExecCreateCmdResponse execaddstringnoipv6forward = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noipv6forward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnoipv6forward.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             String nodynamicaddr = "net.ipv4.ip_dynaddr=0";
             ExecCreateCmdResponse execaddstringnodynamicaddr = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + nodynamicaddr + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnodynamicaddr.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             // get Timezone String 
             String sttimezonecountry = ConfigPanel.stcomboboxtimezone;
             
             //load timezoneconfig
             // write timezone string to /etc/TZ
            
             de.jgsoftwares.guiserverpanel.config.Timezoneconfig tmconfig = new de.jgsoftwares.guiserverpanel.config.Timezoneconfig();     
             String stgettimezone = tmconfig.timezoneopenwrt(sttimezonecountry, cleartimezone);
             ExecCreateCmdResponse execaddstringtotimezone = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + stgettimezone + " >> /etc/TZ").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtotimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
          

            // resolv.conf
             //String stdns1 = null;
             //String stdns2 = null;
             
           
             
             
         
             //nameserver dnsip1
             ExecCreateCmdResponse execaddstringpublicdnsip1 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "nameserver " + stdns1 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip1.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             //nameserver dnsip2
             ExecCreateCmdResponse execaddstringpublicdnsip2 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "nameserver " + stdns2 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip2.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // add search with domainname
             ExecCreateCmdResponse execaddstringsearchdomain = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "search " + ConfigPanel.styourdomainname + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringsearchdomain.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // interface name
             ExecCreateCmdResponse execaddstringinterface = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "interface orange0 " + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringinterface.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // dnsec
             ExecCreateCmdResponse execaddstringdnssearch = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "DNSSEC=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnssearch.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // dnsovertls
             ExecCreateCmdResponse execaddstringdnsovertls = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "DNSOverTLS=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnsovertls.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
          
             // /etc/hostname
             // add hostname for oraclelinuxderbydb
             ExecCreateCmdResponse execaddstringhostname = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "oraclelinuxderbydb" + " >> /etc/hostname").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringhostname.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
          
                       
                         
                    //}
                }
                else if (imageexist.equals("alpinelinux")) 
                {
                    imageexist = "jgsoftwares/alpinelinux_derbydb:openwrtedgehost";
                    ///InspectImageResponse response = dockerClient.inspectImageCmd(stderbydb).exec();
                    //if(response.equals(imageexist))
                    //{
                       // System.out.print("Image alpine - derbydb image exist");
                    //}
                   // else
                    //{
                        System.out.print("pull image " + "\n");
                        dockerClient.pullImageCmd("jgsoftwares/alpinelinux_derbydb")
                                .withTag("openwrtedgehost")
                                .exec(new PullImageResultCallback())
                                .awaitCompletion(30, TimeUnit.SECONDS); 
                        
                        
                          //  start container openwrt             
                        //dockerClient = DockerClientBuilder.getInstance().build();
                         getDockerClient(dockerClient);
                        System.out.print("start container " + "\n");

                        // openwrtext4
                        CreateContainerResponse container = dockerClient.createContainerCmd("jgsoftwares/alpinelinux_derbydb:openwrtedgehost")
                             //.withCmd("/bin/bash", "/root/startderbydb.sh")
                             .withName("alpinelinuxderbydb")
                             .withHostConfig(hostConfig)

                            // .withExposedPorts(tcp1527)
                            // .withExposedPorts(tcp1527)
                             .withDomainName(styourdomainname)
                             .withAttachStderr(false)
                             .withAttachStdin(false)
                             .withAttachStdout(false)
                             //.withIpv4Address(stwanip)
                             //.withStdinOpen(Boolean.TRUE)
                             //.withWorkingDir("/root")
                             .exec();
                         dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec();     
                         dockerClient.startContainerCmd(container.getId()).exec();
                         
                          // edit container settings 
             // /etc/TZ
             // /etc/hosts
             // /etc/resolv.conf
             // /etc/sysctl.conf
             //de.jgsoftwares.guiserverpanel.config.DockerContainerconfig dockercontainerconfig = new de.jgsoftwares.guiserverpanel.config.DockerContainerconfig();
             //dockercontainerconfig.openwrt2305host(stcomboruntime);
           
             
              /*
                
                clear files in the docker container 
                openwrt2305host
             */
             // cat /dev/null > /etc/hosts
             String clearhostsfile = "/etc/hosts";
             ExecCreateCmdResponse execlearhost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearhostsfile).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
           
             // cat /dev/null > /etc/sysctl.conf
             String clearsysctl = "/etc/sysctl.conf";
             ExecCreateCmdResponse execlearsysctl = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearsysctl).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearsysctl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
                 
             // resolv.conf
             // cat /dev/null > /etc/resolv.conf
             String clearresolvconf = "/etc/resolv.conf";
             ExecCreateCmdResponse execlearresolvconf = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearresolvconf).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearresolvconf.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
                 
              // TZ
             // cat /dev/null > /etc/TZ
             String cleartimezone = "/etc/TZ";
             ExecCreateCmdResponse execleartimezone = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + cleartimezone).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execleartimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
              
             
             // clear /etc/hostname
             // cat /dev/null > /etc/hostname
             String clearhostname = "/etc/hostname";
             ExecCreateCmdResponse execlearhostname = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearhostname).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhostname.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
              
             /*
             
                write config to docker container 
                openwrt2305host
             
             */
             
             // add string with wanip and hostname
             String sthost = (String) ConfigPanel.stwanip + "  " + ConfigPanel.styourdomainname;
             ExecCreateCmdResponse execaddstringtohost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + sthost + " >> /etc/hosts").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtohost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             
            // /etc/sysctl.conf
             // add string to /etc/sysctl.conf
             // net.ipv6.conf.all.disable_ipv6=0
             // net.ipv4.conf.all.src_valid_mark=0
             // net.ipv4.ip_forward=0   
             String noforward = "net.ipv4.ip_forward=0";
             ExecCreateCmdResponse execaddstringtosyscontl = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noforward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtosyscontl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             String nosrcvalid = "net.ipv4.conf.all.src_valid_mark=0";
             ExecCreateCmdResponse execaddstringnosourcevalid = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + nosrcvalid + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnosourcevalid.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             String noipv6forward = "net.ipv6.conf.all.disable_ipv6=0";
             ExecCreateCmdResponse execaddstringnoipv6forward = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noipv6forward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnoipv6forward.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             String nodynamicaddr = "net.ipv4.ip_dynaddr=0";
             ExecCreateCmdResponse execaddstringnodynamicaddr = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + nodynamicaddr + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnodynamicaddr.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             // get Timezone String 
             String sttimezonecountry = ConfigPanel.stcomboboxtimezone;
             
             //load timezoneconfig
             // write timezone string to /etc/TZ
            
             de.jgsoftwares.guiserverpanel.config.Timezoneconfig tmconfig = new de.jgsoftwares.guiserverpanel.config.Timezoneconfig();     
             String stgettimezone = tmconfig.timezoneopenwrt(sttimezonecountry, cleartimezone);
             ExecCreateCmdResponse execaddstringtotimezone = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + stgettimezone + " >> /etc/TZ").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtotimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
          

            // resolv.conf
             //String stdns1 = null;
             //String stdns2 = null;
             
        
             
             
         
             //nameserver dnsip1
             ExecCreateCmdResponse execaddstringpublicdnsip1 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "nameserver " + stdns1 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip1.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             //nameserver dnsip2
             ExecCreateCmdResponse execaddstringpublicdnsip2 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "nameserver " + stdns2 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip2.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // add search with domainname
             ExecCreateCmdResponse execaddstringsearchdomain = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "search " + ConfigPanel.styourdomainname + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringsearchdomain.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // interface name
             ExecCreateCmdResponse execaddstringinterface = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "interface orange0 " + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringinterface.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // dnsec
             ExecCreateCmdResponse execaddstringdnssearch = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "DNSSEC=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnssearch.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // dnsovertls
             ExecCreateCmdResponse execaddstringdnsovertls = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "DNSOverTLS=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnsovertls.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
          
             // /etc/hostname
             // add hostname for alpinelinuxderbydb
             ExecCreateCmdResponse execaddstringhostname = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "alpinelinuxderbydb" + " >> /etc/hostname").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringhostname.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
          
                       
                    //}
                }
            
            // dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec();     
            //dockerClient.startContainerCmd(container.getId()).exec();

            
            
        } catch(Exception e)
        {
            System.out.print("Fehler " + e);
        }
    }   


        // start xterm for h2 command 
     /**
     *
     */
    @Override
    public void starth2db()
    {
    try {
                                  process = Runtime.getRuntime().exec("xterm -hold ");


          } catch(IOException e)
          {
              System.out.print("Error " +e);
          }

    }

    /**
     *
     */
    @Override
    public void startmysqldb()
    {
        
        try
        {
              ExposedPort tcp3306 = ExposedPort.tcp(3306);
                Ports portBindings = new Ports();
                portBindings.bind(tcp3306, Ports.Binding.bindPort(3306));
                
              
                Network network = dockerClient.inspectNetworkCmd().withNetworkId(stinterfacename).exec();

                HostConfig hostConfig = HostConfig.newHostConfig().withPortBindings(PortBinding.parse("3306:3306"));
                //Isolation.PROCESS.getValue();
                // add container to host network
                hostConfig.withNetworkMode(stinterfacename).getKernelMemory();
                hostConfig.getNetworkMode();
                
                hostConfig.isUserDefinedNetwork();
                //hostConfig.getIpcMode();
                hostConfig.withPrivileged(Boolean.TRUE);
                hostConfig.getPrivileged();
                
                hostConfig.withRuntime(stcomboruntime);
                hostConfig.getRuntime();
                
                 // isolation process
                hostConfig.withIsolation(Isolation.DEFAULT);
                hostConfig.getIsolation();
                // ipc mode
                hostConfig.withIpcMode("private");
                hostConfig.getIpcMode();
                
                // cgroup host
                hostConfig.withCgroup("private");
                hostConfig.getCgroup();
                
              
                  // dns server config
                de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig publicdnsserverconfig = new de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig();
           
                String stdnsserver = ConfigPanel.stpubdnsserver;
                // returns string dnspulicserver ipdns1 ipdns2

                String stdns1 = null;
                String stdns2 = null;

                publicdnsserverconfig.publicdns(stdnsserver, stdns1, stdns2);

                stdns1 = publicdnsserverconfig.getStdns1();
                stdns2 = publicdnsserverconfig.getStdns2();
                
                     // add dns String 
                String[] stdns = new String [] {stdns1,stdns2};
                hostConfig.withDns(stdns);
                hostConfig.getDns();
                
                  // set kernel memory to max
                hostConfig.withKernelMemory(Long.MAX_VALUE);
                hostConfig.getKernelMemory();
                
                hostConfig.withCpuShares(0);
                hostConfig.getCpuShares();
                System.out.print("mysql cpushare - firewall config to " + hostConfig.getCpuShares() + "\n");
                
                  System.out.print("pull image " + "\n");
                        dockerClient.pullImageCmd("jgsoftwares/demomysqlserver-ce")
                                .withTag("latest")
                                .exec(new PullImageResultCallback())
                                .awaitCompletion(30, TimeUnit.SECONDS);  
                
                
                  //dockerClient = DockerClientBuilder.getInstance().build();  
                   getDockerClient(dockerClient);
            CreateContainerResponse container = dockerClient.createContainerCmd("jgsoftwares/demomysqlserver-ce:latest")
                 //.withCmd("/bin/ash", "/root/LanServer.sh")
                 .withName("mysqlserver")
                 .withUser("root") 
              
                 .withHostConfig(hostConfig)
                
                 .withExposedPorts(tcp3306)
                // .withExposedPorts(tcp1527)
                 //.withDomainName(styourdomainname)
                 //.withIpv4Address(stwanip)
                 .withStdinOpen(Boolean.TRUE)
                 
                 //.withWorkingDir("/root")
                 .exec();
            
          
            
         dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec();    
         dockerClient.startContainerCmd(container.getId()).exec();
         
          // edit container settings 
             // /etc/TZ
             // /etc/hosts
             // /etc/resolv.conf
             // /etc/sysctl.conf
             //de.jgsoftwares.guiserverpanel.config.DockerContainerconfig dockercontainerconfig = new de.jgsoftwares.guiserverpanel.config.DockerContainerconfig();
             //dockercontainerconfig.openwrt2305host(stcomboruntime);
           
             
              /*
                
                clear files in the docker container 
                openwrt2305host
             */
             // cat /dev/null > /etc/hosts
             String clearhostsfile = "/etc/hosts";
             ExecCreateCmdResponse execlearhost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearhostsfile).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
           
             // cat /dev/null > /etc/sysctl.conf
             String clearsysctl = "/etc/sysctl.conf";
             ExecCreateCmdResponse execlearsysctl = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearsysctl).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearsysctl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
                 
             // resolv.conf
             // cat /dev/null > /etc/resolv.conf
             String clearresolvconf = "/etc/resolv.conf";
             ExecCreateCmdResponse execlearresolvconf = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearresolvconf).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearresolvconf.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
                 
              // TZ
             // cat /dev/null > /etc/TZ
             String cleartimezone = "/etc/TZ";
             ExecCreateCmdResponse execleartimezone = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + cleartimezone).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execleartimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
              
             
             // clear /etc/hostname
             // cat /dev/null > /etc/hostname
             String clearhostname = "/etc/hostname";
             ExecCreateCmdResponse execlearhostname = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearhostname).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhostname.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
              
             /*
             
                write config to docker container 
                openwrt2305host
             
             */
             
             // add string with wanip and hostname
             String sthost = (String) ConfigPanel.stwanip + "  " + ConfigPanel.styourdomainname;
             ExecCreateCmdResponse execaddstringtohost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + sthost + " >> /etc/hosts").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtohost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             
            // /etc/sysctl.conf
             // add string to /etc/sysctl.conf
             // net.ipv6.conf.all.disable_ipv6=0
             // net.ipv4.conf.all.src_valid_mark=0
             // net.ipv4.ip_forward=0   
             String noforward = "net.ipv4.ip_forward=0";
             ExecCreateCmdResponse execaddstringtosyscontl = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noforward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtosyscontl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             String nosrcvalid = "net.ipv4.conf.all.src_valid_mark=0";
             ExecCreateCmdResponse execaddstringnosourcevalid = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + nosrcvalid + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnosourcevalid.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             String noipv6forward = "net.ipv6.conf.all.disable_ipv6=0";
             ExecCreateCmdResponse execaddstringnoipv6forward = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noipv6forward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnoipv6forward.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             String nodynamicaddr = "net.ipv4.ip_dynaddr=0";
             ExecCreateCmdResponse execaddstringnodynamicaddr = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + nodynamicaddr + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnodynamicaddr.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             // get Timezone String 
             String sttimezonecountry = ConfigPanel.stcomboboxtimezone;
             
             //load timezoneconfig
             // write timezone string to /etc/TZ
            
             //de.jgsoftwares.guiserverpanel.config.Timezoneconfig tmconfig = new de.jgsoftwares.guiserverpanel.config.Timezoneconfig();     
             String stgettimezone = ConfigPanel.stcomboboxtimezone;
             ExecCreateCmdResponse execaddstringtotimezone = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + stgettimezone + " >> /etc/TZ").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtotimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
          

            // resolv.conf
             //String stdns1 = null;
             //String stdns2 = null;
             
         
             
         
             //nameserver dnsip1
             ExecCreateCmdResponse execaddstringpublicdnsip1 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "nameserver " + stdns1 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip1.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             //nameserver dnsip2
             ExecCreateCmdResponse execaddstringpublicdnsip2 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "nameserver " + stdns2 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip2.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // add search with domainname
             ExecCreateCmdResponse execaddstringsearchdomain = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "search " + ConfigPanel.styourdomainname + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringsearchdomain.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // interface name
             ExecCreateCmdResponse execaddstringinterface = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "interface orange0 " + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringinterface.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // dnsec
             ExecCreateCmdResponse execaddstringdnssearch = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "DNSSEC=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnssearch.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // dnsovertls
             ExecCreateCmdResponse execaddstringdnsovertls = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "DNSOverTLS=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnsovertls.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
          
             // /etc/hostname
             // add hostname for mysql
             ExecCreateCmdResponse execaddstringhostname = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "mysql" + " >> /etc/hostname").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringhostname.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
          
             // add Timezone
             //default_time_zone=Europe/Sofia
         
        } catch(Exception e)
        {
           System.out.print("Error " +e);
        }
        
        
        
    }

    /**
     *
     * @param stlandingpage
     * @param contsystem
     */
    @Override
    public void startlandingpage(String stlandingpage, String contsystem)
    {
       
        String stinstall = null;
        String sttime = null;
        String stimage = null;
        String stimagetag = null;
        String struncmdst = null;
        String stcontainername = null;
        String stshell = null;
        // simle container config 
        if (contsystem.equals("openwrt")) 
        {
	   stinstall = "apk add --allow-untrusted tzdata";
                // ln -s /usr/share/zoneinfo/Europe/Brussels /etc/localtime
           sttime = "echo 'CET-1CEST,M3.5.0,M10.5.0/3' > /etc/TZ";
           // "sh", "-c", "echo 'test:111' | chpasswd")
           // .withCmd("sh", "-c", "echo 'test:111' | chpasswd")

         
           stimage = "jgsoftwares/openwrt23.05landingpage";
           stimagetag = "java" + ConfigPanel.stjavaversion;
           stshell = "/bin/ash";
           struncmdst = "/root/runlandingpage.sh";
           stcontainername = "openwrtlandingpagedebug";
        } else if(contsystem.equals("oraclelinux")) {
                stinstall = "";
                // ln -s /usr/share/zoneinfo/Europe/Brussels /etc/localtime
                sttime = "timedatectl set-timezone Europe/Berlin";
                
                // jgsoftwares/oraclelinux_openjdk_landingpage:hostopenwrtext4 /bin/bash /root/runlandingpage.sh
                stimage = "jgsoftwares/oraclelinux_openjdk_landingpage";
                stimagetag = "hostopenwrtext4";
                stshell = "/bin/bash";
                struncmdst = "/root/runlandingpage.sh";
                stcontainername = "oraclelinuxlandingpagedebug";
        }   
        else if(contsystem.equals("alpinelinux")) {
                //do this code
                stinstall = "apk add tzdata";
                // ln -s /usr/share/zoneinfo/Europe/Brussels /etc/localtime
                sttime = "echo ln -s /usr/share/zoneinfo/Europe/Berlin > /etc/localtime";
                //docker pull jgsoftwares/alpinelinux_landingpage:edgehost
                stimage = "jgsoftwares/alpinelinux_landingpage";
                stimagetag = "edgehost";
                stshell = "/bin/sh";
                struncmdst = "/root/runlandingpage.sh";
                stcontainername = "alpinelinuxlandingpagedebugedge";
        }
       
        
        try
        {
            //InspectContainerResponse startlandingpage = (InspectContainerResponse) dockerClient.startContainerCmd(stlandingpage);
           // InspectContainerResponse startlandingpage = (InspectContainerResponse) dockerClient.startContainerCmd(stlandingpage);
           //  jgsoftwares/oraclelinux_openjdk_landingpage:hostopenwrtext4 /bin/bash /root/runlandingpage.sh
                //ExposedPort tcp80 = ExposedPort.tcp(80);
                
                
                // show java version
                // > 26 java version http3 with upd 
                String showjavaversion = ConfigPanel.stjavaversion;
                Integer intjavaversion = Integer.parseInt(showjavaversion);
                ExposedPort tcp80 = null;
                if(intjavaversion > 24)
                {
                    tcp80 = ExposedPort.tcp(80);
                    System.out.print("landingpage is started with Java JDK" + showjavaversion + "\n");
                    System.out.print("to java > 25 tcp is required for http2" + "\n");
                    System.out.print("container is started with tcp config on port 80");
                }
                else
                {
                    tcp80 = ExposedPort.udp(80);
                    System.out.print("landingpage is started with Java > 25" + showjavaversion + "\n");
                    System.out.print("the landingpage with java version > 25 ist started with port udp over http on port 80 " + "\n");
                }
                
                
                
                ExposedPort tcp1527 = ExposedPort.tcp(1527);
                Ports portBindings = new Ports();
                //portBindings.bind(tcp1527, Ports.Binding.bindPort(1527));
                portBindings.bind(tcp80, Ports.Binding.bindPort(80));
                portBindings.bind(tcp1527, Ports.Binding.bindPort(1527));
                
                
                 // dns server config
                de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig publicdnsserverconfig = new de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig();
           
                String stdnsserver = ConfigPanel.stpubdnsserver;
                // returns string dnspulicserver ipdns1 ipdns2

                String stdns1 = null;
                String stdns2 = null;

                publicdnsserverconfig.publicdns(stdnsserver, stdns1, stdns2);

                stdns1 = publicdnsserverconfig.getStdns1();
                stdns2 = publicdnsserverconfig.getStdns2();
                
                
                // connect to network like eth0 or eth0.10
                Network network = dockerClient.inspectNetworkCmd().withNetworkId(stinterfacename).exec();

                HostConfig hostConfig = HostConfig.newHostConfig().withPortBindings(PortBinding.parse("80:80"), PortBinding.parse("1527:1527"));
                
                System.out.print("started network config for landingpage " + "\n");
                System.out.print("used ports are 80 and 1527 for derbydb " + " on java25 port 80 is started with upd for html3" + "\n");
                // add container to host network
                hostConfig.withNetworkMode(stinterfacename);
                hostConfig.getNetworkMode();
                System.out.print("networkmode " + hostConfig.getNetworkMode());
                
                
                //hostConfig.withCapAdd(com.github.dockerjava.api.model.Capability.NET_ADMIN)
                hostConfig.withCapAdd(Capability.NET_ADMIN);
                hostConfig.withCapAdd(Capability.NET_RAW);
                hostConfig.withCapAdd(Capability.SYS_ADMIN);
                hostConfig.getCapAdd();
                System.out.print("Capability " + hostConfig.getCapAdd().toString() + "\n");
                
                hostConfig.isUserDefinedNetwork();
                
                hostConfig.withPrivileged(Boolean.TRUE);
                hostConfig.getPrivileged();
                System.out.print("Privileged Mode " + hostConfig.getPrivileged() + "\n");
                //Isolation.PROCESS.getValue();
               
                
                // isolation process
                hostConfig.withIsolation(Isolation.DEFAULT);
                hostConfig.getIsolation();
                System.out.print("start landingpage container with Isolation on linux openwrt only default is supported " + hostConfig.getIsolation() + "\n");
                
                // ipc mode
                hostConfig.withIpcMode("host");
                hostConfig.getIpcMode();
                System.out.print("Ipc Mode " + hostConfig.getIpcMode() + "\n");
                
                // cgroup host
                hostConfig.withCgroup("host");
                hostConfig.getCgroup();
                System.out.print("Cgroup mode " + hostConfig.getCgroup() + "\n");
                
                
                //Isolation.HYPERV.getValue();
                
                hostConfig.withMemory(Long.MAX_VALUE);
                //Isolation.HYPERV.getValue();   
                hostConfig.getMemoryReservation();
                hostConfig.getMemory();
                System.out.print("with memory " + hostConfig.getMemory() + "\n");
                
                hostConfig.withMemorySwap(Long.MAX_VALUE);
                hostConfig.getMemorySwap();
                System.out.print("with memory Swap " + hostConfig.getMemorySwap() + "\n");
                
                //hostConfig.getBinds();           
                //hostConfig.getDevices();
            
                //hostConfig.withDns(stdns1.toString() + stdns2.toString());
                
                
                // add dns String 
                String[] stdns = new String [] {stdns1,stdns2};
                hostConfig.withDns(stdns);
                hostConfig.getDns();
                System.out.print("with dns " + hostConfig.getDns());
                
                
                hostConfig.withDnsSearch(ConfigPanel.styourdomainname);
                hostConfig.getDnsSearch();    
                System.out.print("with dns search " + hostConfig.getDnsSearch() + "\n");
                
                hostConfig.withRuntime(stcomboruntime);
                hostConfig.getRuntime();
                System.out.print("with runtime " + hostConfig.getRuntime() + "\n");
                
               // hostConfig.withSysctls(sysctls)
                /*
                dockerClient.pullImageCmd(stimage)
                .withTag(stimagetag)
                .exec(new PullImageResultCallback())
                .awaitCompletion(30, TimeUnit.SECONDS);
                */
                
                // set kernel memory to max
                hostConfig.withKernelMemory(Long.MAX_VALUE);
                hostConfig.getKernelMemory();
                System.out.print("with kernel memory "+ hostConfig.getKernelMemory() + "\n");
                
                
                hostConfig.withCpuShares(0);
                hostConfig.getCpuShares();
                System.out.print("landingpage cpushare - firewall config to " + hostConfig.getCpuShares() + "\n");
                
            //dockerClient = DockerClientBuilder.getInstance().build();  
            getDockerClient(dockerClient);
             
           
            // check image exist
            boolean imagenotexist = false;
            try
            {
                dockerClient.inspectContainerCmd(stcontainername).exec();
            } catch(NotFoundException e)
            {
                System.out.print("error search image " + e);
            }
            if(imagenotexist == true)
            {
                System.out.print("landingpage image exist" + "\n");
            }
            else
            {
               
                String stjavaversion = ConfigPanel.stjavaversion.toString();
                String stlandingpageexist = "jgsoftwares/openwrt23.05landingpage:java" + stjavaversion;
                dockerClient.pullImageCmd(stlandingpageexist).exec(new PullImageResultCallback()).awaitSuccess();
            }
          
            
            
            CreateContainerResponse container = null;

               switch(contsystem)
                {
                  
                case "openwrt":
                    System.out.println("start openwrt container " + "\n");
                    container = dockerClient.createContainerCmd(stimage+":" + stimagetag)
                    .withCmd(stshell, struncmdst)   
                    .withName(stcontainername)
                    .withUser("root")
                    //.withCmd(cmd)
                    .withHostConfig(hostConfig)
                    .withExposedPorts(tcp80, tcp1527)
                    //.getExposedPorts(stgetexposedport)
                    // .withExposedPorts(tcp1527)
                    .withDomainName(styourdomainname)
                    //.withIpv4Address(stwanip)
                    .withStdinOpen(Boolean.TRUE)
                    //.getExposedPorts(extcp80 + extcp1527)
                    //.getExposedPorts(extcp1527.getProtocol())
                    .withAttachStderr(true)
                    .withAttachStdin(true)
                    .withAttachStdout(true)   
                   
                    // timesettings
                    //.withCmd(stinstall)
                    //.withCmd(sttime)
                    .withWorkingDir("/root")
                   
                            // 
                            //   jTextArea1.append("opkg install alpine-repositories" +"apk add --allow-untrusted tzdata" + "\n");
                            // jTextArea1.append("add CET-1CEST,M3.5.0,M10.5.0/3 to  /etc/TZ - for germany" + "/n");
                    // install alpine repositorys
                    
                    //.withCmd(stshell, stinstall)
                    //.withCmd(stshell, sttime)   
                     
                    .exec();
                    break;
                case "oraclelinux":
                   System.out.println("start oracle container " + "\n");
                    container = dockerClient.createContainerCmd(stimage+":" + stimagetag)
                    //.withCmd(stshell, struncmdst)
                    .withName(stcontainername)
                    .withUser("root")
                    //.withCmd(cmd)
                    .withHostConfig(hostConfig)
                    .withExposedPorts(tcp80)
                    // .withExposedPorts(tcp1527)
                    .withDomainName(styourdomainname)
                    //.withIpv4Address(stwanip)
                    .withStdinOpen(Boolean.TRUE)
                    .withAttachStderr(false)
                    .withAttachStdin(false)
                    .withAttachStdout(false)
                    // timesettings
                    //.withCmd(stshell + " " + struncmdst)
                    //.withCmd(sttime)
                    .withWorkingDir("/root")
                    //.withCmd(stshell, "-c", stinstall)
                    //.withCmd(sttime)     
                    .exec();
                    break;
                case "alpinelinux":
                   System.out.println("start alpine linux container " + "\n");
                    container = dockerClient.createContainerCmd(stimage+":" + stimagetag)
                    //.withCmd(stshell, struncmdst)
                    .withName(stcontainername)
                    .withUser("root")
                    //.withCmd(cmd)
                    .withHostConfig(hostConfig)
                    .withExposedPorts(tcp80)
                    // .withExposedPorts(tcp1527)
                    .withDomainName(styourdomainname)
                    //.withIpv4Address(stwanip)
                    .withStdinOpen(Boolean.TRUE)
                    .withAttachStderr(false)
                    .withAttachStdin(false)
                    .withAttachStdout(false)
                    // timesettings
                    //.withCmd(stinstall)
                    //.withCmd(sttime)
                    .withWorkingDir("/root")
                    //.withCmd(stshell, "-c", stinstall)
                    //.withCmd(stshell, "-c", sttime)     
                    .exec();
                    break;
               
                default:
                    System.out.println("Error no system selected " + "\n");
                    break;
                } 

            
            /*container = dockerClient.createContainerCmd(stimage+":" + stimagetag)
                    .withCmd(stshell, struncmdst)
                    .withName(stcontainername)
                    .withUser("root")
                    //.withCmd(cmd)
                    .withHostConfig(hostConfig)
                    .withExposedPorts(tcp80)
                    // .withExposedPorts(tcp1527)
                    .withDomainName(styourdomainname)
                    //.withIpv4Address(stwanip)
                    .withStdinOpen(Boolean.TRUE)
                    .withAttachStderr(false)
                    .withAttachStdin(false)
                    .withAttachStdout(false)
                    // timesettings
                    //.withCmd(stinstall)
                    //.withCmd(sttime)
                    .withWorkingDir("/root")
                    //.withCmd("sh", "-c", "echo 'test:111' | chpasswd")
                    .exec();
                */
            
            dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec();    
            
            // Network network = dockerClient.inspectNetworkCmd().withNetworkId("none").exec();
            // dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec();

            
            //DefaultDockerClientConfig build = DefaultDockerClientConfig.createDefaultConfigBuilder().withDockerHost("tcp://docker:2375").build();
            //DockerClient docker = DockerClientBuilder.getInstance(build).build();
            //docker.execCreateCmd("containerName").withCmd("sh", "-c", "cd /root/Downloads && ./myScript.sh").exec();
    
         
             // create container from image
              //  CreateContainerResponse container = dockerClient.createContainerCmd("jgsoftwares/oraclelinux_openjdk_lanservertcp:hostopenwrtext4")            
                //        .withExposedPorts(tcp8443)
                 //       .withHostConfig(hostConfig) //.withPortBindings(portBindings))
                 //       .withName("oraclelinuxlanservertcp")
                 //       .exec();
         dockerClient.startContainerCmd(container.getId()).exec();
    
          // edit container settings 
             // /etc/TZ
             // /etc/hosts
             // /etc/resolv.conf
             // /etc/sysctl.conf
     
             
              /*
                
                clear files in the docker container 
                landingpage
             */
             // cat /dev/null > /etc/hosts
             String clearhostsfile = "/etc/hosts";
             ExecCreateCmdResponse execlearhost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearhostsfile).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file /etc/host");
             
             // cat /dev/null > /etc/sysctl.conf
             String clearsysctl = "/etc/sysctl.conf";
             ExecCreateCmdResponse execlearsysctl = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearsysctl).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearsysctl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
              System.out.print("flush file /etc/sysctl.conf" + "\n");     
            
             // resolv.conf
             // cat /dev/null > /etc/resolv.conf
             String clearresolvconf = "/etc/resolv.conf";
             ExecCreateCmdResponse execlearresolvconf = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearresolvconf).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearresolvconf.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file /etc/resolv.conf" + "\n");
             
              // TZ
             // cat /dev/null > /etc/TZ
             String cleartimezone = "/etc/TZ";
             ExecCreateCmdResponse execleartimezone = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + cleartimezone).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execleartimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file /etc/TZ" + "\n");
             
             // clear /etc/hostname
             // cat /dev/null > /etc/hostname
             String clearhostname = "/etc/hostname";
             ExecCreateCmdResponse execlearhostname = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearhostname).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhostname.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file /etc/hostname " + "\n");
             
             /*
             
                write config to docker container 
                openwrtlandingpage
             
             */
             
             // add string with wanip and hostname
             String sthost = (String) ConfigPanel.stwanip + "  " + ConfigPanel.styourdomainname;
             ExecCreateCmdResponse execaddstringtohost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + sthost + " >> /etc/hosts").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtohost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("wirte String " + sthost + " >> /etc/hosts" + " to container landingpage " + "\n");
             
             
            // /etc/sysctl.conf
             // add string to /etc/sysctl.conf
             // net.ipv6.conf.all.disable_ipv6=0
             // net.ipv4.conf.all.src_valid_mark=0
             // net.ipv4.ip_forward=0   
             String noforward = "net.ipv4.ip_forward=0";
             ExecCreateCmdResponse execaddstringtosyscontl = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noforward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtosyscontl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write string " + noforward + " >> /etc/sysctl.conf" + "to container landingpage " + "\n");
             
             String nosrcvalid = "net.ipv4.conf.all.src_valid_mark=0";
             ExecCreateCmdResponse execaddstringnosourcevalid = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + nosrcvalid + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnosourcevalid.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write String " + nosrcvalid + " >> /etc/sysctl.conf" + " to landingpage container " + "\n");
             
             String noipv6forward = "net.ipv6.conf.all.disable_ipv6=0";
             ExecCreateCmdResponse execaddstringnoipv6forward = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noipv6forward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnoipv6forward.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write String to " + noipv6forward + " >> /etc/sysctl.conf" + " to container landingapge " + "\n");
             
             String nodynamicaddr = "net.ipv4.ip_dynaddr=0";
             ExecCreateCmdResponse execaddstringnodynamicaddr = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + nodynamicaddr + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnodynamicaddr.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write String to " + nodynamicaddr + " >> /etc/sysctl.conf" + " to container landingpage " + "\n");
             
             // get Timezone String 
             String sttimezonecountry = ConfigPanel.stcomboboxtimezone;
             
             //load timezoneconfig
             // write timezone string to /etc/TZ
            
             de.jgsoftwares.guiserverpanel.config.Timezoneconfig tmconfig = new de.jgsoftwares.guiserverpanel.config.Timezoneconfig();     
             String stgettimezone = tmconfig.timezoneopenwrt(sttimezonecountry, cleartimezone);
             ExecCreateCmdResponse execaddstringtotimezone = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + stgettimezone + " >> /etc/TZ").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtotimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write String to " + stgettimezone + " >> /etc/TZ" + "to container landingpage ");

            // resolv.conf
             //String stdns1 = null;
             //String stdns2 = null;
             
             //de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig publicdnsserverconfig = new de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig();
           
             //String stdnsserver = ConfigPanel.stpubdnsserver;
             // returns string dnspulicserver ipdns1 ipdns2
             
             //String stdns1 = null;
             //String stdns2 = null;
             
             //publicdnsserverconfig.publicdns(stdnsserver, stdns1, stdns2);
            
             //stdns1 = publicdnsserverconfig.getStdns1();
             //stdns2 = publicdnsserverconfig.getStdns2();
             
             
         
             //nameserver dnsip1
             ExecCreateCmdResponse execaddstringpublicdnsip1 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "nameserver " + stdns1 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip1.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write String to "  + stdns1 + " >> /etc/resolv.conf" + "to landingpage container " + "\n");
            
             //nameserver dnsip2
             ExecCreateCmdResponse execaddstringpublicdnsip2 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "nameserver " + stdns2 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip2.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write String to" +  stdns2 + " >> /etc/resolv.conf" + " to container landingapge " + "\n");
             
             // add search with domainname
             ExecCreateCmdResponse execaddstringsearchdomain = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "search " + ConfigPanel.styourdomainname + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringsearchdomain.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write String to " + "search " + ConfigPanel.styourdomainname + " >> /etc/resolv.conf" + " to container landingpage " + "\n");
             
             // interface name
             ExecCreateCmdResponse execaddstringinterface = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "interface orange0 " + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringinterface.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write String to " + "interface orange0 " + " >> /etc/resolv.conf" + " to container landingpage "+ "\n");
             
             // dnsec
             ExecCreateCmdResponse execaddstringdnssearch = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "DNSSEC=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnssearch.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write String to" + "echo " + "DNSSEC=yes" + " >> /etc/resolv.conf" + " to landingpage container " + "\n");
             
             
             
             // dnsovertls
             ExecCreateCmdResponse execaddstringdnsovertls = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "DNSOverTLS=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnsovertls.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write String to " + "echo " + "DNSOverTLS=yes" + " >> /etc/resolv.conf" + " to container landingapge " + "\n");
             
             // /etc/hostname
             // add hostname for openwrt2305
             ExecCreateCmdResponse execaddstringhostname = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "openwrtlandingpage" + " >> /etc/hostname").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringhostname.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write String to Container " + "echo " + "openwrtlandingpage" + " >> /etc/hostname" + " to container landingpage " + "\n");
             
    
             // run command to update the date time
             // opkg update && opkg install zoneinfo-all
             ExecCreateCmdResponse execrunzoneinfo = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "opkg update && opkg install zoneinfo-all").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execrunzoneinfo.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("run command " + "opkg update && opkg install zoneinfo-all" + " in container landingapge " + "\n");
             
             // install iptables firewall package
             // create dir
             // /var/run -- for lock file for iptables
             // opkg install iptables-legacy
             ExecCreateCmdResponse execreatedir = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "mkdir /var/run/").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execreatedir.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("create diretory /var/run" + " on landingpage container " + "\n");
             
             ExecCreateCmdResponse execinstalliptables = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "opkg install iptables-legacy").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execinstalliptables.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("install package iptables-legacy" + " to landingpagecontainer " + "\n");
             
             // iptables save
             ExecCreateCmdResponse execiptablessave = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "iptables-legacy-save").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execiptablessave.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("run command iptables-save" + "on container landingpage " + "\n");
             
             // commit
             // jgsoftwares/openwrt23.05landingpage   java11
             dockerClient.commitCmd(stcontainername).withRepository("jgsoftwares/openwrt23.05landingpage").withTag("java" +ConfigPanel.stjavaversion).exec();
             System.out.print("local image commit jgsoftwares/openwrt23.05landingpage:java11" + "\n");
         
             System.out.print("restart container openwrt2305host to run iptables on this container in memory " + "\n");
             
        } catch(Exception e)
        {
            System.out.print("Fehler " + e);
        }
        
    }
    
    /**
     *
     */
    @Override
    public void startopenwrtcloudflaredmz()
    {
          try {
                                  process = Runtime.getRuntime().exec("xterm -hold ");

          } catch(IOException e)
          {
              System.out.print("Error " +e);
          }
    }
        
        
    @Override
    public void startopenwrtopendns()
    {
          try {
                                  process = Runtime.getRuntime().exec("xterm -hold ");

          } catch(IOException e)
          {
              System.out.print("Error " +e);
          }
    }

    /**
     *
     * @param imageName
     */
    @Override
    public void pullImage(String imageName)
    {


    }
    
    /**
     *
     * @param stopenwrtnone
     * 
     */
    @Override
    public void startopenwrt2305none(String stopenwrtnone)
    {
        /*
            start openwrt container on network none
        */
        
     
        try
        {
             InspectContainerResponse startopenwrtnone = (InspectContainerResponse) dockerClient.startContainerCmd(stopenwrtnone);
             startopenwrtnone.getConfig();
             
        } catch(Exception e)
        {
            System.out.print("Fehler " + e);
        }
    }
    
    /**
     *
     * @param stopenwrthost
     */
    @Override
    public void startopenwrt2305host(String stopenwrthost)
    {
        /*
            start openwrt 2305 docker container on host network
        */
       
        try
        {
                // add volume - dockersocket 
                Volume dockersocket = new Volume("/var/run/docker.sock");
                
                
                // network none
                // connect to network like eth0 or eth0.10
                Network network = dockerClient.inspectNetworkCmd().withNetworkId(stinterfacename).exec();

                
                
                
                // dns server config
                de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig publicdnsserverconfig = new de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig();
           
                String stdnsserver = ConfigPanel.stpubdnsserver;
                // returns string dnspulicserver ipdns1 ipdns2

                String stdns1 = null;
                String stdns2 = null;

                publicdnsserverconfig.publicdns(stdnsserver, stdns1, stdns2);

                stdns1 = publicdnsserverconfig.getStdns1();
                stdns2 = publicdnsserverconfig.getStdns2();
                
                
                HostConfig hostConfig = HostConfig.newHostConfig();
                        //.withPortBindings(PortBinding.parse("80:80"), PortBinding.parse("1527:1527"));
               
                
                        
                // add container to host network
                hostConfig.withNetworkMode(stinterfacename).getKernelMemory();
                hostConfig.getNetworkMode();
                
                //hostConfig.withCapAdd(com.github.dockerjava.api.model.Capability.NET_ADMIN)
                hostConfig.withCapAdd(Capability.NET_ADMIN);
                hostConfig.withCapAdd(Capability.NET_RAW);
                hostConfig.withCapAdd(Capability.SYS_ADMIN);
                hostConfig.getCapAdd();
                
                
                hostConfig.isUserDefinedNetwork();
                        
                hostConfig.withPrivileged(Boolean.TRUE);
                hostConfig.getPrivileged();
                
                //Isolation.PROCESS.getValue();
                
                // domain name from config panel
                hostConfig.withDnsSearch(ConfigPanel.styourdomainname);
                //hostConfig.getDnsSearch();
                //hostConfig.withDns(stdns1 + stdns2);
                     // add dns String 
                String[] stdns = new String [] {stdns1,stdns2};
                hostConfig.withDns(stdns);
                hostConfig.getDns();
                hostConfig.getDnsSearch();
                
                //Isolation.HYPERV.getValue();
                hostConfig.getMemory();
                
                // isolation process
                hostConfig.withIsolation(Isolation.DEFAULT);
                hostConfig.getIsolation();
                // ipc mode
                hostConfig.withIpcMode("private");
                hostConfig.getIpcMode();
                
                // cgroup host
                hostConfig.withCgroup("private");
                hostConfig.getCgroup();
                
                
                //hostConfig.getBinds();
                //hostConfig.getDevices();
                // get runtime from config panel
                hostConfig.withRuntime(stcomboruntime);
                hostConfig.getRuntime();
                
                     // set kernel memory to max
                hostConfig.withKernelMemory(Long.MAX_VALUE);
                hostConfig.getKernelMemory();
                
                // mount docker socket
                hostConfig.withBinds(new Bind("/var/run/docker.sock", dockersocket));
                //Collections.singletonMap("/tmp", "rw,noexec,nosuid,size=50m")
                
                // run in memory with tmpfs
                hostConfig.withTmpFs(Collections.singletonMap("/opt/docker", "rw,noexec,nosuid,size=50m"));
                hostConfig.getTmpFs(); //.get("/opt/docker");
                //hostConfig.setLinks("orange0");
                //Isolation.PROCESS.getValue();
                /*
                // jgsoftwares/openwrt23.05:nftbridgelayer2ext4
                dockerClient.pullImageCmd("jgsoftwares/openwrt23.05")
                .withTag("nftbridgelayer2ext4")
                .exec(new PullImageResultCallback())
                .awaitCompletion(30, TimeUnit.SECONDS);
                */
                
                
            String stcontainername = "openwrt2305host";
            // check image exist
            boolean imagenotexist = false;
            try
            {
                dockerClient.inspectContainerCmd(stcontainername).exec();
            } catch(NotFoundException e)
            {
                System.out.print("error search image " + e);
            }
            if(imagenotexist == true)
            {
                System.out.print("openwrthost image exist" + "\n");
            }
            else
            {
               
                // jgsoftwares/openwrt23.05:nftbridgelayer2ext4 
               
                
                String stimagetag = "iptablesext4";
                String stopenwrt2305host = "jgsoftwares/openwrt23.05:" + stimagetag;
                dockerClient.pullImageCmd("jgsoftwares/openwrt23.05:" + stimagetag).exec(new PullImageResultCallback()).awaitSuccess();
            }
                
            //dockerClient = DockerClientBuilder.getInstance().build();  
             getDockerClient(dockerClient);
           
            
             
           
            
            //CreateContainerResponse container;
            CreateContainerResponse container = dockerClient.createContainerCmd("jgsoftwares/openwrt23.05:iptablesext4")
                    .withName("openwrt2305host")
                    .withUser("root")
                    .withVolumes(dockersocket)
                    .withHostConfig(hostConfig)
                    //.withExposedPorts(tcp80)
                    //.withExposedPorts(tcp1527)
                    .withAttachStderr(true)
                    .withAttachStdin(true)
                    .withAttachStdout(true)
                    .withDomainName(ConfigPanel.styourdomainname)  
                    .withTty(true)
                    .withDomainName(styourdomainname)
                    //.withIpv4Address(stwanip)
                    .withStdinOpen(Boolean.TRUE) 
                    .withWorkingDir("/root")
                    .exec();
            
             dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec();    
            
             // start container
             dockerClient.startContainerCmd(container.getId()).exec();
             
             
             // edit container settings 
             // /etc/TZ
             // /etc/hosts
             // /etc/resolv.conf
             // /etc/sysctl.conf
             //de.jgsoftwares.guiserverpanel.config.DockerContainerconfig dockercontainerconfig = new de.jgsoftwares.guiserverpanel.config.DockerContainerconfig();
             //dockercontainerconfig.openwrt2305host(stcomboruntime);
           
             
              /*
                
                clear files in the docker container 
                openwrt2305host
             */
             // cat /dev/null > /etc/hosts
             String clearhostsfile = "/etc/hosts";
             ExecCreateCmdResponse execlearhost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearhostsfile).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file - /etc/hosts" + "\n");
             
             // cat /dev/null > /etc/sysctl.conf
             String clearsysctl = "/etc/sysctl.conf";
             ExecCreateCmdResponse execlearsysctl = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearsysctl).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearsysctl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file - /etc/sysctl.conf" + "\n"); 
             
             // resolv.conf
             // cat /dev/null > /etc/resolv.conf
             String clearresolvconf = "/etc/resolv.conf";
             ExecCreateCmdResponse execlearresolvconf = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearresolvconf).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearresolvconf.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file - /etc/resolv.conf" + "\n");   
             
              // TZ
             // cat /dev/null > /etc/TZ
             String cleartimezone = "/etc/TZ";
             ExecCreateCmdResponse execleartimezone = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + cleartimezone).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execleartimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file - /etc/TZ" + "\n");   
             
             // clear /etc/hostname
             // cat /dev/null > /etc/hostname
             String clearhostname = "/etc/hostname";
             ExecCreateCmdResponse execlearhostname = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearhostname).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhostname.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file - /etc/hostname" + "\n");   
             
             
             /*
             
                write config to docker container 
                openwrt2305host
             
             */
             
             // add string with wanip and hostname
             String sthost = (String) ConfigPanel.stwanip + "  " + ConfigPanel.styourdomainname;
             ExecCreateCmdResponse execaddstringtohost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + sthost + " >> /etc/hosts").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtohost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write string " + sthost + "to etc/host");
             
            // /etc/sysctl.conf
             // add string to /etc/sysctl.conf
             // net.ipv6.conf.all.disable_ipv6=0
             // net.ipv4.conf.all.src_valid_mark=0
             // net.ipv4.ip_forward=0   
             String noforward = "net.ipv4.ip_forward=0";
             ExecCreateCmdResponse execaddstringtosyscontl = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noforward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtosyscontl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write string " + noforward + "to /etc/sysctl.conf");
             
             
             String nosrcvalid = "net.ipv4.conf.all.src_valid_mark=0";
             ExecCreateCmdResponse execaddstringnosourcevalid = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + nosrcvalid + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnosourcevalid.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write string " + nosrcvalid + "to /etc/sysctl.conf");
             
             
             String noipv6forward = "net.ipv6.conf.all.disable_ipv6=0";
             ExecCreateCmdResponse execaddstringnoipv6forward = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noipv6forward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnoipv6forward.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write string " + noipv6forward + "to /etc/sysctl.conf");
             
             
             String nodynamicaddr = "net.ipv4.ip_dynaddr=0";
             ExecCreateCmdResponse execaddstringnodynamicaddr = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + nodynamicaddr + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnodynamicaddr.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write string " + nodynamicaddr + "to /etc/sysctl.conf");
             
             // get Timezone String 
             String sttimezonecountry = ConfigPanel.stcomboboxtimezone;
             
             //load timezoneconfig
             // write timezone string to /etc/TZ
            
             de.jgsoftwares.guiserverpanel.config.Timezoneconfig tmconfig = new de.jgsoftwares.guiserverpanel.config.Timezoneconfig();     
             String stgettimezone = tmconfig.timezoneopenwrt(sttimezonecountry, cleartimezone);
             ExecCreateCmdResponse execaddstringtotimezone = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + stgettimezone + " >> /etc/TZ").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtotimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write string to timezone /etc/TZ" + stgettimezone);

            // resolv.conf
             //String stdns1 = null;
             //String stdns2 = null;
             
           
             
             
         
             //nameserver dnsip1
             //ExecCreateCmdResponse execaddstringpublicdnsip1 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "nameserver " + stdns1 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             //dockerClient.execStartCmd(execaddstringpublicdnsip1.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             //nameserver dnsip2
             //ExecCreateCmdResponse execaddstringpublicdnsip2 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "nameserver " + stdns2 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             //dockerClient.execStartCmd(execaddstringpublicdnsip2.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // add search with domainname
             //ExecCreateCmdResponse execaddstringsearchdomain = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "search " + ConfigPanel.styourdomainname + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             //dockerClient.execStartCmd(execaddstringsearchdomain.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
            
             // interface name
             ExecCreateCmdResponse execaddstringinterface = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "interface orange0 " + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringinterface.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write String interface orange0 /etc/resolv.conf" + "\n");
             
             // dnsec
             ExecCreateCmdResponse execaddstringdnssearch = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "DNSSEC=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnssearch.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write String" + "DNSSEC=yes" + " >> /etc/resolv.conf" + "\n");
             
             // dnsovertls
             ExecCreateCmdResponse execaddstringdnsovertls = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "DNSOverTLS=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnsovertls.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write String DNSOverTLS=yes" + " >> /etc/resolv.conf" + "\n");
             // /etc/hostname
             // add hostname for openwrt2305
             ExecCreateCmdResponse execaddstringhostname = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "openwrt2305host" + " >> /etc/hostname").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringhostname.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("write String + openwrt2305host" + " >> /etc/hostname" + "\n");
             // install iptables firewall package
             // create dir
             // /var/run -- for lock file for iptables
             // opkg install iptables-legacy
             ExecCreateCmdResponse execreatedir = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "mkdir /var/run/").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execreatedir.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("create direcotory /var/run" + "\n");
             
             //ExecCreateCmdResponse execinstalliptables = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "opkg install iptables-legacy").withAttachStdout(true).withAttachStderr(true).exec();
             //dockerClient.execStartCmd(execinstalliptables.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
          
             // iptables save
             ExecCreateCmdResponse execiptablessave = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "iptables-legacy-save").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execiptablessave.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("run command iptables-legacy-save on container openwrt2305host" + "\n" + "output iptables " + "\n");
             // commit
             // jgsoftwares/openwrt23.05
             dockerClient.commitCmd(stcontainername).withRepository("jgsoftwares/openwrt23.05").withTag("iptablesext4").exec();
             System.out.print("local image commit jgsoftwares/openwrt23.05:iptablesext4");
             

        } catch(Exception e)
        {
            System.out.print("Fehler " + e);
        }
        
      
    }

     /*
        start ipfire in host mode
    */
     public void startipfire(String stipfire)
    {
          try
          {
        
                // ipfire http port 
                // access to ipfire container over vpn
                // https://192.168.10.56:444
                ExposedPort tcp444 = ExposedPort.tcp(444);
                // port 53 for dhcp
                //ExposedPort tcp53 = ExposedPort.tcp(53);
                
                Ports portBindings = new Ports();
                //portBindings.bind(tcp1527, Ports.Binding.bindPort(1527));
                portBindings.bind(tcp444, Ports.Binding.bindPort(444));
                //portBindings.bind(tcp53, Ports.Binding.bindPort(53));
                
             
                 // dns server config
                de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig publicdnsserverconfig = new de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig();
           
                String stdnsserver = ConfigPanel.stpubdnsserver;
                // returns string dnspulicserver ipdns1 ipdns2

                String stdns1 = null;
                String stdns2 = null;

                publicdnsserverconfig.publicdns(stdnsserver, stdns1, stdns2);

                stdns1 = publicdnsserverconfig.getStdns1();
                stdns2 = publicdnsserverconfig.getStdns2();
                
                
               
                System.out.print("start network config docker-java " + "\n");
                // connect to network like eth0 or eth0.10
                Network network = dockerClient.inspectNetworkCmd().withNetworkId(stinterfacename).exec();
                
                HostConfig hostConfig = HostConfig.newHostConfig();
                        //.withPortBindings(PortBinding.parse("80:80"), PortBinding.parse("1527:1527"));
                        
               
                
                // add container to host network
                hostConfig.withNetworkMode(stinterfacename); //.getKernelMemory();
                hostConfig.getNetworkMode();
                System.out.print("host network mode " + hostConfig.getNetworkMode() + "\n");
                
                //hostConfig.withCapAdd(com.github.dockerjava.api.model.Capability.NET_ADMIN)
                hostConfig.withCapAdd(Capability.NET_ADMIN);  
                hostConfig.withCapAdd(Capability.NET_RAW);
                hostConfig.withCapAdd(Capability.SYS_ADMIN);
                hostConfig.getCapAdd();
                System.out.print("Capability"  + hostConfig.getCapAdd().toString() + "\n");
                
                
                hostConfig.isUserDefinedNetwork();
                
                hostConfig.withPrivileged(Boolean.TRUE);
                hostConfig.getPrivileged();
                //Isolation.PROCESS.getValue();     
                System.out.print("Privileged" + hostConfig.getPrivileged());
                
                
                hostConfig.withMemory(Long.MAX_VALUE);
                //Isolation.HYPERV.getValue();   
                hostConfig.getMemoryReservation();
                hostConfig.getMemory();
                System.out.print("with container memory " + hostConfig.getMemory());
                
                
                hostConfig.getBinds();
                hostConfig.getDevices();
                
                // set container with dns over config panel
                     // add dns String 
                String[] stdns = new String [] {stdns1,stdns2};
                hostConfig.withDns(stdns);
                hostConfig.getDns();
                System.out.print("with dns " + hostConfig.getDns() + "\n");
                
                
                // add dns search from config panel
                hostConfig.withDnsSearch(ConfigPanel.styourdomainname);
                hostConfig.getDnsSearch();
                System.out.print("with dns search config " + hostConfig.getDnsSearch() + "\n");
                
                // docker runtime over ConfigPanel
                hostConfig.withRuntime(stcomboruntime);
                hostConfig.getRuntime();
                System.out.print("with Docker Container runtime " + hostConfig.getRuntime() + "\n");
                
                
                 // isolation process
                 
                hostConfig.withIsolation(Isolation.DEFAULT);
                hostConfig.getIsolation();
                System.out.print("with isolation mode linux only default is supported " + hostConfig.getIsolation() + "\n");
                
                
                
                // ipc mode
                //hostConfig.withIpcMode("private");
                hostConfig.withIpcMode("host");
                hostConfig.getIpcMode();
                System.out.print("with IpcMode " + hostConfig.getIpcMode() + "\n");
                
                
                
                // cgroup host
                //hostConfig.withCgroup("private");
                hostConfig.withCgroup("host");
                hostConfig.getCgroup();
                System.out.print("with cgroup mode " + hostConfig.getCgroup() + "\n");
                                
                // set kernel memory to max
                hostConfig.withKernelMemory(Long.MAX_VALUE);
                hostConfig.getKernelMemory();
                System.out.print("wiht kernel Memory " + hostConfig.getKernelMemory() + "\n");
                
                
                
                hostConfig.withMemorySwap(Long.MAX_VALUE);
                hostConfig.getMemorySwap();
                System.out.print("with memory Swap " + hostConfig.getMemorySwap() + "\n");
                
                hostConfig.withMemory(Long.MAX_VALUE);
                hostConfig.getMemory();
                System.out.print("with memory " + hostConfig.getMemory() + "\n");
                
                
                hostConfig.withCpuShares(2048);
                hostConfig.getCpuShares();
                System.out.print("ipfire cpushare - firewall config to " + hostConfig.getCpuShares() + "\n");
                
                // jgsoftwares/openwrt23.05:nftbridgelayer2ext4
                //dockerClient.pullImageCmd("jgsoftwares/ipfire")
                //.withTag("cloud")
                //.exec(new PullImageResultCallback())
                //.awaitCompletion(30, TimeUnit.SECONDS);
               
            String stcontainername = "ipfire";
            // check image exist
            boolean imagenotexist = false;
            try
            {
                dockerClient.inspectContainerCmd(stcontainername).exec();
            } catch(NotFoundException e)
            {
                System.out.print("error search image " + e);
            }
            if(imagenotexist == true)
            {
                System.out.print("ipfire image exist" + "\n");
            }
            else
            {         
                // jgsoftwares/openwrt23.05:nftbridgelayer2ext4 
               
                String stimagetag = "cloud";
                String stopenwrt2305host = "jgsoftwares/ipfire:" + stimagetag;
                dockerClient.pullImageCmd("jgsoftwares/ipfire:" + stimagetag).exec(new PullImageResultCallback()).awaitSuccess();
            }
                
           // dockerClient = DockerClientBuilder.getInstance().build();  
            getDockerClient(dockerClient);
           // Volume vmdockersoc = new Volume("/var/run/docker.sock:/var/run/docker.sock");
           
            CreateContainerResponse container;
            container = dockerClient.createContainerCmd("jgsoftwares/ipfire:cloud")
                   
                    .withName("ipfire")
                    .withUser("root")
                    .withHostConfig(hostConfig)
                
                    //.withExposedPorts(tcp80)
                    // .withExposedPorts(tcp1527)
                    .withAttachStderr(true)
                    .withAttachStdin(true)
                    .withAttachStdout(true)
                    .withDomainName(styourdomainname)
                    //.withIpv4Address(stwanip)
                    .withStdinOpen(Boolean.TRUE) 
                    //.withWorkingDir("/root")
                    .exec();
            
             dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec();    
            
        
             dockerClient.startContainerCmd(container.getId()).exec();
             System.out.print("start config for conatiner " + "\n");
            
     
             
             // set network speed to 100 mbit half with 
             // network interface eth0
             //ExecCreateCmdResponse networkspeedeth0 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "ethtool -s eth0 speed 100 duplex half").withAttachStdout(true).withAttachStderr(true).exec();
             //dockerClient.execStartCmd(networkspeedeth0.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             //System.out.print("set network speed to 100 mbit half with ethtool -- command -- ethtool -s eth0 speed 100 duplex half" + "\n");
             
             // delete provider dns2 file
             ExecCreateCmdResponse setdns1 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "rm -rf /var/run/dns1").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(setdns1.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("delete dns1 file form path /var/run/dns1" + "\n");
             
             // delete provider dns2 file
             ExecCreateCmdResponse setdns2 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "rm -rf /var/run/dns2").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(setdns2.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("delete dns1 file form path /var/run/dns2" + "\n");
             
             
             // local commit container
             // jgsoftwares/ipfire:cloud 
             dockerClient.commitCmd("ipfire").withRepository("jgsoftwares/ipfire").withTag("cloud").exec();
             System.out.print("local image commit jgsoftwares/ipfire:cloud");
             System.out.print("restart container openwrt2305host to run iptables in memory of this container" + "\n");
             
             
             
             
        } catch(Exception e)
        {
            System.out.print("Fehler " + e);
        }
      }

    private String fwriter(String ceT1CESTM350M10503__etcTZ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
    
    public void starthttpfileserver(String stport)
    {
        
        String sthttpfileserver = null;
        String sthttpfileservertag = null;
        
        int inthttpport = Integer.parseInt(stport);
        ExposedPort tcphttp = ExposedPort.tcp(inthttpport);
        Ports portBindings = new Ports();
        portBindings.bind(tcphttp, Ports.Binding.bindPort(inthttpport));
        
        // add volume - dockersocket 
        Volume filevolume = new Volume("/usr/share/apache2/htdocs/");
        
        
           // dns server config
                de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig publicdnsserverconfig = new de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig();
           
                String stdnsserver = ConfigPanel.stpubdnsserver;
                // returns string dnspulicserver ipdns1 ipdns2

                String stdns1 = null;
                String stdns2 = null;

                publicdnsserverconfig.publicdns(stdnsserver, stdns1, stdns2);

                stdns1 = publicdnsserverconfig.getStdns1();
                stdns2 = publicdnsserverconfig.getStdns2();
        
        
                Network network = dockerClient.inspectNetworkCmd().withNetworkId(stinterfacename).exec();
                HostConfig hostConfig = HostConfig.newHostConfig().withPortBindings(PortBinding.parse(inthttpport + ":" + inthttpport));

                hostConfig.withNetworkMode(stinterfacename);
                hostConfig.getNetworkMode();
                System.out.print("start network mode for httpfileserver openwrt " + hostConfig.getNetworkMode() + "\n");
        
                //hostConfig.withCapAdd(com.github.dockerjava.api.model.Capability.NET_ADMIN)
                hostConfig.withCapAdd(Capability.NET_ADMIN);
                hostConfig.withCapAdd(Capability.NET_RAW);
                hostConfig.withCapAdd(Capability.SYS_ADMIN);
                hostConfig.getCapAdd();
                System.out.print("with capability " + hostConfig.getCapAdd() + "\n");
                
                hostConfig.isUserDefinedNetwork();
                
                hostConfig.withPrivileged(Boolean.TRUE);
                hostConfig.getPrivileged();
                System.out.print("wiht privilged mode " + hostConfig.getPrivileged() + "\n");
                
                //Isolation.PROCESS.getValue();
                //Isolation.HYPERV.getValue();
                hostConfig.withBinds(new Bind("/srv/www/htdocs/", filevolume));
                System.out.print("with mount volume for httpfileserver " + "/srv/www/htdocs" + "\n");
                
                
                 hostConfig.withMemory(Long.MAX_VALUE);
                //Isolation.HYPERV.getValue();   
                hostConfig.getMemoryReservation();
                hostConfig.getMemory();
                System.out.print("with memory " + hostConfig.getMemory() + "\n");
                
                hostConfig.withMemorySwap(Long.MAX_VALUE);
                hostConfig.getMemorySwap();
                System.out.print("with memory Swap " + hostConfig.getMemorySwap() + "\n");

                hostConfig.withCpuShares(0);
                hostConfig.getCpuShares();
                System.out.print("landingpage cpushare - firewall config to " + hostConfig.getCpuShares() + "\n");
                
                //hostConfig.getBinds();
                //hostConfig.getDevices();
                
                 // add dns String 
                String[] stdns = new String [] {stdns1,stdns2};
                hostConfig.withDns(stdns);
                hostConfig.getDns();
                System.out.print("with dns " + hostConfig.getDns() + "\n");
                
                
                hostConfig.withRuntime(stcomboruntime);
                hostConfig.getRuntime();
                System.out.print("with runtime " + hostConfig.getRuntime() + "\n");
                
                 // isolation process
                hostConfig.withIsolation(Isolation.DEFAULT);
                hostConfig.getIsolation();
                System.out.print("with isolation on openwrt only default is supported " + hostConfig.getIsolation() + "\n");
                
                // ipc mode
                hostConfig.withIpcMode("host");
                hostConfig.getIpcMode();
                System.out.print("with ipc mode " + hostConfig.getIpcMode() + "\n");
                // cgroup host
                hostConfig.withCgroup("host");
                hostConfig.getCgroup();
                System.out.print("with cgroup mode " + hostConfig.getCgroup() + "\n");
                
                  // set kernel memory to max
                hostConfig.withKernelMemory(Long.MAX_VALUE);
                hostConfig.getKernelMemory();
                System.out.print("with kernel memory " + hostConfig.getKernelMemory() + "\n");
                
                //dockerClient = DockerClientBuilder.getInstance().build();  
                getDockerClient(dockerClient);

            
            String stcontainername = "openwrthttpfileserver";
            // check image exist
            boolean imagenotexist = false;
            try
            {
                dockerClient.inspectContainerCmd(stcontainername).exec();
            } catch(NotFoundException e)
            {
                System.out.print("error search image " + e);
            }
            if(imagenotexist == true)
            {
                System.out.print("landingpage image exist" + "\n");
            }
            else
            {
               sthttpfileservertag = "shell";
                //String stjavaversion = ConfigPanel.stjavaversion.toString();
                sthttpfileserver = "jgsoftwares/openwrthttpfileserver" + ":" + sthttpfileservertag;
                dockerClient.pullImageCmd(sthttpfileserver).exec(new PullImageResultCallback()).awaitSuccess();
            }
            
            
            // start container 
            CreateContainerResponse container = null;
            System.out.println("start openwrt container " + "\n");
                    container = dockerClient.createContainerCmd("jgsoftwares/openwrthttpfileserver:shell")
                    //.withCmd("apachectl restart")
                    .withName(stcontainername)
                    .withUser("root")
                    //.withCmd(cmd)
                    .withHostConfig(hostConfig)
                    .withExposedPorts(tcphttp)
                    // .withExposedPorts(tcp1527)
                    .withDomainName(styourdomainname)
                    //.withIpv4Address(stwanip)
                    .withStdinOpen(Boolean.TRUE)
                    .withAttachStderr(true)
                    .withAttachStdin(true)
                    .withAttachStdout(true)
                    // timesettings
                    //.withCmd(stinstall)
                    //.withCmd(sttime)
                    .withWorkingDir("/root")
                    
                            // 
                            //   jTextArea1.append("opkg install alpine-repositories" +"apk add --allow-untrusted tzdata" + "\n");
                            // jTextArea1.append("add CET-1CEST,M3.5.0,M10.5.0/3 to  /etc/TZ - for germany" + "/n");
                    // install alpine repositorys
                    
                    //.withCmd(stshell, stinstall)
                    //.withCmd(stshell, sttime)     
                    .exec();
                    
                  
                    dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec();    
                    dockerClient.startContainerCmd(container.getId()).exec();
                    
            // container config 
            try
            {
                  /*
                
                clear files in the docker container 
                openwrt2305host
             */
             // cat /dev/null > /etc/hosts
             String clearhostsfile = "/etc/hosts";
             ExecCreateCmdResponse execlearhost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearhostsfile).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file /etc/hosts" + "\n");
             
             // cat /dev/null > /etc/sysctl.conf
             String clearsysctl = "/etc/sysctl.conf";
             ExecCreateCmdResponse execlearsysctl = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearsysctl).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearsysctl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file /etc/sysctl.conf" + "\n");    
             
             // resolv.conf
             // cat /dev/null > /etc/resolv.conf
             String clearresolvconf = "/etc/resolv.conf";
             ExecCreateCmdResponse execlearresolvconf = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearresolvconf).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearresolvconf.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file /etc/resolv.conf" + "\n");   
             
              // TZ
             // cat /dev/null > /etc/TZ
             String cleartimezone = "/etc/TZ";
             ExecCreateCmdResponse execleartimezone = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + cleartimezone).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execleartimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file /etc/TZ" + "\n"); 
             
             
             // clear /etc/hostname
             // cat /dev/null > /etc/hostname
             String clearhostname = "/etc/hostname";
             ExecCreateCmdResponse execlearhostname = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "cat /dev/null > " + clearhostname).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhostname.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("flush file /etc/hostname" + "\n");
             
               /*
             
                write config to docker container 
                openwrthttpfileserver
             
             */
             
             // add string with wanip and hostname
             String sthost = (String) ConfigPanel.stwanip + "  " + ConfigPanel.styourdomainname;
             ExecCreateCmdResponse execaddstringtohost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + sthost + " >> /etc/hosts").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtohost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/host " + sthost + "\n");
             
            // /etc/sysctl.conf
             // add string to /etc/sysctl.conf
             // net.ipv6.conf.all.disable_ipv6=0
             // net.ipv4.conf.all.src_valid_mark=0
             // net.ipv4.ip_forward=0   
             String noforward = "net.ipv4.ip_forward=0";
             ExecCreateCmdResponse execaddstringtosyscontl = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noforward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtosyscontl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/sysctl.conf" + noforward + "\n");
             
             
             String nosrcvalid = "net.ipv4.conf.all.src_valid_mark=0";
             ExecCreateCmdResponse execaddstringnosourcevalid = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + nosrcvalid + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnosourcevalid.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/sysctl.conf" + nosrcvalid + "\n");
             
             
             String noipv6forward = "net.ipv6.conf.all.disable_ipv6=0";
             ExecCreateCmdResponse execaddstringnoipv6forward = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noipv6forward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnoipv6forward.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/sysctl.conf" + noipv6forward + "\n");
             
             
             String nodynamicaddr = "net.ipv4.ip_dynaddr=0";
             ExecCreateCmdResponse execaddstringnodynamicaddr = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + nodynamicaddr + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnodynamicaddr.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/sysctl.conf "+ nodynamicaddr + "\n");
             
             
             // get Timezone String 
             String sttimezonecountry = ConfigPanel.stcomboboxtimezone;
             
             //load timezoneconfig
             // write timezone string to /etc/TZ
            
             de.jgsoftwares.guiserverpanel.config.Timezoneconfig tmconfig = new de.jgsoftwares.guiserverpanel.config.Timezoneconfig();     
             String stgettimezone = tmconfig.timezoneopenwrt(sttimezonecountry, cleartimezone);
             ExecCreateCmdResponse execaddstringtotimezone = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + stgettimezone + " >> /etc/TZ").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtotimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/TZ" + stgettimezone + "\n");

            // resolv.conf
             //String stdns1 = null;
             //String stdns2 = null;
             
          
             
         
             //nameserver dnsip1
             ExecCreateCmdResponse execaddstringpublicdnsip1 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "nameserver " + stdns1 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip1.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add Stirng /etc/resolv.conf" + stdns1 + "\n");

             //nameserver dnsip2
             ExecCreateCmdResponse execaddstringpublicdnsip2 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "nameserver " + stdns2 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip2.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String /etc/resolv.conf " + stdns2 + "\n");
            
             // add search with domainname
             ExecCreateCmdResponse execaddstringsearchdomain = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "search " + ConfigPanel.styourdomainname + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringsearchdomain.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String /etc/resolv.conf "+ ConfigPanel.styourdomainname + "\n");

             // interface name
             ExecCreateCmdResponse execaddstringinterface = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "interface orange0 " + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringinterface.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String /etc/resolv.conf" + "inteface oragne0" + "\n");

             // dnsec
             ExecCreateCmdResponse execaddstringdnssearch = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "DNSSEC=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnssearch.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String /etc/resolv.conf "+  "DNSSEC=yes " + "\n");

             // dnsovertls
             ExecCreateCmdResponse execaddstringdnsovertls = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "DNSOverTLS=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnsovertls.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/resolv.conf " + "DNSOverTLS=yes " + "\n");
             
             // /etc/hostname
             // add hostname for openwrt2305
             ExecCreateCmdResponse execaddstringhostname = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + "openwrtlandingpage" + " >> /etc/hostname").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringhostname.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("add String to /etc/hostname " + "openwrtlandingpage " + "\n");
    
             // run command to update the date time
             // opkg update && opkg install zoneinfo-all
             ExecCreateCmdResponse execrunzoneinfo = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "opkg update && opkg install zoneinfo-all").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execrunzoneinfo.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("install packages to container " + "opkg update && opkg install zoneinfo-all" + "\n");
             
            // run command to update the date time
            // opkg update && opkg install zoneinfo-all
            ExecCreateCmdResponse execrunapachehttp = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "apachectl restart").withAttachStdout(true).withAttachStderr(true).exec();
            dockerClient.execStartCmd(execrunapachehttp.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
            System.out.print("start apache webserver with command " + "apachectl restart " + "\n");
             
             // install iptables firewall package
             // create dir
             // /var/run -- for lock file for iptables
             // opkg install iptables-legacy
             ExecCreateCmdResponse execreatedir = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "mkdir /var/run/").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execreatedir.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("create direcotry /var/run" + "\n");
             
             ExecCreateCmdResponse execinstalliptables = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "opkg install iptables-legacy").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execinstalliptables.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("install iptables-legacy with" + "command opkg install iptables-legacy" + "\n");
             
             // iptables save
             ExecCreateCmdResponse execiptablessave = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "iptables-legacy-save").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execiptablessave.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("run command optables-leagcy-save in container openwrthttpfileser" + "\n");
             
             
             System.out.print("restart container openwrt2305host to run iptables in memory of this container");
             
             /*
             
                 sthttpfileservertag = "shell";
                //String stjavaversion = ConfigPanel.stjavaversion.toString();
                sthttpfileserver = "jgsoftwares/openwrthttpfileserver" + sthttpfileservertag;
             */
             // commit
             // jgsoftwares/openwrt23.05landingpage   java11
             dockerClient.commitCmd(stcontainername).withRepository("jgsoftwares/openwrthttpfileserver").withTag("shell").exec();
             System.out.print("local image commit jgsoftwares/openwrthttpfileserver:shell");
         
             
            } catch (Exception e)
            {
                System.out.print("Error " + e);
            }
    }
    
    public void startreverseproxy(String port, List portlist)
    {
        
    }
    
    @Override
    public void copyjartohttpfileserver(File httpfile, String stpath)
    {
       try {
            String containerid = "openwrthttpfileserver";
            
            // dockerClient = DockerClientBuilder.getInstance().build();
            getDockerClient(dockerClient);
            
            String containerID = dockerClient.inspectContainerCmd(containerid).getContainerId();
            
            String resource = httpfile.toString();
      
            // dockerClient.copyArchiveToContainerCmd("openwrtlandingpagedebug")
            //        .withRemotePath("/root/")
            //        .withTarInputStream(tarArchiveInputStream)
            //        .exec();
            dockerClient.copyArchiveToContainerCmd(containerID)
                    
                    .withHostResource(resource)
                    .withRemotePath("/usr/share/apache2/htdocs/" + stpath).exec();
            //Landingpage.jLabel1chooser.setText("file upload to landingpagecontainer " + resource + "\n");
            httpfileserver.jLabel7.setText("upload file to " + resource + "\n");
            System.out.print("file " + httpfile + "\n");
            
            
            
        } catch (Exception ex) {
            System.getLogger(dockerclient.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    // copy jar from local to docker container landingpage
    public void copyjartolandingpage(File landingpagefile)
    {
        try {
            String containerid = "openwrtlandingpagedebug";
            
            // dockerClient = DockerClientBuilder.getInstance().build();
            getDockerClient(dockerClient);
            
            String containerID = dockerClient.inspectContainerCmd(containerid).getContainerId();
            
            String resource = landingpagefile.toString();
            
            
           
           
            // dockerClient.copyArchiveToContainerCmd("openwrtlandingpagedebug")
            //        .withRemotePath("/root/")
            //        .withTarInputStream(tarArchiveInputStream)
            //        .exec();
            dockerClient.copyArchiveToContainerCmd(containerID)
                    
                    .withHostResource(resource)
                    .withRemotePath("/root").exec();
            Landingpage.jLabel1chooser.setText("file upload to landingpagecontainer " + resource + "\n");
            System.out.print("file " + landingpagefile + "\n");
            
            
        } catch (Exception ex) {
            System.getLogger(dockerclient.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    // copy jar from local to docker container lanserver
    public void copyjartolanserver(File lanserverfile)
    {
        try {
            String containerid = "openwrtlanservertcp";
            
            // dockerClient = DockerClientBuilder.getInstance().build();
            getDockerClient(dockerClient);
            
            String containerID = dockerClient.inspectContainerCmd(containerid).getContainerId();
            
            String resource = lanserverfile.toString();
            
            
           
           
            // dockerClient.copyArchiveToContainerCmd("openwrtlandingpagedebug")
            //        .withRemotePath("/root/")
            //        .withTarInputStream(tarArchiveInputStream)
            //        .exec();
            dockerClient.copyArchiveToContainerCmd(containerID)
                    .withHostResource(resource)
                    .withRemotePath("/root").exec();
            LanServerTCP.jLabel3.setText("file upload to openwrtlanservertcp  " + resource + "\n");
            System.out.print("file " + lanserverfile + "\n");
            
            
        } catch (NotFoundException ex) {
            System.getLogger(dockerclient.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
    
    
    // run httpfileserver 
    public void runopenwrthttpfileserver()
    {
        
    }

    
    @Override
    public void attachopenwrt2305hostcontainer()
    {
        
       

                


    }
    
    // delete file form dockercontainer httpfileserver
    @Override
    public void deletefilehttpfileserver(String fileresourcepath)
    {
       
       
    }
    
    
    @Override
    public void openwrt2305hostsaveiptables()
    {
          try
          {
            String containerid = "openwrt2305host";
            
            // dockerClient = DockerClientBuilder.getInstance().build();
            getDockerClient(dockerClient);
            
            //String containerID = dockerClient.inspectContainerCmd(containerid).getContainerId();
            InspectContainerResponse container = dockerClient.inspectContainerCmd("" + containerid.toString()).exec();
            // iptables save
             ExecCreateCmdResponse execiptablessave = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "iptables-legacy-save").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execiptablessave.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             System.out.print("openwrt2305host container command iptables-legacy-save" + "\n");
             System.out.print("restart container openwrt2305host to run iptables in this container memory" + "\n");
             
             
             // set network speed to 100 mbit half with 
             // network interface eth0
             //ExecCreateCmdResponse networkspeedeth0 = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "ethtool -s eth0 speed 100 duplex half").withAttachStdout(true).withAttachStderr(true).exec();
             //dockerClient.execStartCmd(networkspeedeth0.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             //System.out.print("set network speed to 100 mbit half with ethtool -- command -- ethtool -s eth0 speed 100 duplex half" + "\n");
             
             
          } catch(Exception e)
          {
              
          }
          
    }
}
