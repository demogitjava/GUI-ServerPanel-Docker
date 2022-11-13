package de.jgsoftwares.guiserverpanel;


import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

import javax.swing.JFrame;

import java.awt.*;

@SpringBootApplication
public class GUIServerPanel implements i_GuiServerPanel
{



    public static void showFrame()
    {
        try
        {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e)
        {
            System.out.print("LookAndFeel Error Nimbus !");
        }
        de.jgsoftwares.guiserverpanel.frames.MainPanel mpanel = new de.jgsoftwares.guiserverpanel.frames.MainPanel();
        mpanel.setVisible(true);
        mpanel.setSize(640,400);
    }


}
