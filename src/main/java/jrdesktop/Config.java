package jrdesktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import jrdesktop.main;
import jrdesktop.utilities.InetAdrUtility;

/**
 * Config.java
 * @author benbac
 */

public class Config {

    public static String server_address = "127.0.0.1";
    public static int server_port = 6666;     
    public static boolean default_address = false;
    
    public static void loadConfiguration() {
       
    }
    
    public static void storeConfiguration () {
         
    }    
    
    public static void SetConfiguration(int Port) { 
        server_address = InetAdrUtility.getLocalAdr().getHostAddress(); 
        server_port = Port;                           
        
        storeConfiguration();       
    }    
}
