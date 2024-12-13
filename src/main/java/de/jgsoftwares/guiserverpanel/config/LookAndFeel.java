/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jgsoftwares.guiserverpanel.config;

import de.jgsoftwares.guiserverpanel.GUIServerPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author hoscho
 */
// openjdk 21

public class LookAndFeel
{
    
    
    
    
    public void setNimbusLookAndFeel()
    {
         try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(GUIServerPanel.mpanel);
        } catch (Exception e)
        {
            System.out.print("LookAndFeel Error Nimbus !");
        }
    }
    
    
    public void setMetalLookAndFeel()
    {
         try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            SwingUtilities.updateComponentTreeUI(GUIServerPanel.mpanel);
        } catch (Exception e)
        {
            System.out.print("LookAndFeel Error Nimbus !");
        }
    }

    
}
