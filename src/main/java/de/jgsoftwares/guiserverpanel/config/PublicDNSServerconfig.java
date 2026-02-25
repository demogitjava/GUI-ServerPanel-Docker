package de.jgsoftwares.guiserverpanel.config;

/**
 *
 * @author hoscho
 */
public class PublicDNSServerconfig 
{
    public PublicDNSServerconfig()
    {
        
    }
    
    public String publicdns(String stdnsserver, String stdns1, String stdns2)
    {
        // GCore
        // google
        // cloudflare

        stdns1 = null;
        stdns2 = null;
        
        switch(stdnsserver)
        {
            case "gcore":
            {
                System.out.print("gcore public dns selected" + "\n");
                stdns1 = "95.85.95.85";
                stdns2 = "2.56.220.2";
                break;
            }
            case "google":
            {
                System.out.print("google public dns selected " + "\n");
                stdns1 = "8.8.8.8";
                stdns2 = "8.8.4.4";
                break;
            }    
            case "cloudflare":
            {
                System.out.print("cloudflasre public dns selected " + "\n");
                stdns1 = "1.1.1.1";
                stdns2 = "1.0.0.1";
                break;
            }
            
            default:
                break;
        }
        
        return stdnsserver + stdns1 + stdns2;  
    }
}
