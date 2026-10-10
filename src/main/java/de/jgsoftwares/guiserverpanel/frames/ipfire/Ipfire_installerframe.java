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
    public static int panelindex;
    
    // int index for southpanel 1 - 9
    public int southpanelindex;
    
    
    Container contentPane;
    JPanel centerpanel;
    
    de.jgsoftwares.guiserverpanel.frames.ipfire.SouthPanel southpanel;
    
    
    public Ipfire_installerframe()
    {
        ipfireinstallerframe = new JFrame("Ipfire installer");
        
        contentPane = ipfireinstallerframe.getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        
        centerpanel = new JPanel();
        
        
        
        
        
        
        southpanel = new de.jgsoftwares.guiserverpanel.frames.ipfire.SouthPanel();
        // set jLabel1 to 0
        panelindex = 0;
        loadnextpanel(panelindex);
        contentPane.add(southpanel, BorderLayout.SOUTH);
        
        
        ipfireinstallerframe.setSize(400,300);
        ipfireinstallerframe.setVisible(true);
        ipfireinstallerframe.pack();
       
    }
    
    public static void main(String[] args)
    {
        Ipfire_installerframe ipfireinstaller = new Ipfire_installerframe();
    }
    
    
    public void loadnextpanel(int panelindex)
    {
        //int panelindex = 0;
        
        switch(panelindex)
        {
            // welcome panel
            case 0:
                de.jgsoftwares.guiserverpanel.frames.ipfire.WelcomePanel welcomepanel = new de.jgsoftwares.guiserverpanel.frames.ipfire.WelcomePanel();
                centerpanel.add(new JScrollPane(welcomepanel));
                contentPane.add(centerpanel, BorderLayout.CENTER);
                
                
                // panelindex to 1
                Integer pageindex = Integer.valueOf(1);
                panelindex = pageindex + 1;
                // set int value number to southpanel 
                southpanel = new de.jgsoftwares.guiserverpanel.frames.ipfire.SouthPanel();
                southpanel.pagevalueindex(pageindex);
                
                break;
                
            // load firewall config
            case 1: 
                de.jgsoftwares.guiserverpanel.frames.ipfire.firewallconfig firewallconfigpanel = new de.jgsoftwares.guiserverpanel.frames.ipfire.firewallconfig();
                // remove all components
                centerpanel.removeAll();
                
                // load firewall config panel
                centerpanel.add(new JScrollPane(firewallconfigpanel));
                contentPane.add(centerpanel, BorderLayout.CENTER);
                
                
                // panelindex is set to 2
                // set value to southpanel jLabel1
                Integer intconfigfirewall = new Integer(1);
                southpanelindex = panelindex + intconfigfirewall;
                
                // set int value 
                panelindex = southpanelindex;
                
                // set int value number to southpanel 
                southpanel = new de.jgsoftwares.guiserverpanel.frames.ipfire.SouthPanel();
                southpanel.pagevalueindex(panelindex);
                
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
    
    public int getIndexfromsouthpanel(int panelindex)
    {
        loadnextpanel(panelindex);
        return 0;
        
    }
    
}
