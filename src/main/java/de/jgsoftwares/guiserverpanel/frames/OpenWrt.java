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
import static java.lang.ProcessBuilder.Redirect.to;
import java.util.logging.Level;
import java.util.logging.Logger;


public class OpenWrt extends javax.swing.JPanel {

    Process process;
    BufferedReader reader;
    PrintWriter writer;
    /**
     * Creates new form OpenWrt
     */
    public OpenWrt() {
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPasswordField1 = new javax.swing.JPasswordField();
        jPasswordField2 = new javax.swing.JPasswordField();
        jButton3 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton4 = new javax.swing.JButton();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("install");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("restart openwrt - docker container");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jPasswordField1.setText("jPasswordField1");

        jPasswordField2.setText("jPasswordField2");

        jButton3.setText("edit container - password");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton4.setText("set german systemtime to container ");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPasswordField1)
                    .addComponent(jPasswordField2)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPasswordField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
     
        // install opoenwrt 
        try {
            process = Runtime.getRuntime().exec("docker run -it --name openwrtbackfire --runtime runc -e TZ=Europe/Berlin -e NTP_SERVER=\"2.rhel.pool.ntp.org\" -v /etc/resolv.conf:/etc/resolv.conf --blkio-weight 100 --cpu-shares 1024 --cpu-quota 1000 --cpu-period 1000 --net=host --add-host=demogitjava.ddns.net:217.160.255.254 --platform=linux/amd64 --kernel-memory=6M --restart unless-stopped --privileged jgsoftwares/openwrt-x86-backfire:latest /bin/ash");

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            jTextArea2.setText("");
            while ((line = reader.readLine()) != null) {
                jTextArea2.append(line + "\n");
               // System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        
        // restart openwrt container
        try {
            process = Runtime.getRuntime().exec("docker exec -it openwrtbackfire");

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            writer = new PrintWriter(new PrintWriter(process.getOutputStream()));
            
            String line = "";
            jTextArea2.setText("");
            while ((line = reader.readLine()) != null) {
                jTextArea2.append(line + "new password is set " + "\n");
               // System.out.println(line);
            }
            writer.write(jPasswordField1 + "\r");
            writer.write(jPasswordField1 + "\r");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        String stpassword1 = null;
        String stpassword2 = null;
        
        
        try
        {
          stpassword1 = jPasswordField1.getText();
          stpassword2 = jPasswordField2.getText();
          
          
          if(stpassword1 == stpassword2)
          {
             // password ist gleich
              
               try {
            process = Runtime.getRuntime().exec("docker container restart openwrtbackfire");

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            jTextArea2.setText("");
            while ((line = reader.readLine()) != null) {
                jTextArea2.append(line + "container backfire is restarted" + "\n");
               // System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
              
        }
        } catch (Exception e)
        {
            System.out.print("Fehler " +e);
        }
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
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
            jTextArea2.setText("");
            while ((line = reader.readLine()) != null) {
                jTextArea2.append(line + "new systemtime is set - de -" + "\n");
               // System.out.println(line);
            }
            writer.write(stsystemtime + "\r");
           
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    public static javax.swing.JPasswordField jPasswordField1;
    public static javax.swing.JPasswordField jPasswordField2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
