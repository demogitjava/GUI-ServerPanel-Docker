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


public class Databases extends javax.swing.JPanel {

    
    Process process;
    BufferedReader reader;
    PrintWriter writer;
    /**
     * Creates new form Databases
     */
    public Databases() {
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

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        jLabel1.setText("install Databases Docker Container");

        jButton1.setText("install DerbyDB - for Web");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("install H2 Database - for Lanserver TCP");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("install MySQL Server - with Database");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        // install docker container derbydb web
          // install landingpage
        try {
            process = Runtime.getRuntime().exec("docker run -it -p 0.0.0.0:1527:1527 --ip 172.17.0.2 --name oraclederbydb --runtime runc --blkio-weight 100 --cap-add=NET_ADMIN -e TZ=Europe/Berlin -v /etc/resolv.conf:/etc/resolv.conf -v /var/run/docker.sock:/var/run/docker.sock --network 172.17.0.0 --restart unless-stopped --platform=linux/amd64 --cpu-quota 2000 --cpu-period 2000 --cpu-shares 1024 --kernel-memory=6M --cpuset-cpus=\"1\" -e NTP_SERVER=\"2.rhel.pool.ntp.org\" jgsoftwares/oraclelinux_openjdk_derbydb:latest /bin/bash /root/startderbydb.sh");

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            writer = new PrintWriter(new PrintWriter(process.getOutputStream()));
            
            String line = "";
            jTextArea1.setText("");
            while ((line = reader.readLine()) != null) {
                jTextArea1.append(line + "docker container derbydb is running " + "\n");
               // System.out.println(line);
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        
        // install h2 docker container database
        try {
            process = Runtime.getRuntime().exec("docker run -it --runtime runc -e TZ=Europe/Berlin --add-host=docker:217.160.255.254 --runtime runc --blkio-weight 100 --platform=linux/amd64 --cpu-quota 2000 --cpu-period 2000 --cpu-shares 1024 --kernel-memory=6M --cpuset-cpus=\"1\" -p 0.0.0.0:8082:8082 -p 0.0.0.0:9092:9092 --network 172.17.0.0 --ip 172.17.0.101 --name oraclelinuxh2db -e NTP_SERVER=\"2.rhel.pool.ntp.org\" -v /var/run/docker.sock:/var/run/docker.sock -v /etc/resolv.conf:/etc/resolv.conf --restart unless-stopped --cap-add=NET_ADMIN jgsoftwares/oraclelinux_openjdk_h2db:1.4.199 /bin/bash /root/landingpageh2db.sh");

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            writer = new PrintWriter(new PrintWriter(process.getOutputStream()));
            
            String line = "";
            jTextArea1.setText("");
            while ((line = reader.readLine()) != null) {
                jTextArea1.append(line + "docker container h2 is running " + "\n");
               // System.out.println(line);
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
           // install mysql database
        try {
            process = Runtime.getRuntime().exec("docker container run --name mysqlcontainer -p 0.0.0.0:3306:3306 jgsoftwares/demomysqlserver-ce:latest");

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            writer = new PrintWriter(new PrintWriter(process.getOutputStream()));
            
            String line = "";
            jTextArea1.setText("");
            while ((line = reader.readLine()) != null) {
                jTextArea1.append(line + "docker container is installed" + "\n");
               // System.out.println(line);
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
