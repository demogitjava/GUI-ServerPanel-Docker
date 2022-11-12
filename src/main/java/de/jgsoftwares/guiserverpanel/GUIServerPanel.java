package de.jgsoftwares.guiserverpanel;

import com.jgoodies.looks.plastic.PlasticLookAndFeel;
import com.jgoodies.looks.plastic.theme.DesertBluer;
import com.jgoodies.looks.plastic.theme.ExperienceBlue;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.border.Border;
import java.awt.*;

@SpringBootApplication
public class GUIServerPanel implements i_GuiServerPanel
{



    public static void showFrame(JFrame frame, JPanel centerpanel, JPanel northpanel, JPanel southpanel)
    {


            frame = new JFrame();
            Container contentPane = frame.getContentPane();


            centerpanel = new JPanel();
            contentPane.add(centerpanel, BorderLayout.CENTER);

            northpanel = new JPanel();
            contentPane.add(northpanel, BorderLayout.NORTH);



            southpanel = new JPanel();
            contentPane.add(southpanel, BorderLayout.SOUTH);


            frame.setTitle("GUI Server Panel");
            frame.setSize(640,400);
            frame.setVisible(true);
    }


    public GUIServerPanel()
    {
        try {

            //PlasticLookAndFeel.setPlasticTheme(new DesertBluer());
            //UIManager.setLookAndFeel(new PlasticLookAndFeel());
          //  UIManager.setLookAndFeel(new PlasticLookAndFeel());

        } catch (Exception ex) {
        }
    }
}
