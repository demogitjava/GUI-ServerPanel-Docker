package de.jgsoftwares.guiserverpanel;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

import javax.swing.JFrame;
import java.awt.*;

@SpringBootApplication
public class GUIServerPanel implements i_GuiServerPanel
{



    public static void showFrame(JFrame frame)
    {
            frame = new JFrame();
            frame.setTitle("GUI Server Panel");
            frame.setSize(640,400);
            frame.setVisible(true);
    }


    public GUIServerPanel() {
    }
}
