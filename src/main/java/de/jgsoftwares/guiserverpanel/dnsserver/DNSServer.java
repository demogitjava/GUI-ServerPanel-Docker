package de.jgsoftwares.guiserverpanel.dnsserver;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class DNSServer
{
    String reverseip4;
    
   
    public DNSServer()
    {
       
    }

  
    
   
    public void addARecord(String reverseip4, String stfqdn) 
    {
       
           
         
           
        
      
    }
   
    public void addCNAMERecord()
    {
        
    }
    
 
    public void addTxtRecord()
    {
        
    }
    
    public void addGoogleResolver()
    {
            
    }
    
    
    
    
    public void reverseip4(String stip4, String reverseip4)
    {
      

        // String#split(string regex) accepts regex as the argument
        String[] outp = stip4.split("\\.");

        String part1 = outp[0]; 
        String part2 = outp[1]; 
        String part3 = outp[2]; 
        String part4 = outp[3]; 
        
        
        reverseip4 = part4 + "." + part3 + "." + part2 + "." + part1;
        
        setReverseip4(reverseip4);
        
    }

    public String getReverseip4() {
        return reverseip4;
    }

    public void setReverseip4(String reverseip4) {
        this.reverseip4 = reverseip4;
    }
    
    
    
    
    
}
