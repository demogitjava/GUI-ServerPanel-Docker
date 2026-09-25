package de.jgsoftwares.guiserverpanel;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;

public class GUIServerPanel implements i_GuiServerPanel
{ 
  public static de.jgsoftwares.guiserverpanel.frames.MainPanel mpanel;

  //public static ScriptEngineManager manager;
  //public static ScriptEngine engine;
  
  /**
   *
  */
  public GUIServerPanel()
  {
        NtpClient ntpclient;
        try 
        {
            ntpclient = new NtpClient();
            ntpclient.getSystemTime();
            System.out.print(ntpclient.getSystemTime());
        } catch (Exception ex) {
            System.out.print("Error by method getSystemTime");
        }
        
     
  }
    
  
   
  public static void main(String[] args)
  {      
       
            //com.nilo.plaf.nimrod.NimRODLF
          
      
        try
        {
            UIManager.setLookAndFeel("net.sf.nimrod.NimRODLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e)
        {
            System.out.print("LookAndFeel Error NomRoD !");
        }     
        
        /*
        manager = new ScriptEngineManager();
        // Nashorn-Engine
        engine = manager.getEngineByName("nashorn");
      
        try {
            engine.eval("print('nashorn javascript egnine is loaded');"); 
        } catch (ScriptException e) {
            System.out.print("Error " + e);
        }
        */ 
        
        GUIServerPanel.showFrame();
        
       // SpringApplication.run(GUIServerPanel.class, args);
   }
  
  
  
     
   

    public static void showFrame()
    {

        mpanel = new de.jgsoftwares.guiserverpanel.frames.MainPanel();
        mpanel.setVisible(true);
        //mpanel.setSize(800,400);
        mpanel.pack();
        try
        {
            de.jgsoftwares.guiserverpanel.dao.dockerclient dockerclient = new de.jgsoftwares.guiserverpanel.dao.dockerclient();  
            dockerclient.startdockerclient();

        } catch(Exception e)
        {
            System.out.print("Fehler " + e);
        }
       
    }
    
   

}
