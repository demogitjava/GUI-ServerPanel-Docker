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
import java.io.IOException;
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        jLabel1.setText("install Databases Docker Container - Oracle Linux");

        jButton1.setText("install DerbyDB - host");
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

        jLabel2.setText("alternative run database with docker compose");

        jLabel3.setText("derbydb / h2db");

        jLabel4.setText("wget https://github.com/demogitjava/landingpage/blob/master/dockerderbydb/docker-compose.yml");

        jLabel5.setText("docker compose up");

        jLabel6.setText("the containers are standalone ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
        // install docker container 
        // derbydb web
       
        try {
            
            
            //process = Runtime.getRuntime().exec("docker run -it -p 0.0.0.0:1527:1527 --ip 172.17.0.2 --name oraclederbydb --runtime runc --blkio-weight 100 --cap-add=NET_ADMIN -e TZ=Europe/Berlin -v /etc/resolv.conf:/etc/resolv.conf -v /var/run/docker.sock:/var/run/docker.sock --network 172.17.0.0 --restart unless-stopped --platform=linux/amd64 --cpu-quota 2000 --cpu-period 2000 --cpu-shares 1024 --kernel-memory=6M --cpuset-cpus=\"1\" -e NTP_SERVER=\"2.rhel.pool.ntp.org\" jgsoftwares/oraclelinux_openjdk_derbydb:latest /bin/bash /root/startderbydb.sh");

            
                  // "/path-to/bash -c \"rm *.foo\""
      
            String stderbydb = new String(
                    "docker run -it -p 0.0.0.0:1527:1527 "
                            + "--add-host=" + ConfigPanel.styourdomainname + ":"  + ConfigPanel.stwanip + " "     
                            + "--name oraclederbydb "
                            + "--runtime " + stcomboruntime + " " 
                            + "-e NETWORK_NONE=" + stinterfacename + " "
                            + "--hostname " + ConfigPanel.styourdomainname + " " 
                            + "--cap-add=NET_ADMIN "
                            + "--cap-add SYS_ADMIN "
                            + "--net=host --net=none "
                            + "-v /etc/resolv.conf:/etc/resolv.conf "
                            + "--restart unless-stopped "
                            + "--platform=linux/amd64 "
                            + "--kernel-memory=6M " 
                            + "-e NTP_SERVER=\"2.rhel.pool.ntp.org\" "
                            + "jgsoftwares/oraclelinux_openjdk_derbydb:latest "
                            + "/bin/bash /root/startderbydb.sh");
                    
          
                jTextArea1.setText("" + "\n");
                jTextArea1.append("alternative start the container manually" + "\n");
                jTextArea1.append("#################################" + "\n");
                jTextArea1.append(stderbydb + "\n");
                jTextArea1.append("#################################" + "\n");
            

                 // getRuntime start LanServer container
                de.jgsoftwares.guiserverpanel.dao.dockerclient dockerclient = new de.jgsoftwares.guiserverpanel.dao.dockerclient();
                dockerclient.startderbydb(stderbydb);

             
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        //process = Runtime.getRuntime().exec("docker run -it --runtime runc -e TZ=Europe/Berlin --add-host=docker:217.160.255.254 --runtime runc --blkio-weight 100 --platform=linux/amd64 --cpu-quota 2000 --cpu-period 2000 --cpu-shares 1024 --kernel-memory=6M --cpuset-cpus=\"1\" -p 0.0.0.0:8082:8082 -p 0.0.0.0:9092:9092 --network 172.17.0.0 --ip 172.17.0.101 --name oraclelinuxh2db -e NTP_SERVER=\"2.rhel.pool.ntp.org\" -v /var/run/docker.sock:/var/run/docker.sock -v /etc/resolv.conf:/etc/resolv.conf --restart unless-stopped --cap-add=NET_ADMIN jgsoftwares/oraclelinux_openjdk_h2db:1.4.199 /bin/bash /root/landingpageh2db.sh");
        
        String sth2db = new String("docker run -it "
                                + "-e TZ=" + stcombotimezone + " " 
                                + "--add-host=" + ConfigPanel.styourdomainname + ":"  + ConfigPanel.stwanip + " "  
                                + "--runtime " + stcomboruntime + " "
                                + "-e NETWORK_NONE=" + stinterfacename + " "
                                + "--hostname " + ConfigPanel.styourdomainname + " " 
                                + "--kernel-memory=6M "
                                + "--name oraclelinuxh2db "
                                + "--net=host --net=none "
                                + "-e LANG=" + stlocales + " " 
                                + "-e NTP_SERVER=\"2.rhel.pool.ntp.org\" "
                                + "-v /var/run/docker.sock:/var/run/docker.sock "
                                + "-v /etc/resolv.conf:/etc/resolv.conf "
                                + "--restart unless-stopped "
                                + "--cap-add=NET_ADMIN --cap-add SYS_ADMIN jgsoftwares/oraclelinux_openjdk_h2db:1.4.199 /bin/bash /root/landingpageh2db.sh");
        
        jTextArea1.setText("");
        jTextArea1.append("docker container is started oraclelinuxh2db " + "\n");
        jTextArea1.append("#################################" + "\n");
        jTextArea1.append(sth2db + "\n");
        jTextArea1.append("#################################" + "\n");
        // getRuntime start h2db  container xterm window
        de.jgsoftwares.guiserverpanel.dao.dockerclient dockerclient = new de.jgsoftwares.guiserverpanel.dao.dockerclient();
        dockerclient.starth2db();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
           // install mysql database
        try {
          
            String stmysql = new String("docker container run "
                + "--runtime " + stcomboruntime + " "
                + "-e TZ=" + stcombotimezone + " "
                + "--name mysqlcontainer "
                + "--runtime " + stcomboruntime + " "
                + "-e LANG=" + stlocales + " " 
                + "-e NETWORK_NONE=" + stinterfacename + " "
                + "--hostname " + ConfigPanel.styourdomainname + " " 
                + "--platform=linux/amd64" + " "
                + "--net=host --net=none" + " "
                + "--cap-add=NET_ADMIN " + " "
                + "--cap-add SYS_ADMIN " + " "
                + "jgsoftwares/demomysqlserver-ce:latest");

            
                jTextArea1.setText("");
                jTextArea1.append("docker container is started -  mysqlcontainer " + "\n");
                jTextArea1.append("#################################" + "\n");
                jTextArea1.append(stmysql + "\n");
                jTextArea1.append("#################################" + "\n");
                jTextArea1.append("copy docker run command with" + "\n");
                jTextArea1.append("Strg + c" + "\n");
                jTextArea1.append("insert string with schift + inster on the xterm window" + "\n");


            // getRuntime start mysql  container xterm window
            de.jgsoftwares.guiserverpanel.dao.dockerclient dockerclient = new de.jgsoftwares.guiserverpanel.dao.dockerclient();
            dockerclient.startmysqldb();
                 
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
