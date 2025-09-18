package de.jgsoftwares.guiserverpanel.dao;


import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Network;

import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import java.io.BufferedReader;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Capability;

import com.github.dockerjava.core.DockerClientBuilder;
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
    
    // List of dockerimages
    // from /var/run/docker.sock
    public List<Image> dockerimages;
    public List<Container> dockercontainers;
    
    public dockerclient()
    {
       dockerClient = DockerClientBuilder.getInstance().build();    
       dockerimages = dockerClient.listImagesCmd().exec();
       dockercontainers = dockerClient.listContainersCmd().exec();
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
              
                
            dockerClient.pullImageCmd("jgsoftwares/openwrt23.05lanserver")
                .withTag("11")
                .exec(new PullImageResultCallback())
                .awaitCompletion(30, TimeUnit.SECONDS);
           
                
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
                
                
            dockerClient.pullImageCmd("jgsoftwares/openwrt23.05derbydb")
                .withTag("10-14-02")
                .exec(new PullImageResultCallback())
                .awaitCompletion(30, TimeUnit.SECONDS);     
                
          // opkg install zoneinfo-all              
            dockerClient = DockerClientBuilder.getInstance().build();    
            CreateContainerResponse container = dockerClient.createContainerCmd("jgsoftwares/openwrt23.05derbydb:10-14-02")
                 .withCmd("/bin/ash", "/root/startderbydb.sh")
                 .withName("openwrtderbydb")
                 .withHostConfig(hostConfig)
                  
                // .withExposedPorts(tcp1527)
                // .withExposedPorts(tcp1527)
                 .withDomainName(styourdomainname)
                 
                 //.withIpv4Address(stwanip)
                 //.withStdinOpen(Boolean.TRUE)
                 //.withWorkingDir("/root")
                 .exec();
            
            dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec();     
            dockerClient.startContainerCmd(container.getId()).exec();

            
            
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
            try {
                                  process = Runtime.getRuntime().exec("xterm -hold ");
          } catch(IOException e)
          {
              System.out.print("Error " +e);
          }
    }

    /**
     *
     * @param stlandingpage
     */
    @Override
    public void startlandingpage(String stlandingpage)
    {
        
        
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
                hostConfig.isUserDefinedNetwork();
                hostConfig.withPrivileged(Boolean.TRUE);
                hostConfig.getIsolation();
                hostConfig.withRuntime(stcomboruntime);
              
            
                dockerClient.pullImageCmd("jgsoftwares/openwrt23.05landingpage")
                .withTag("java11")
                .exec(new PullImageResultCallback())
                .awaitCompletion(30, TimeUnit.SECONDS);
               
                
            dockerClient = DockerClientBuilder.getInstance().build();  
            
            CreateContainerResponse container;
            container = dockerClient.createContainerCmd("jgsoftwares/openwrt23.05landingpage:java11")
                    .withCmd("/bin/ash", "/root/runlandingpage.sh")
                    .withName("openwrtlandingpagedebug")
                    .withUser("root")
                    .withHostConfig(hostConfig)
                    .withExposedPorts(tcp80)
                    // .withExposedPorts(tcp1527)
                    .withDomainName(styourdomainname)
                    //.withIpv4Address(stwanip)
                    .withStdinOpen(Boolean.TRUE)
                    .withWorkingDir("/root")
                    .exec();
            
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
                hostConfig.isUserDefinedNetwork();
                hostConfig.withPrivileged(Boolean.TRUE);
                hostConfig.getIsolation();
                hostConfig.withRuntime(stcomboruntime);
              
                // jgsoftwares/openwrt23.05:nftbridgelayer2ext4
                dockerClient.pullImageCmd("jgsoftwares/openwrt23.05")
                .withTag("nftbridgelayer2ext4")
                .exec(new PullImageResultCallback())
                .awaitCompletion(30, TimeUnit.SECONDS);
               
                
            dockerClient = DockerClientBuilder.getInstance().build();  
            
            CreateContainerResponse container;
            container = dockerClient.createContainerCmd("jgsoftwares/openwrt23.05:nftbridgelayer2ext4")
                    //.withCmd("/bin/ash", "/root/runlandingpage.sh")
                    .withName("openwrt2305host")
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

                HostConfig hostConfig = HostConfig.newHostConfig().withPortBindings(PortBinding.parse("444:444"));
                
                // add container to host network
                hostConfig.withNetworkMode(stinterfacename).getKernelMemory();
                hostConfig.isUserDefinedNetwork();
               
                hostConfig.withPrivileged(Boolean.TRUE);
                hostConfig.getIsolation();
                hostConfig.withRuntime(stcomboruntime);
              
            
                dockerClient.pullImageCmd("jgsoftwares/ipfire")
                .withTag("greenred")
                .exec(new PullImageResultCallback())
                .awaitCompletion(30, TimeUnit.SECONDS);
           
                
            dockerClient = DockerClientBuilder.getInstance().build();    
            CreateContainerResponse container = dockerClient.createContainerCmd("jgsoftwares/ipfire:greenred")
                 .withName("ipfire")
                 //.withUser("root") 
                 //.withHostConfig(hostConfig)
                 //.withExposedPorts(tcp444)
                // .withExposedPorts(tcp1527)
                 //.withDomainName(styourdomainname)
                 //.withIpv4Address(stwanip)
                 //.withStdinOpen(Boolean.TRUE)
                 
                 //.withWorkingDir("/root")
                 .exec();
            
             dockerClient.connectToNetworkCmd().withContainerId(container.getId()).withNetworkId(network.getId()).exec();    
            
      
        } catch(Exception e)
        {
            System.out.print("Fehler " + e);
        }
      }
                
     
}
