package de.jgsoftwares.guiserverpanel.dao;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.core.DockerClientBuilder;
import java.io.BufferedReader;
import com.github.dockerjava.api.DockerClient;
import static com.github.dockerjava.api.model.Capability.NET_ADMIN;
import static com.github.dockerjava.api.model.Capability.SYS_ADMIN;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;


import javax.swing.tree.DefaultMutableTreeNode;

import de.jgsoftwares.guiserverpanel.frames.MainPanel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.tree.MutableTreeNode;



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
         
            // host image 
            // jgsoftwares/oraclelinux_openjdk_lanservertcp:host  
          
            //ExposedPort tcp4444 = ExposedPort.tcp(8443);
            //Ports portBindings = new Ports();
            //portBindings.bind(tcp4444,Ports.Binding.bindPort(8443));

            //String name = new String("oraclelinuxlanservertcp");
            //HostConfig hc = new HostConfig();
            //hc.withNetworkMode("host");
            
            // jgsoftwares/oraclelinux_openjdk_lanservertcp:host 
            
            //CreateContainerResponse response = dockerClient.
              //      createContainerCmd("oraclelinuxlanservertcp")
              //      .withHostConfig(hc)
              //      .withPortSpecs("8443:8443")
              //      .withName(name)
              //      .withImage("jgsoftwares/oraclelinux_openjdk_lanservertcp:host")
              //      .withExposedPorts(ExposedPort.tcp(8443))
              //      //.withPortBindings(tcp4444)
              //      .withAttachStderr(false)
              //      .withAttachStdin(false)
              //      .withAttachStdout(false)
              //      .exec();

                     // Actually run the container
              //       dockerClient.startContainerCmd("oraclelinuxlanservertcp").exec();
                     
                     
                ExposedPort tcp8443 = ExposedPort.tcp(8443);
                Ports portBindings = new Ports();
                portBindings.bind(tcp8443, Ports.Binding.bindPort(8443));

                HostConfig hostConfig = HostConfig.newHostConfig().withPortBindings(PortBinding.parse("8443:8443"));
                hostConfig.withNetworkMode("host");
                hostConfig.withPrivileged(Boolean.TRUE);
                hostConfig.withRuntime("io.containerd.runc.v2");
               
                
                // create container from image
                CreateContainerResponse container = dockerClient.createContainerCmd("jgsoftwares/oraclelinux_openjdk_lanservertcp:host")            
                        .withExposedPorts(tcp8443)
                        .withHostConfig(hostConfig) //.withPortBindings(portBindings))
                        .withName("oraclelinuxlanservertcp")
                        .exec();
    
                //dockerclient.execCreateCmd("containerName").withCmd("sh", "-c", "cd /root/Downloads && ./myScript.sh").exec();

                
                // run shell script 
                //dockerClient.execCreateCmd("oraclelinuxlanservertcp").withCmd("/bin/bash", "-c", "sh /root/LanServer.sh").exec();
                
                dockerClient.statsCmd("/root/LanServer.sh");
                
                // start the container
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
            as standalone docker container
        */
        InspectContainerResponse container = dockerClient.inspectContainerCmd("" + stderbydb).exec();
        dockerClient.restartContainerCmd(container.getId()).exec();
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
             InspectContainerResponse startlandingpage = (InspectContainerResponse) dockerClient.startContainerCmd(stlandingpage);
             startlandingpage.getConfig();
             
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

}
