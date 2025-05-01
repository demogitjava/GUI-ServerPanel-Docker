package de.jgsoftwares.guiserverpanel.dao;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DockerClientBuilder;
import java.io.BufferedReader;

import javax.swing.tree.DefaultMutableTreeNode;

import de.jgsoftwares.guiserverpanel.frames.MainPanel;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;


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

   
    public DockerClient dockerClient;
    
    // List of dockerimages
    // from /var/run/docker.sock
    public List<Image> dockerimages;
    public List<Container> dockercontainers;
    
    public dockerclient()
    {
       
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

    
     /**
     *
     */
    @Override
    public void startdockerclient()
    {

        
        // Docker client
        // default unix:///var/run/docker.sock
        dockerClient = DockerClientBuilder.getInstance().build();    	
        dockerimages = dockerClient.listImagesCmd().exec();
        
        dockercontainers = dockerClient.listContainersCmd().exec();
        
        // get size of images
        int imagesize = dockerimages.size();
        
        // get size of containers
        int containersize = dockercontainers.size();

        // list containers
        try {
                for(int i = 0; i < imagesize; i++)
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
             for(int i = 0; i < containersize; i++)
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
          // list start container 
          // lanserver tcp 
        try {
				process = Runtime.getRuntime().exec("xterm -hold ");
				
	
        } catch(IOException e)
        {
            System.out.print("Error " +e);
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
        // restart docker contaienr 
        // lanserver tcp
            try {
				process = Runtime.getRuntime().exec("xterm -hold ");

        } catch(IOException e)
        {
            System.out.print("Error " +e);
        }
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
     */
    @Override
    public void startderbydb()
    {
           try {
				process = Runtime.getRuntime().exec("xterm -hold ");
				
	
        } catch(IOException e)
        {
            System.out.print("Error " +e);
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
     */
    @Override
    public void startlandingpage()
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
