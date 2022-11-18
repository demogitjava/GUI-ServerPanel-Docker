package de.jgsoftwares.guiserverpanel;



import com.github.dockerjava.api.DockerClientDelegate;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import javax.swing.*;

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

        de.jgsoftwares.guiserverpanel.dao.dockerclient dockerclient = new de.jgsoftwares.guiserverpanel.dao.dockerclient();
        dockerclient.startdockerclient();


    }


}
