package de.jgsoftwares.guiserverpanel.dao;

import de.jgsoftwares.guiserverpanel.frames.ConfigPanel;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 *
 * @author hoscho
 */
public class IpfireConfig 
{
    public IpfireConfig()
    {
        
    }
    
    // Firewall config rules
    /*
        5,REJECT,FORWARDFW,ON,std_net_src,ALL,std_net_tgt,RED,ON,UDP,,9092,ON,,,TGT_PORT,9092,dropbittorent,,,,,,,,,,00:00,00:00,,AUTO,,dnat,,,,,second
        6,ACCEPT,FORWARDFW,ON,src_addr,217.160.255.254/32,std_net_tgt,RED,,TCP,,80,ON,,,cust_srv,HTTP,HTTP,,,,,,,,,,00:00,00:00,ON,RED,,snat,,,,,second
        1,ACCEPT,FORWARDFW,ON,src_addr,217.160.255.254/32,std_net_tgt,RED,ON,TCP,,1527,ON,,,TGT_PORT,1527,DerbyDB,,,,,,,,,,00:00,00:00,ON,RED,,snat,,,,,second
        4,ACCEPT,FORWARDFW,ON,std_net_src,ALL,std_net_tgt,ORANGE,,TCP,,51820,ON,,,cust_srv,SSH,ssh,,,,,,,,,,00:00,00:00,ON,ORANGE,,snat,,,,,second
        3,ACCEPT,FORWARDFW,ON,std_net_src,ALL,std_net_tgt,ORANGE,,TCP,,51820,ON,,,cust_srv,SSH,ssh,,,,,,,,,,00:00,00:00,ON,ORANGE,,snat,,,,,second
        2,ACCEPT,FORWARDFW,ON,std_net_src,ALL,std_net_tgt,ORANGE,,TCP,,51820,ON,,,cust_srv,SSH,ssh,,,,,,,,,,00:00,00:00,ON,ORANGE,,snat,,,,,second
    */
    public JList firewallconfig()
    {
        
         DefaultListModel listModel = new DefaultListModel();        
         JList<de.jgsoftwares.guiserverpanel.model.ipfire> firewallconfig = new JList<de.jgsoftwares.guiserverpanel.model.ipfire>(listModel);
         
         de.jgsoftwares.guiserverpanel.model.ipfire ipfireconfig = new de.jgsoftwares.guiserverpanel.model.ipfire();
         
         ipfireconfig.setId(1);
         ipfireconfig.setFirewallrule("5,REJECT,FORWARDFW,ON,std_net_src,ALL,std_net_tgt,RED,ON,UDP,,9092,ON,,,TGT_PORT,9092,dropbittorent,,,,,,,,,,00:00,00:00,,AUTO,,dnat,,,,,second");
         listModel.addElement(firewallconfig );
         
         ipfireconfig.setId(2);
         ipfireconfig.setFirewallrule("6,ACCEPT,FORWARDFW,ON,src_addr,"+ ConfigPanel.stwanip +"/32,std_net_tgt,RED,,TCP,,80,ON,,,cust_srv,HTTP,HTTP,,,,,,,,,,00:00,00:00,ON,RED,,snat,,,,,second");
         listModel.addElement(firewallconfig );    
         
         ipfireconfig.setId(3);
         ipfireconfig.setFirewallrule("1,ACCEPT,FORWARDFW,ON,src_addr," + ConfigPanel.stwanip + "/32,std_net_tgt,RED,ON,TCP,,1527,ON,,,TGT_PORT,1527,DerbyDB,,,,,,,,,,00:00,00:00,ON,RED,,snat,,,,,second");
         listModel.addElement(firewallconfig );
         
         ipfireconfig.setId(4);
         ipfireconfig.setFirewallrule("4,ACCEPT,FORWARDFW,ON,std_net_src,ALL,std_net_tgt,ORANGE,,UPD,,51820,ON,,,cust_srv,SSH,ssh,,,,,,,,,,00:00,00:00,ON,ORANGE,,snat,,,,,second");
         listModel.addElement(firewallconfig );
         
         ipfireconfig.setId(5);
         ipfireconfig.setFirewallrule("3,ACCEPT,FORWARDFW,ON,std_net_src,ALL,std_net_tgt,ORANGE,,UPD,,51820,ON,,,cust_srv,SSH,ssh,,,,,,,,,,00:00,00:00,ON,ORANGE,,snat,,,,,second");
         listModel.addElement(firewallconfig );
         
         ipfireconfig.setId(6);
         ipfireconfig.setFirewallrule("2,ACCEPT,FORWARDFW,ON,std_net_src,ALL,std_net_tgt,ORANGE,,UPD,,51820,ON,,,cust_srv,SSH,ssh,,,,,,,,,,00:00,00:00,ON,ORANGE,,snat,,,,,second");
         listModel.addElement(firewallconfig );
         
         
         return (JList) firewallconfig;
    
    }
    
    
    /* 
    Firewall rules -> vi /var/ipfire/firewall/input
    8,ACCEPT,INPUTFW,ON,std_net_src,ALL,ipfire,ORANGE,,TCP,,51820,ON,,,cust_srv,SSH,ssh,,,,,,,,,,00:00,00:00,ON,ORANGE,,snat,,,,,second,
    4,ACCEPT,INPUTFW,ON,src_addr,217.160.255.254/32,ipfire,RED1,ON,TCP,,1527,ON,,,TGT_PORT,1527,DerbyDB,,,,,,,,,,00:00,00:00,ON,RED,,snat,,,,,second
    5,ACCEPT,INPUTFW,ON,src_addr,217.160.255.254/32,ipfire,RED1,ON,TCP,,8443,ON,,,TGT_PORT,8443,Lanserver,,,,,,,,,,00:00,00:00,ON,RED,,snat,,,,,second
    6,ACCEPT,INPUTFW,ON,src_addr,217.160.255.254/32,ipfire,RED1,ON,TCP,,8000,ON,,,TGT_PORT,8000,HttpFileserver,,,,,,,,,,00:00,00:00,ON,RED,,snat,,,,,second
    3,ACCEPT,INPUTFW,ON,src_addr,217.160.255.254/32,ipfire,RED1,,TCP,,80,ON,,,cust_srv,HTTP,HTTP,,,,,,,,,,00:00,00:00,ON,RED,,snat,,,,,second
    7,ACCEPT,INPUTFW,ON,src_addr,217.160.255.254/32,ipfire,GREEN,ON,UDP,,51820,ON,,,TGT_PORT,51820,Wireguard,,,,,,,,,,00:00,00:00,ON,RED,,snat,,,,,second
    1,ACCEPT,INPUTFW,ON,src_addr,192.168.10.56/32,ipfire,ORANGE,ON,TCP,,22,ON,,,TGT_PORT,22,ssh,,,,,,,,,,00:00,00:00,ON,ORANGE,,snat,,,,,second
    2,ACCEPT,INPUTFW,ON,src_addr,217.160.255.254/32,ipfire,RED1,,TCP,,80,ON,,,cust_srv,HTTP,HTTP,,,,,,,,,,00:00,00:00,ON,Default IP,80,dnat,,,,,second

    */
    
