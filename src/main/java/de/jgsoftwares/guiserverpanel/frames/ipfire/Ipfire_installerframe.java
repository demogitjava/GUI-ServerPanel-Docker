package de.jgsoftwares.guiserverpanel.frames.ipfire;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author hoscho
 */
public class Ipfire_installerframe 
{
    public JFrame ipfireinstallerframe;
    
    // int 
    int panelindex;

   
    
    public Ipfire_installerframe()
    {
        ipfireinstallerframe = new JFrame("Ipfire installer");
        
        Container contentPane = ipfireinstallerframe.getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        
        JPanel centerpanel = new JPanel();
        
        panelindex = 0;
        de.jgsoftwares.guiserverpanel.frames.ipfire.WelcomePanel welcomepanel = new de.jgsoftwares.guiserverpanel.frames.ipfire.WelcomePanel();
        centerpanel.add(new JScrollPane(welcomepanel));
        contentPane.add(centerpanel, BorderLayout.CENTER);
        
        
        de.jgsoftwares.guiserverpanel.frames.ipfire.SouthPanel southpanel = new de.jgsoftwares.guiserverpanel.frames.ipfire.SouthPanel();
        
        contentPane.add(southpanel, BorderLayout.SOUTH);
        
        
        ipfireinstallerframe.setSize(400,300);
        ipfireinstallerframe.setVisible(true);
       
    }
    
    //public static void main(String[] args)
    //{
    //    Ipfire_installerframe ipfireinstaller = new Ipfire_installerframe();
    //}
    
    
    public void loadnextpanel(int panelindex)
    {
        //int panelindex = 0;
        
        switch(panelindex)
        {
            case 1: 
                
               break;
               
            default:
                break;
               
        }
        
    }
    
     public int getPanelindex() {
        return panelindex;
    }

    public void setPanelindex(int panelindex) {
        this.panelindex = panelindex;
    }
    
}
