/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jgsoftwares.guiserverpanel.frames;

import static de.jgsoftwares.guiserverpanel.frames.ConfigPanel.stcomboruntime;
import static de.jgsoftwares.guiserverpanel.frames.ConfigPanel.stinterfacename;
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
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("openwrt dmz - google");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane2.setViewportView(jTextArea2);

        jButton5.setText("openwrt open dns");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel1.setText("acces to running container typ:");

        jLabel2.setText("docker exec -it openwrtbackfireopendns /bin/ash");

        jLabel3.setText("docker exec -it openwrtbackfiregoogle /bin/ash");

        jLabel4.setText("edit password -   passwd");

        jLabel5.setText("edit date  -  date -s hh:mm[:ss]");

        jLabel6.setText("start the web interface with -  /etc/init.d/uhttpd restart");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jButton1)
                .addGap(4, 4, 4)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(3, 3, 3)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
     
        // install opoenwrt 
        try {
           String strunopenwrtdmz = new String("docker run -it -p "
                   + "0.0.0.0:123:123 "
                   + "-p 0.0.0.0:53:53 "
                   + "--name openwrtbackfiregoogle "
                   + "--runtime " + stcomboruntime + " "
                   + "-e NETWORK_IF=" + stinterfacename + " " 
                   + "-v /etc/resolv.conf:/etc/resolv.conf "
                   + "-e TZ=Europe/Berlin "
                   + "--blkio-weight 100 "
                   + "--cpu-shares 1024 "
                   + "--cpu-quota 1000 "
                   + "--cpu-period 1000 "
                   + "--net=host "
                   + "--add-host=" + ConfigPanel.styourdomainname + ":"  + ConfigPanel.stwanip + " " 
                   + "--platform=linux/amd64 "
                   + "--kernel-memory=6M "
                   + "--restart unless-stopped "
                   + "--privileged jgsoftwares/openwrt-x86-backfire:google /bin/ash");
           
           
           
                jTextArea2.setText("");
                jTextArea2.append("start the docker container openwrt cloud flare dmz " + "\n");
                jTextArea2.append("#################################" + "\n");
                jTextArea2.append(strunopenwrtdmz + "\n");
                jTextArea2.append("#################################" + "\n");
                jTextArea2.append("copy docker run command with" + "\n");
                jTextArea2.append("Strg + c" + "\n");
                jTextArea2.append("insert string with schift + inster on the xterm window" + "\n");

                 // getRuntime start Landingpage container
                de.jgsoftwares.guiserverpanel.dao.dockerclient dockerclient = new de.jgsoftwares.guiserverpanel.dao.dockerclient();
                dockerclient.startopenwrtcloudflaredmz();
           
        } catch (Exception e) {
            System.out.print("Error " + e);
        }
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        try {
           String strunopenwrtopendns = new String("docker run -it "
                   + "-p 0.0.0.0:123:123 "
                   + "-p 0.0.0.0:53:53 "
                   + "-p 0.0.0.0:22:22 "
                   + "--name openwrtbackfireopendns "
                   + "--runtime " + stcomboruntime + " "
                   + "-e NETWORK_IF=" + stinterfacename + " " 
                   + "-v /etc/resolv.conf:/etc/resolv.conf "
                   + "-e TZ=Europe/Berlin "
                   + "--blkio-weight 100 "
                   + "--cpu-shares 1024 "
                   + "--cpu-quota 1000 "
                   + "--cpu-period 1000 "
                   + "--net=host "
                   + "--add-host=" + ConfigPanel.styourdomainname + ":"  + ConfigPanel.stwanip + " " 
                   + "--platform=linux/amd64 "
                   + "--kernel-memory=6M "
                   + "--restart unless-stopped "
                   + "--privileged jgsoftwares/openwrt-x86-backfire:opendns /bin/ash");
           
           
           
               jTextArea2.setText("");
                jTextArea2.append("start the docker container openwrt cloud flare dmz " + "\n");
                jTextArea2.append("#################################" + "\n");
                jTextArea2.append(strunopenwrtopendns + "\n");
                jTextArea2.append("#################################" + "\n");
                jTextArea2.append("copy docker run command with" + "\n");
                jTextArea2.append("Strg + c" + "\n");
                jTextArea2.append("insert string with schift + inster on the xterm window" + "\n");

                 // getRuntime start Landingpage container
                de.jgsoftwares.guiserverpanel.dao.dockerclient dockerclient = new de.jgsoftwares.guiserverpanel.dao.dockerclient();
                dockerclient.startopenwrtopendns();
           
        } catch (Exception e) {
            System.out.print("Error " + e);
        }
        
    }//GEN-LAST:event_jButton5ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
