/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package de.jgsoftwares.guiserverpanel.frames;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.*;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class MainPanel extends javax.swing.JFrame {

    
    public static DefaultMutableTreeNode rootNode;

    public static DefaultMutableTreeNode dockerimages;
    public static DefaultMutableTreeNode dockercontainers;


    Process process;
    BufferedReader reader;
    
    HashMap dockercontainerhashmap;

    /**
     * Creates new form MainPanel
     */
    public MainPanel() {

        dockercontainerhashmap = new HashMap();
        initComponents();
        
        
        try
        {
              String stremoteip = (String) Inet4Address.getLocalHost().getHostAddress();
            jTextField1.setText(stremoteip);
            jTextArea1.setText("");
            jTextArea1.setText("Remote ip4 " + stremoteip + "\n");
        } catch(Exception e)
        {
            System.out.print("Fehler " +e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTree2 = new javax.swing.JTree();
        jPanel3 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel4.setLayout(new java.awt.BorderLayout());

        rootNode = new DefaultMutableTreeNode("Docker Client");
        dockerimages = new DefaultMutableTreeNode("Images");
        dockercontainers = new DefaultMutableTreeNode("Containers");

        jTree2 = new JTree(rootNode);
        rootNode.add(dockerimages);
        rootNode.add(dockercontainers);
        jScrollPane2.setViewportView(jTree2);

        jPanel4.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel4);

        jSplitPane1.setLeftComponent(jPanel2);

        jPanel3.setLayout(null);

        jButton2.setText("DE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            };
        });
        jPanel3.add(jButton2);
        jButton2.setBounds(0, 110, 72, 25);

        jButton3.setText("FR");
        jPanel3.add(jButton3);
        jButton3.setBounds(0, 140, 72, 25);

        jButton4.setText("GB");
        jPanel3.add(jButton4);
        jButton4.setBounds(0, 170, 72, 25);

        jButton5.setText("ES");
        jPanel3.add(jButton5);
        jButton5.setBounds(0, 200, 72, 25);

        jButton6.setText("IT");
        jPanel3.add(jButton6);
        jButton6.setBounds(0, 230, 72, 25);

        jLabel1.setText("start Docker Containers");
        jPanel3.add(jLabel1);
        jLabel1.setBounds(6, 17, 154, 19);

        jButton7.setText("TR");
        jPanel3.add(jButton7);
        jButton7.setBounds(0, 260, 72, 25);

        jButton8.setText("--> restart");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton8);
        jButton8.setBounds(80, 110, 99, 25);

        jButton9.setText("--> restart");
        jPanel3.add(jButton9);
        jButton9.setBounds(80, 140, 99, 25);

        jButton10.setText("--> restart");
        jPanel3.add(jButton10);
        jButton10.setBounds(80, 170, 99, 25);

        jButton11.setText("--> restart");
        jPanel3.add(jButton11);
        jButton11.setBounds(80, 200, 99, 25);

        jButton12.setText("--> restart");
        jPanel3.add(jButton12);
        jButton12.setBounds(80, 230, 99, 25);

        jButton13.setText("--> restart");
        jPanel3.add(jButton13);
        jButton13.setBounds(80, 260, 99, 25);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jPanel3.add(jScrollPane1);
        jScrollPane1.setBounds(190, 110, 390, 170);

        jButton1.setText("install nginx proxy");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);
        jButton1.setBounds(0, 40, 180, 25);

        jButton14.setText("restart nginx");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton14);
        jButton14.setBounds(0, 70, 180, 25);
        jPanel3.add(jTextField1);
        jTextField1.setBounds(280, 70, 300, 30);

        jLabel2.setText("your ip here:");
        jPanel3.add(jLabel2);
        jLabel2.setBounds(190, 69, 150, 30);

        jSplitPane1.setRightComponent(jPanel3);

        jPanel1.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        // create container de
        try {
            String myip = jTextField1.getText();

            process = Runtime.getRuntime().exec("/bin/docker compose -f /root/IdeaProjects/GUI-ServerPanel-Docker/de_docker-compose.yml up -d");

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = null;
            jTextArea1.setText("");
            while ((line = reader.readLine()) != null)
            {

                System.out.print(" " + line + "\n");
                jTextArea1.append(line + "\n");
            }


            reader.close();




        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:

        // restart container de
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:


        Runnable runnable = () -> {
        System.out.println("Inside : " + Thread.currentThread().getName());
        
        // install nginx
        try {
            String myip = jTextField1.getText();
            process = Runtime.getRuntime().exec("docker run --name reverseproxynginx -p" + myip + ":80:80 -d nginx");

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = null;
            jTextArea1.setText("");
            while ((line = reader.readLine()) != null)
            {

                System.out.print(" " + line + "\n");
                dockercontainerhashmap.put(line, "reverseproxynginx");
                jTextArea1.append("nginx running: " + "\n" + line + "\n");
            }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        Thread th = new Thread(runnable);
        th.start();
        
      
        
        
      

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        
        // restart nginx
            /*
            docker run -d --name baeldung baeldung
        */
            
        Runnable runnable = () -> {
        System.out.println("Inside : " + Thread.currentThread().getName());
          
       
        // restart nginx proxy
        try {
           
            // get docker container id from hashmap 
            String dockercontainerid = (String) dockercontainerhashmap.keySet().toArray()[0];
                 
            
            String dockerun = "docker restart " + dockercontainerid;
            process = Runtime.getRuntime().exec(dockerun);
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line = null;
            jTextArea1.setText("");
            while ((line = reader.readLine()) != null)
            {

               
                
                System.out.print(" " + line + "\n");
                jTextArea1.append("nginx restart: " + "\n" + line + "\n");
            }


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };

        Thread th = new Thread(runnable);
        th.start();
        
        
    }//GEN-LAST:event_jButton14ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTree jTree2;
    // End of variables declaration//GEN-END:variables
}
