package de.jgsoftwares.guiserverpanel.config;

import java.util.ArrayList;

/**
 *
 * @author hoscho
 */
public class containerconfig implements Icontainerconfig
{
    
    public containerconfig()
    {
        
    }
    
    
    /**
    *  
    * edit package url to http 217.160.255.254
    * /etc/opkg/distfeeds.conf
    * @param arraylistdistfeed
    * @return 
    */
    @Override
    public ArrayList<String> openwrt2305disfeedtohttp(ArrayList<String> arraylistdistfeed)
    {
        
        /*
            edit from 
            src/gz openwrt_core https://downloads.openwrt.org/releases/23.05.5/targets/x86/64/packages
            src/gz openwrt_base https://downloads.openwrt.org/releases/23.05.5/packages/x86_64/base
            src/gz openwrt_luci https://downloads.openwrt.org/releases/23.05.5/packages/x86_64/luci
            src/gz openwrt_packages https://downloads.openwrt.org/releases/23.05.5/packages/x86_64/packages
            src/gz openwrt_routing https://downloads.openwrt.org/releases/23.05.5/packages/x86_64/routing
            src/gz openwrt_telephony https://downloads.openwrt.org/releases/23.05.5/packages/x86_64/telephony
        
        
            to 
            src/gz openwrt_core http://217.160.255.254:8000/openwrt/23.05.packages/packages/
            src/gz openwrt_base http://217.160.255.254:8000/openwrt/23.05.packages/base/
            src/gz openwrt_luci http://217.160.255.254:8000/openwrt/23.05.packages/luci/
            src/gz openwrt_packages http://217.160.255.254:8000/openwrt/23.05.packages/packages/
            src/gz openwrt_routing http://217.160.255.254:8000/openwrt/23.05.packages/routing/
            src/gz openwrt_telephony http://217.160.255.254:8000/openwrt/23.05.packages/telephony/
        
        */
        
        
        //System.out.print("edit file distfeed to jgsoftwares http fileserver " + "\n");
        
        // clear file
        // :> /pfad/zur/datei
                
        arraylistdistfeed = new ArrayList<>();
        
        
        String openwt_core = """
            src/gz openwrt_core http://217.160.255.254:8000/openwrt/23.05.packages/packages""";
        
        String openwrt_base = """
            src/gz openwrt_base http://217.160.255.254:8000/openwrt/23.05.packages/base""";
        
        String openwrt_luci = """
            src/gz openwrt_luci http://217.160.255.254:8000/openwrt/23.05.packages/luci""";
        
        String openwrt_packages = """
            src/gz openwrt_packages http://217.160.255.254:8000/openwrt/23.05.packages/packages""";
        
        String openwrt_routing = """
            src/gz openwrt_routing http://217.160.255.254:8000/openwrt/23.05.packages/routing""";
        
        String openwrt_telephony = """
                src/gz openwrt_telephony http://217.160.255.254:8000/openwrt/23.05.packages/telephony""";
        
        //String openwt_core = new String("src/gz openwrt_core " + "http://217.160.255.254:8000/openwrt/23.05.packages/packages");
        //String openwrt_base = new String("src/gz openwrt_base " + "http://217.160.255.254:8000/openwrt/23.05.packages/base");
        //String openwrt_luci = new String("src/gz openwrt_luci" + "http://217.160.255.254:8000/openwrt/23.05.packages/luci");
        //String openwrt_packages = new String("src/gz openwrt_packages " + "http://217.160.255.254:8000/openwrt/23.05.packages/packages");
        //String openwrt_routing = new String("src/gz openwrt_routing " + "http://217.160.255.254:8000/openwrt/23.05.packages/routing");
        //String openwrt_telephony = new String("src/gz openwrt_telephony " + "http://217.160.255.254:8000/openwrt/23.05.packages/telephony");
        
        arraylistdistfeed.add(openwt_core);
        arraylistdistfeed.add(openwrt_base);
        arraylistdistfeed.add(openwrt_luci);
        arraylistdistfeed.add(openwrt_packages);
        arraylistdistfeed.add(openwrt_routing);
        arraylistdistfeed.add(openwrt_telephony);
        
        return arraylistdistfeed;    
    }
    
    
    
    
    
    
}
