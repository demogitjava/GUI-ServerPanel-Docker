
package de.jgsoftwares.guiserverpanel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import javax.swing.JFrame;

public interface i_GuiServerPanel
{

    JFrame frame = null;
    JPanel centerpanel = null;
    JPanel northpanel = null;
    JPanel southpanel = null;


    public static void main(String[] args)
    {

        GUIServerPanel.showFrame();

        SpringApplication.run(GUIServerPanel.class, args);


    }



}
