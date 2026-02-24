package de.jgsoftwares.guiserverpanel.dao;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Network;


import com.github.dockerjava.core.async.ResultCallbackTemplate;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import java.io.BufferedReader;

        
import com.github.dockerjava.api.model.Capability;
import com.github.dockerjava.api.model.Volume;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.core.command.PullImageResultCallback;
import static de.jgsoftwares.guiserverpanel.frames.ConfigPanel.stcomboruntime;
import static de.jgsoftwares.guiserverpanel.frames.ConfigPanel.stinterfacename;
import static de.jgsoftwares.guiserverpanel.frames.ConfigPanel.styourdomainname;


import javax.swing.tree.DefaultMutableTreeNode;

import de.jgsoftwares.guiserverpanel.frames.MainPanel;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Arrays;
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

    
    
    
    PipedInputStream inputStream;
    PipedOutputStream outputStream;
   
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
           stimagetag = "11";
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
              
            
            
               
                ExposedPort tcp8443 = ExposedPort.tcp(8443);
               
                Ports portBindings = new Ports();
             
                portBindings.bind(tcp8443, Ports.Binding.bindPort(8443));     
                // connect to network like eth0 or eth0.10
                Network network = dockerClient.inspectNetworkCmd().withNetworkId(stinterfacename).exec();

                HostConfig hostConfig = HostConfig.newHostConfig().withPortBindings(PortBinding.parse("8443:8443"));
                
                // add container to host network
                hostConfig.withNetworkMode(stinterfacename).getKernelMemory();
                hostConfig.isUserDefinedNetwork();
               
                hostConfig.withPrivileged(Boolean.TRUE);
                hostConfig.getIsolation();
                hostConfig.withRuntime(stcomboruntime);
              
                
            dockerClient.pullImageCmd(stimage)
                .withTag(stimagetag)
                .exec(new PullImageResultCallback())
                .awaitCompletion(30, TimeUnit.SECONDS);
            
        
            switch(contsystem)
            {
                case "openwrt":
                {
                    
                    
                  //dockerClient = DockerClientBuilder.getInstance().build();    
                  getDockerClient(dockerClient);
                  
                  CreateContainerResponse container = dockerClient.createContainerCmd(stimage + ":" + stimagetag)
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

                 // start container openwrtlanserver
                 dockerClient.startContainerCmd(container.getId()).exec();
               
                   String noforward = "net.ipv4.ip_forward=0";
         ExecCreateCmdResponse execaddstringtohost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noforward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
         dockerClient.execStartCmd(execaddstringtohost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
        
                  
               
                 // install time zones
                 //dockerClient.execCreateCmd(container.getId()).withCmd("/bin/ash", "-c", "opkg install zoneinfo-all").exec();

                 // set time zone
                 //dockerClient.execCreateCmd(container.getId()).withCmd("/bin/ash", "-c", "echo 'CET-1CEST,M3.5.0,M10.5.0/3' > /etc/TZ").exec();
                
                 //System.out.print(dockerClient.startContainerCmd(container.getId()) +  "/n");
               
                 // get access to running container 
                 /*
                    jTextArea1.append("opkg install alpine-repositories" + "/n");
            jTextArea1.append("opkg install zoneinfo-all" + "/n");
            jTextArea1.append("check time settings /etc/TZ - for germany CET-1CEST,M3.5.0,M10.5.0/3" + "/n");
                 */
                
                 
               
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

                HostConfig hostConfig = HostConfig.newHostConfig().withPortBindings(PortBinding.parse("1527:1527"));
                
                // add container to host network
                hostConfig.withNetworkMode(stinterfacename).getKernelMemory();
                hostConfig.isUserDefinedNetwork();
               
                hostConfig.withPrivileged(Boolean.TRUE);
                hostConfig.getIsolation();
                hostConfig.withRuntime(stcomboruntime);
                
               
                
                String imageexist = null;
                // image exist
                imageexist = de.jgsoftwares.guiserverpanel.frames.ConfigPanel.stcontainersystem;
                if (imageexist.equals("openwrt")) 
                {
                    imageexist = "jgsoftwares/openwrt23.05derbydb:10-14-02firejail";
                    //InspectImageResponse response = dockerClient.inspectImageCmd(stderbydb).exec();
                    //if(response.equals("jgsoftwares/openwrt23.05derbydb:10-14-0"))
                    //{
                     //   System.out.print("Image openwrt - derbydb exist");
                    //}
                
                    //else
                    //{
                        System.out.print("pull image " + "\n");
                        dockerClient.pullImageCmd("jgsoftwares/openwrt23.05derbydb")
                                .withTag("10-14-02")
                                .exec(new PullImageResultCallback())
                                .awaitCompletion(30, TimeUnit.SECONDS);
                        
                        
                        //  start container openwrt             
                        //dockerClient = DockerClientBuilder.getInstance().build();
                         getDockerClient(dockerClient);
                        System.out.print("start container " + "\n");

                        CreateContainerResponse container = dockerClient.createContainerCmd("jgsoftwares/openwrt23.05derbydb:10-14-02")
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
            
                         String noforward = "net.ipv4.ip_forward=0";
                         ExecCreateCmdResponse execaddstringtohost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noforward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
                         dockerClient.execStartCmd(execaddstringtohost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();

                         
                    //}
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
                         
                         String noforward = "net.ipv4.ip_forward=0";
                         ExecCreateCmdResponse execaddstringtohost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noforward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
                         dockerClient.execStartCmd(execaddstringtohost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();

                         
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
                         
                         String noforward = "net.ipv4.ip_forward=0";
                         ExecCreateCmdResponse execaddstringtohost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noforward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
                         dockerClient.execStartCmd(execaddstringtohost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();

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
                
                // add container to host network
                hostConfig.withNetworkMode(stinterfacename).getKernelMemory();
                hostConfig.isUserDefinedNetwork();
               
                hostConfig.withPrivileged(Boolean.TRUE);
                hostConfig.getIsolation();
                hostConfig.withRuntime(stcomboruntime);
                
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
           stimagetag = "java11";
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
                ExposedPort tcp80 = ExposedPort.tcp(80);
                ExposedPort tcp1527 = ExposedPort.tcp(1527);
                Ports portBindings = new Ports();
                //portBindings.bind(tcp1527, Ports.Binding.bindPort(1527));
                portBindings.bind(tcp80, Ports.Binding.bindPort(80));

                
                // connect to network like eth0 or eth0.10
                Network network = dockerClient.inspectNetworkCmd().withNetworkId(stinterfacename).exec();

                HostConfig hostConfig = HostConfig.newHostConfig().withPortBindings(PortBinding.parse("80:80"), PortBinding.parse("1527:1527"));
                
                // add container to host network
                hostConfig.withNetworkMode(stinterfacename).getKernelMemory();
                //hostConfig.withCapAdd(com.github.dockerjava.api.model.Capability.NET_ADMIN)
                hostConfig.withCapAdd(Capability.NET_ADMIN);
                hostConfig.withCapAdd(Capability.NET_RAW);
                hostConfig.withCapAdd(Capability.SYS_ADMIN);
                hostConfig.isUserDefinedNetwork();
                hostConfig.withPrivileged(Boolean.TRUE);
                hostConfig.getIsolation();
                hostConfig.withRuntime(stcomboruntime);
               // hostConfig.withSysctls(sysctls)
                /*
                dockerClient.pullImageCmd(stimage)
                .withTag(stimagetag)
                .exec(new PullImageResultCallback())
                .awaitCompletion(30, TimeUnit.SECONDS);
                */
                
            //dockerClient = DockerClientBuilder.getInstance().build();  
             getDockerClient(dockerClient);
             
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

         String noforward = "net.ipv4.ip_forward=0";
         ExecCreateCmdResponse execaddstringtohost = dockerClient.execCreateCmd(container.getId()).withCmd("sh", "-c", "echo " + noforward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
         dockerClient.execStartCmd(execaddstringtohost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
         
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
         
                
                // connect to network like eth0 or eth0.10
                Network network = dockerClient.inspectNetworkCmd().withNetworkId(stinterfacename).exec();

                HostConfig hostConfig = HostConfig.newHostConfig();
                        //.withPortBindings(PortBinding.parse("80:80"), PortBinding.parse("1527:1527"));
                
                // add container to host network
                hostConfig.withNetworkMode(stinterfacename).getKernelMemory();
                //hostConfig.withCapAdd(com.github.dockerjava.api.model.Capability.NET_ADMIN)
                hostConfig.withCapAdd(Capability.NET_ADMIN);
                hostConfig.withCapAdd(Capability.NET_RAW);
                hostConfig.withCapAdd(Capability.SYS_ADMIN);
                hostConfig.isUserDefinedNetwork();
                hostConfig.withPrivileged(Boolean.TRUE);
                hostConfig.getIsolation();
                hostConfig.withRuntime(stcomboruntime);
               
                // jgsoftwares/openwrt23.05:nftbridgelayer2ext4
                dockerClient.pullImageCmd("jgsoftwares/openwrt23.05")
                .withTag("nftbridgelayer2ext4")
                .exec(new PullImageResultCallback())
                .awaitCompletion(30, TimeUnit.SECONDS);
               
                
            //dockerClient = DockerClientBuilder.getInstance().build();  
             getDockerClient(dockerClient);
           
            // add volume     
            Volume vmdockersoc = new Volume("/var/run/docker.sock");
           
           
            
            CreateContainerResponse container;
            container = dockerClient.createContainerCmd("jgsoftwares/openwrt23.05:nftbridgelayer2ext4")
                    .withName("openwrt2305host")
                    .withUser("root")
                    .withVolumes(vmdockersoc)
                    .withHostConfig(hostConfig)
                    //.withExposedPorts(tcp80)
                    //.withExposedPorts(tcp1527)
                    .withAttachStderr(true)
                    .withAttachStdin(true)
                    .withAttachStdout(true)
                    .withDomainName(styourdomainname)
                    //.withIpv4Address(stwanip)
                    .withStdinOpen(Boolean.TRUE) 
                    .withWorkingDir("/root")
                    .exec();
            
             dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec();    
            
             // start container
             dockerClient.startContainerCmd(container.getId()).exec();
             
             

          

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
            //InspectContainerResponse startlandingpage = (InspectContainerResponse) dockerClient.startContainerCmd(stlandingpage);
           // InspectContainerResponse startlandingpage = (InspectContainerResponse) dockerClient.startContainerCmd(stlandingpage);
           //  jgsoftwares/oraclelinux_openjdk_landingpage:hostopenwrtext4 /bin/bash /root/runlandingpage.sh
                ExposedPort tcp444 = ExposedPort.tcp(444);
              
                Ports portBindings = new Ports();
                //portBindings.bind(tcp1527, Ports.Binding.bindPort(1527));
                portBindings.bind(tcp444, Ports.Binding.bindPort(444));
   
                
             
             
                // connect to network like eth0 or eth0.10
                Network network = dockerClient.inspectNetworkCmd().withNetworkId(stinterfacename).exec();

                HostConfig hostConfig = HostConfig.newHostConfig();
                        //.withPortBindings(PortBinding.parse("80:80"), PortBinding.parse("1527:1527"));
                
                // add container to host network
                hostConfig.withNetworkMode(stinterfacename).getKernelMemory();
                //hostConfig.withCapAdd(com.github.dockerjava.api.model.Capability.NET_ADMIN)
                hostConfig.withCapAdd(Capability.NET_ADMIN);
                hostConfig.withCapAdd(Capability.NET_RAW);
                hostConfig.isUserDefinedNetwork();
                hostConfig.withPrivileged(Boolean.TRUE);
                hostConfig.getIsolation();
                hostConfig.withRuntime(stcomboruntime);
              
                // jgsoftwares/openwrt23.05:nftbridgelayer2ext4
                dockerClient.pullImageCmd("jgsoftwares/ipfire")
                .withTag("dmz")
                .exec(new PullImageResultCallback())
                .awaitCompletion(30, TimeUnit.SECONDS);
               
                
           // dockerClient = DockerClientBuilder.getInstance().build();  
            getDockerClient(dockerClient);
           // Volume vmdockersoc = new Volume("/var/run/docker.sock:/var/run/docker.sock");
           
            CreateContainerResponse container;
            container = dockerClient.createContainerCmd("jgsoftwares/ipfire:dmz")
                   
                    .withName("ipfiredmz")
                    .withUser("root")
                    .withHostConfig(hostConfig)
                    //.withExposedPorts(tcp80)
                    // .withExposedPorts(tcp1527)
                    .withAttachStderr(false)
                    .withAttachStdin(false)
                    .withAttachStdout(false)
                    .withDomainName(styourdomainname)
                    //.withIpv4Address(stwanip)
                    .withStdinOpen(Boolean.TRUE) 
                    .withWorkingDir("/root")
                    .exec();
            
             dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec();    
            
        
             dockerClient.startContainerCmd(container.getId()).exec();
        } catch(Exception e)
        {
            System.out.print("Fehler " + e);
        }
      }

    private String fwriter(String ceT1CESTM350M10503__etcTZ) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
                
     
}
