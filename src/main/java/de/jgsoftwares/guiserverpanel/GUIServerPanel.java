package de.jgsoftwares.guiserverpanel;



import javax.swing.*;


public class GUIServerPanel implements i_GuiServerPanel
{
 
  
  public static de.jgsoftwares.guiserverpanel.frames.MainPanel mpanel;
    
  
   
  public static void main(String[] args)
    {

        GUIServerPanel.showFrame();
   
       // SpringApplication.run(GUIServerPanel.class, args);
    }

    public static void showFrame()
    {
        try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e)
        {
            System.out.print("LookAndFeel Error Nimbus !");
        }

        mpanel = new de.jgsoftwares.guiserverpanel.frames.MainPanel();
        mpanel.setVisible(true);
        mpanel.setSize(800,400);

        de.jgsoftwares.guiserverpanel.dao.dockerclient dockerclient = new de.jgsoftwares.guiserverpanel.dao.dockerclient();
        dockerclient.startdockerclient();


    }
    
   

}
