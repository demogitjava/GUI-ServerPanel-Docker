
package de.jgsoftwares.guiserverpanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import javax.swing.JFrame;

public interface i_GuiServerPanel
{

    public static void main(String[] args)
    {

        try
        {
            JFrame frame = null;
            GUIServerPanel.showFrame(frame);
        } catch(Exception e)
        {
            System.out.print("Fehler" + e);
        }

        SpringApplication.run(GUIServerPanel.class, args);


    }



}
