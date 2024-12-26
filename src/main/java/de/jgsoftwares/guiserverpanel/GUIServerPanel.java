package de.jgsoftwares.guiserverpanel;



import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class GUIServerPanel implements i_GuiServerPanel
{
 
  
  public static de.jgsoftwares.guiserverpanel.frames.MainPanel mpanel;
    
  
   
  public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel("net.sf.nimrod.NimRODLookAndFeel");
            
        } catch (Exception e)
        {
            System.out.print("LookAndFeel Error  NomRoD !");
        }
        GUIServerPanel.showFrame();
        
       // SpringApplication.run(GUIServerPanel.class, args);
    }

    public static void showFrame()
    {
   

       
        mpanel = new de.jgsoftwares.guiserverpanel.frames.MainPanel();
        mpanel.setVisible(true);
        //mpanel.setSize(800,400);
        mpanel.pack();
        
        
        
        de.jgsoftwares.guiserverpanel.dao.dockerclient dockerclient = new de.jgsoftwares.guiserverpanel.dao.dockerclient();
        dockerclient.startdockerclient();

       
    }
    
   

}
