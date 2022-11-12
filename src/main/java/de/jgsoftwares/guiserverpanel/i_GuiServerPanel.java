package de.jgsoftwares.guiserverpanel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public interface i_GuiServerPanel
{
    public static void main(String[] args)
    {
        SpringApplication.run(GUIServerPanel.class, args);
    }
}
