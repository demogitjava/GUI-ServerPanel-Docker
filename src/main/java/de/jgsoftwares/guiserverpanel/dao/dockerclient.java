package de.jgsoftwares.guiserverpanel.dao;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.tree.DefaultMutableTreeNode;

import de.jgsoftwares.guiserverpanel.frames.MainPanel;



public class dockerclient
{

    Process process;
    BufferedReader reader;


    public dockerclient()
    {

    }

    public void listContainers()
    {


    }

    public void startdockerclient()
    {


        // list images
        try {
            process = Runtime.getRuntime().exec("docker images");

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                MainPanel.dockerimages.add(new DefaultMutableTreeNode(line));
               // System.out.println(line);
            }
        } catch (IOException e) {
            System.out.print("Error " + e);
        }

        // list containers
        try {
            process = Runtime.getRuntime().exec("docker container ls");

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                MainPanel.dockercontainers.add(new DefaultMutableTreeNode(line));
                // System.out.println(line);
            }
        } catch (IOException e) {
          System.out.print("Error " + e);
        }

    }
    
    public void startlanservercontiner(String struncontainer)
    {
          // list start container 
          // lanserver tcp 
        try {
				process = Runtime.getRuntime().exec("xterm -hold ");
				
	
        } catch(Exception e)
        {
            System.out.print("Error " +e);
        }
        
        
       
    }
    
    
    // lanserver tcp container 
    // function restart 
    public void restartlanserver(String strestartcontainer)
    {
        
        
        // restart docker contaienr 
        // lanserver tcp
            try {
				process = Runtime.getRuntime().exec("xterm -hold ");
				
	
        } catch(Exception e)
        {
            System.out.print("Error " +e);
        }
    }


    // lanserver tcp set systemtime
    public void setsystemtimetolanservertcp()
    {
        
       
           try {
				process = Runtime.getRuntime().exec("xterm -hold ");
				
	
        } catch(Exception e)
        {
            System.out.print("Error " +e);
        }
    }

    // start xterm window for derby db command
    public void startderbydb()
    {
    
           try {
				process = Runtime.getRuntime().exec("xterm -hold ");
				
	
        } catch(Exception e)
        {
            System.out.print("Error " +e);
        }
    }



        // start xterm for h2 command 
    public void starth2db()
    {
    try {
                                  process = Runtime.getRuntime().exec("xterm -hold ");


          } catch(Exception e)
          {
              System.out.print("Error " +e);
          }

    }

    public void pullImage(String imageName)
    {


    }
    
    
    
}
