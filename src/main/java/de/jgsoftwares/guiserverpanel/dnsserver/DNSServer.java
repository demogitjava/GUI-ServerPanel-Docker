package de.jgsoftwares.guiserverpanel.dnsserver;

import java.net.UnknownHostException;
import org.xbill.DNS.ExtendedResolver;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Resolver;
import org.xbill.DNS.SimpleResolver;


public class DNSServer
{
    
    
    
    
    public DNSServer()
    {
       
    }

  
    
   
    public void addARecord()
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
    
    
    
}
