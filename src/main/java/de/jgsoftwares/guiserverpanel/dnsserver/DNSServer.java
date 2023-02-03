package de.jgsoftwares.guiserverpanel.dnsserver;

import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import org.xbill.DNS.ExtendedResolver;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SimpleResolver;
import org.xbill.DNS.*;
import org.xbill.DNS.Record;
import org.xbill.DNS.Master;
import org.xbill.DNS.Name;

public class DNSServer
{
    String reverseip4;
    
    Master master;
    
    
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
            try {
            // configure the resolvers, starting with the default ones (based on the current network connection)
            Resolver defaultResolver = Lookup.getDefaultResolver();
            
            System.out.print("default resolver is " + defaultResolver + "\n");
            // use Google's public DNS services
            Resolver googleFirstResolver = new SimpleResolver("8.8.8.8");
            Resolver googleSecondResolver = new SimpleResolver("8.8.4.4");
             
            Lookup.setDefaultResolver(new ExtendedResolver(new Resolver[] { googleFirstResolver, googleSecondResolver, defaultResolver }));
            
            System.out.print("first google resovler" + googleFirstResolver.toString() + "\n");
            System.out.print("secord google resolver" + googleSecondResolver.toString() + "\n");
        } catch (UnknownHostException e) {
            System.out.print("Error " + e);
        }
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
