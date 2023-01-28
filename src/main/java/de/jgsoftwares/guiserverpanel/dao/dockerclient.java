package de.jgsoftwares.guiserverpanel.dao;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.tree.DefaultMutableTreeNode;

import de.jgsoftwares.guiserverpanel.frames.MainPanel;

public class dockerclient
{

    Process process;
    BufferedReader reader;


    public dockerclient()
    {

    }

    public void listContainers()
    {


    }

    public void startdockerclient()
    {


        // list images
        try {
            process = Runtime.getRuntime().exec("docker images");

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                MainPanel.dockerimages.add(new DefaultMutableTreeNode(line));
               // System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // list containers
        try {
            process = Runtime.getRuntime().exec("docker container ls");

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = reader.readLine()) != null) {
                MainPanel.dockercontainers.add(new DefaultMutableTreeNode(line));
                // System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void pullImage(String imageName)
    {


    }
}
