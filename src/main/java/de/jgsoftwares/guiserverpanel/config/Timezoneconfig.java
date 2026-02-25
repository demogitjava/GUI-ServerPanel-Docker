package de.jgsoftwares.guiserverpanel.config;

/**
 *
 * @author hoscho
 */
public class Timezoneconfig 
{
    public Timezoneconfig()
    {
        
    }
    
    // return german timezone
    public String timezoneopenwrt(String sttimezonecountry, String openwrttimezone)
    {
        
        // set default timezones for container config
        /*  
        Europe/Berlin
        Europe/Paris
        Europe/London
        Europe/Madrid
        Europe/Rom
        Europe/Istanbul
        */
        // 
       switch (sttimezonecountry)
       {
            case "Europe/Berlin":
                //code
                openwrttimezone = "CET-1CEST,M3.5.0,M10.5.0/3";
                break;
            case "Europe/Paris":
                //code
                openwrttimezone = "CET-1CEST,M3.5.0,M10.5.0/3";
                break;
            case "Europe/London":
                //code
                openwrttimezone = "GMT0BST,M3.5.0/1,M10.5.0";
                break; 
            case "Europe/Madrid":
                //code
                openwrttimezone = "CET-1CEST,M3.5.0,M10.5.0/3";
                break;   
            case "Europe/Rom":
                //code
                openwrttimezone = "CET-1CEST,M3.5.0,M10.5.0/3";
                break; 
            case "Europe/Istanbul":
                //code
                openwrttimezone = "EET-2EEST,M3.5.0/3,M10.5.0/4";
                break;
                
            default:
                //code
                System.out.print("no country");
                break;
        }
       // return sttimezonecountry;
        return openwrttimezone;
     }
}
