package de.jgsoftwares.guiserverpanel.frames.ipfire;

import java.awt.BorderLayout;
import java.awt.Container;
import javax.swing.JFrame;

/**
 *
 * @author hoscho
 */
public class Ipfire_installerframe 
{
    public JFrame ipfireinstallerframe;
    
    
    public Ipfire_installerframe()
    {
        ipfireinstallerframe = new JFrame("Ipfire installer");
        
        Container contentPane = ipfireinstallerframe.getContentPane();
        contentPane.setLayout(new BorderLayout());
        
        
        
        ipfireinstallerframe.setVisible(true);
    }
    
    
    
}