    // Incoming firewall access
    public JList incomingfirewallconfig()
    {
         DefaultListModel defaultmodelincomingfirewall = new DefaultListModel();        
         JList<de.jgsoftwares.guiserverpanel.model.ipfire> incomingfirewallconfig = new JList<de.jgsoftwares.guiserverpanel.model.ipfire>(defaultmodelincomingfirewall);
         
         de.jgsoftwares.guiserverpanel.model.ipfire incommingipfireconfigmodel = new de.jgsoftwares.guiserverpanel.model.ipfire();
        
         
        incommingipfireconfigmodel.setId(1);
        incommingipfireconfigmodel.setFirewallrule("8,ACCEPT,INPUTFW,ON,std_net_src,ALL,ipfire,ORANGE,,TCP,,51820,ON,,,cust_srv,SSH,ssh,,,,,,,,,,00:00,00:00,ON,ORANGE,,snat,,,,,second,");
        defaultmodelincomingfirewall.addElement(incomingfirewallconfig);
        
        incommingipfireconfigmodel.setId(2);
        incommingipfireconfigmodel.setFirewallrule("4,ACCEPT,INPUTFW,ON,src_addr," + ConfigPanel.stwanip + "/32,ipfire,RED1,ON,TCP,,1527,ON,,,TGT_PORT,1527,DerbyDB,,,,,,,,,,00:00,00:00,ON,RED,,snat,,,,,second");
        defaultmodelincomingfirewall.addElement(incomingfirewallconfig);
        
       incommingipfireconfigmodel.setId(3);
        incommingipfireconfigmodel.setFirewallrule("5,ACCEPT,INPUTFW,ON,src_addr," + ConfigPanel.stwanip + "/32,ipfire,RED1,ON,TCP,,8443,ON,,,TGT_PORT,8443,Lanserver,,,,,,,,,,00:00,00:00,ON,RED,,snat,,,,,second");
        defaultmodelincomingfirewall.addElement(incomingfirewallconfig);
        
        incommingipfireconfigmodel.setId(4);
        incommingipfireconfigmodel.setFirewallrule("6,ACCEPT,INPUTFW,ON,src_addr," + ConfigPanel.stwanip + "/32,ipfire,RED1,ON,TCP,,8000,ON,,,TGT_PORT,8000,HttpFileserver,,,,,,,,,,00:00,00:00,ON,RED,,snat,,,,,second");
        defaultmodelincomingfirewall.addElement(incomingfirewallconfig);
        
        incommingipfireconfigmodel.setId(5);
        incommingipfireconfigmodel.setFirewallrule("3,ACCEPT,INPUTFW,ON,src_addr," + ConfigPanel.stwanip + "/32,ipfire,RED1,,TCP,,80,ON,,,cust_srv,HTTP,HTTP,,,,,,,,,,00:00,00:00,ON,RED,,snat,,,,,second");
        defaultmodelincomingfirewall.addElement(incomingfirewallconfig);
        
        incommingipfireconfigmodel.setId(6);
        incommingipfireconfigmodel.setFirewallrule("7,ACCEPT,INPUTFW,ON,src_addr," + ConfigPanel.stwanip + "/32,ipfire,GREEN,ON,UDP,,51820,ON,,,TGT_PORT,51820,Wireguard,,,,,,,,,,00:00,00:00,ON,RED,,snat,,,,,second");
        defaultmodelincomingfirewall.addElement(incomingfirewallconfig);
        
        incommingipfireconfigmodel.setId(7);
        incommingipfireconfigmodel.setFirewallrule("1,ACCEPT,INPUTFW,ON,src_addr,192.168.10.56/32,ipfire,ORANGE,ON,TCP,,22,ON,,,TGT_PORT,22,ssh,,,,,,,,,,00:00,00:00,ON,ORANGE,,snat,,,,,second");
        defaultmodelincomingfirewall.addElement(incomingfirewallconfig);
        
        incommingipfireconfigmodel.setId(8);
        incommingipfireconfigmodel.setFirewallrule("2,ACCEPT,INPUTFW,ON,src_addr," + ConfigPanel.stwanip + "/32,ipfire,RED1,,TCP,,80,ON,,,cust_srv,HTTP,HTTP,,,,,,,,,,00:00,00:00,ON,Default IP,80,dnat,,,,,second");
        defaultmodelincomingfirewall.addElement(incomingfirewallconfig);
        
        return (JList) incomingfirewallconfig;
        
    }
  
    // Outgoing firewall access
    public JList outgoingfirewallconfig()
    {
       
        return null;
    }
}
