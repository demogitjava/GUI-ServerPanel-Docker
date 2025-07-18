/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jgsoftwares.guiserverpanel.frames;


import static de.jgsoftwares.guiserverpanel.frames.ConfigPanel.stcomboruntime;
import static de.jgsoftwares.guiserverpanel.frames.ConfigPanel.stcombotimezone;
import static de.jgsoftwares.guiserverpanel.frames.ConfigPanel.stinterfacename;
import static de.jgsoftwares.guiserverpanel.frames.ConfigPanel.stlocales;
import java.io.BufferedReader;
import java.io.PrintWriter;

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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        jButton1.setText("start container - lanserver tcp");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton2.setText("restart container  - lanserver tcp");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setText("Container name:");

        jLabel2.setText("oraclelinuxlanservertcp ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(4, 4, 4)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     
       
                  
        
            
            // "/path-to/bash -c \"rm *.foo\""
            String struncontainer = "docker run -it -p 0.0.0.0:8443:8443 " +
                    "--add-host=" + ConfigPanel.styourdomainname + ":"  + ConfigPanel.stwanip + " "  +     
                    "--runtime " + stcomboruntime + " " +
                    "-e NETWORK_NONE=" + stinterfacename + " " +
                    "-e NTP_SERVER=\"2.rhel.pool.ntp.org\" " +
                    "-e TZ=" + stcombotimezone + " " +
                    "--platform=linux/amd64 " +
                    "--hostname " + ConfigPanel.styourdomainname + " " +
                    "--net=host --net=none " +
                    "--name openwrtlanservertcp " +
                    "--restart unless-stopped " +
                    "--cap-add=NET_ADMIN " +
                    "--cap-add=SYS_ADMIN " +
                    "jgsoftwares/openwrt23.05lanserver:11 bin/ash /root/LanServer.sh";
           
                     
            System.out.println("docker string " + struncontainer + "\n" + "\n");
         
            
          
          
            de.jgsoftwares.guiserverpanel.dao.dockerclient dockerclient = new de.jgsoftwares.guiserverpanel.dao.dockerclient();
            dockerclient.startlanservercontiner(struncontainer);
            
             

         
            
            
            String line = "";
            jTextArea1.setText("");
            jTextArea1.setText("DemolanServer" + "\n" + 
                    "if container is not running paste the string to shell and restart the GUI Server Panel " + "\n" +
                    "the started container is then visible on the Panel" + "\n" +
                    "the ntp server is set by default  \"-e NTP_SERVER=\\\"2.rhel.pool.ntp.org\\\" \" " + "\n" +
                    "################################" + "\n" + "\n" +
                      struncontainer + "\n" + "\n" +
                    "################################" + "\n" + "\n");

       
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        
        // restart lanserver container
        
            String strestartcontainer = "docker container restart oraclelinuxlanservertcp";
         
            // getRuntime start LanServer container
            de.jgsoftwares.guiserverpanel.dao.dockerclient dockerclient = new de.jgsoftwares.guiserverpanel.dao.dockerclient();
            dockerclient.restartlanserver(strestartcontainer);
            jTextArea1.setText("");
            jTextArea1.setText("DemolanServer" + "\n" + 
                    "restart container with command " + "\n" + "run command with Shift + Insert" + "\n" +
                     "################################" + "\n" + "\n" +
                    strestartcontainer + "\n" + "\n" +
                    "################################" + "\n" + "\n");
                               
             
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
