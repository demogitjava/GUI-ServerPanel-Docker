/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jgsoftwares.guiserverpanel.frames;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class LanServerTCP extends javax.swing.JPanel {

    Runtime rt;
    Process process;
    BufferedReader reader;
    PrintWriter writer;
    
    /**
     * Creates new form LanServer TCP
     */
    public LanServerTCP() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jButton1.setText("install de - lanserver tcp");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton2.setText("restart container - de - lanserver tcp");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("set Systemtime to lanserver tcp - de - Container");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     
        try {
                  
        
            
            // "/path-to/bash -c \"rm *.foo\""
            String struncontainer = "docker run -it -p 0.0.0.0:8443:8443 " +
                    "--add-host=" + ConfigPanel.styourdomainname + ":"  + ConfigPanel.stwanip + " " +      
                    "--runtime runc " +
                    "--blkio-weight 100 " + 
                    "-e NTP_SERVER=\"2.rhel.pool.ntp.org\" " +
                    "--platform=linux/amd64 " +
                    "--hostname " + ConfigPanel.styourdomainname + " " +
                    "--network 172.17.0.0 " +
                    "--ip 172.17.0.104 " +
                    "--name oraclelinuxlanservertcp " +
                    "-v /var/run/docker.sock:/var/run/docker.sock " +
                    "-v /etc/resolv.conf:/etc/resolv.conf " +
                    "--restart unless-stopped " +
                    "--cap-add=NET_ADMIN " +
                    "--cap-add SYS_ADMIN " +
                    "jgsoftwares/oraclelinux_openjdk_lanservertcp:latest bin/bash /root/LanServer.sh";
           
                        ///bin/bash /root/LanServer.sh
            System.out.println("docker string " + struncontainer + "\n" + "\n");
         
           //process = Runtime.getRuntime().exec(struncontainer);
           
           
           /*
           Runtime.getRuntime().exec( 
           new String[]{ "bash", "-c", "mysqldump -uxxx -pxxx -hxxx.xxx.xxx mydatabase | mysql -uxxx -pxxx -hxxx.xxx.xxx.xxx targetdatabase" }
           );
           */
             rt = Runtime.getRuntime();
             process = rt.exec("gnome-terminal" + struncontainer);
           

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            writer = new PrintWriter(new PrintWriter(process.getOutputStream()));
            
            String line = "";
            jTextArea1.setText("");
            jTextArea1.setText("DemolanServer" + "\n" + 
                    "if container is not running paste the string to shell and restart the GUI Server Panel " + "\n" +
                    "the started container is then visible on the Panel" + "\n" +
                    "the ntp server is set by default  \"-e NTP_SERVER=\\\"2.rhel.pool.ntp.org\\\" \" " + "\n" +
                    "################################" + "\n" + "\n" +
                      struncontainer + "\n" + "\n" +
                    "################################" + "\n");
            
           
             jTextArea1.append("" + process.info());
            while ((line = reader.readLine()) != null) {
                jTextArea1.append(line + "docker container is running " + "\n");
               // System.out.println(line);
            }
            
        } catch (IOException e) {
            System.out.print("Fehler " + e);
           
         e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
            // install landingpage
        try {
            process = Runtime.getRuntime().exec("docker container oraclelinuxlandingpage restart");

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            writer = new PrintWriter(new PrintWriter(process.getOutputStream()));
            
            String line = "";
            jTextArea1.setText("");
            while ((line = reader.readLine()) != null) {
                jTextArea1.append(line + "docker container oraclelinux landingapge restarted " + "\n" + "\n");
                jTextArea1.append("start manually with command " + "\n" + "\n");
                jTextArea1.append("docker container oraclelinuxlandingpage restart");
               // System.out.println(line);
            }
            
        } catch (IOException e) {
          System.out.print("Fehler " + e);
        }
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        
              // add systemtime
           
        de.jgsoftwares.guiserverpanel.NtpClient ntpclient = null;   
        try {
            process = Runtime.getRuntime().exec("docker exec -it openwrtbackfire");

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            writer = new PrintWriter(new PrintWriter(process.getOutputStream()));
            
            try {
                ntpclient = new de.jgsoftwares.guiserverpanel.NtpClient();
            } catch (Exception ex) {
                Logger.getLogger(OpenWrt.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String stsystemtime = (String) ntpclient.getSystemTime().toString();
            
            String line = "";
            jTextArea1.setText("");
            while ((line = reader.readLine()) != null) {
                jTextArea1.append(line + "new systemtime is set - de -  " + "\n");
               // System.out.println(line);
            }
            writer.write(stsystemtime + "\r");
           
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
