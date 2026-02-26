package de.jgsoftwares.guiserverpanel.config;

/**
 *
 * @author hoscho
 */
public class PublicDNSServerconfig 
{
    public String stdns1;
    public String stdns2;
    
    public PublicDNSServerconfig()
    {
        
    }
    
    public String publicdns(String stdnsserver, String stdns1, String stdns2)
    {
        // GCore
        // google
        // cloudflare

        //stdns1 = null;
        //stdns2 = null;
        
        switch(stdnsserver)
        {
            case "gcore":
            {
                System.out.print("gcore public dns selected" + "\n");
                stdns1 = "95.85.95.85";        
                stdns2 = "2.56.220.2";
                setStdns1(stdns1);
                setStdns2(stdns2);
                break;
            }
            case "google":
            {
                System.out.print("google public dns selected " + "\n");
                stdns1 = "8.8.8.8";
                stdns2 = "8.8.4.4";
                setStdns1(stdns1);
                setStdns2(stdns2);
                break;
            }    
            case "cloudflare":
            {
                System.out.print("cloudflasre public dns selected " + "\n");
                stdns1 = "1.1.1.1";
                stdns2 = "1.0.0.1";
                setStdns1(stdns1);
                setStdns2(stdns2);
                break;
            }
            
            default:
                break;
        }
        
        return stdnsserver + stdns1 + stdns2;  
    }
    
     public String getStdns1() {
        return stdns1;
    }

    public String getStdns2() {
        return stdns2;
    }
    
       public void setStdns1(String stdns1) {
        this.stdns1 = stdns1;
    }

    public void setStdns2(String stdns2) {
        this.stdns2 = stdns2;
    }
}
