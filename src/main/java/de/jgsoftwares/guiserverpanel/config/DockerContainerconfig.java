package de.jgsoftwares.guiserverpanel.config;

import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import static de.jgsoftwares.guiserverpanel.dao.dockerclient.dockerClient;
import de.jgsoftwares.guiserverpanel.frames.ConfigPanel;

/**
 *
 * @author hoscho
 */
public class DockerContainerconfig 
{
   
    // docker container config
    
    public DockerContainerconfig()
    {
        
    }

    
    // dockercontainer - openwrt2305host
    public void openwrt2305host(String containerid)
    {
        
          try
          {
            /*
                
                clear files in the docker container 
                openwrt2305host
             */
             // cat /dev/null > /etc/hosts
             String clearhostsfile = "/etc/hosts";
             ExecCreateCmdResponse execlearhost = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "cat /dev/null > " + clearhostsfile).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
           
             // cat /dev/null > /etc/sysctl.conf
             String clearsysctl = "/etc/sysctl.conf";
             ExecCreateCmdResponse execlearsysctl = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "cat /dev/null > " + clearsysctl).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearsysctl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
                 
             // resolv.conf
             // cat /dev/null > /etc/resolv.conf
             String clearresolvconf = "/etc/resolv.conf";
             ExecCreateCmdResponse execlearresolvconf = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "cat /dev/null > " + clearresolvconf).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearresolvconf.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
                 
              // TZ
             // cat /dev/null > /etc/TZ
             String cleartimezone = "/etc/TZ";
             ExecCreateCmdResponse execleartimezone = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "cat /dev/null > " + cleartimezone).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execleartimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
               
             
             
             /*
             
                write config to docker container 
                openwrt2305host
             
             */
             
             // add string with wanip and hostname
             String sthost = (String) ConfigPanel.stwanip + "  " + ConfigPanel.styourdomainname;
             ExecCreateCmdResponse execaddstringtohost = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + sthost + " >> /etc/hosts").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtohost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             
            // /etc/sysctl.conf
             // add string to /etc/sysctl.conf
             // net.ipv6.conf.all.disable_ipv6=0
             // net.ipv4.conf.all.src_valid_mark=0
             // net.ipv4.ip_forward=0   
             String noforward = "net.ipv4.ip_forward=0";
             ExecCreateCmdResponse execaddstringtosyscontl = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + noforward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtosyscontl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             String nosrcvalid = "net.ipv4.conf.all.src_valid_mark=0";
             ExecCreateCmdResponse execaddstringnosourcevalid = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + nosrcvalid + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnosourcevalid.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             String noipv6forward = "net.ipv6.conf.all.disable_ipv6=0";
             ExecCreateCmdResponse execaddstringnoipv6forward = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + noipv6forward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnoipv6forward.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         

             // get Timezone String 
             String sttimezonecountry = ConfigPanel.stcombotimezone.toString();
             
             //load timezoneconfig
             // write timezone string to /etc/TZ
             de.jgsoftwares.guiserverpanel.config.Timezoneconfig tmconfig = new de.jgsoftwares.guiserverpanel.config.Timezoneconfig();     
             String stgettimezone = tmconfig.timezoneopenwrt(sttimezonecountry, cleartimezone);
             ExecCreateCmdResponse execaddstringtotimezone = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + stgettimezone + " >> /etc/TZ").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtotimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
          
             
             
            // resolv.conf
             /*
             
                    nameserver 95.85.95.85
                    nameserver 2.56.220.2
                    search demogitjava.ddns.net
                    interface orange0
                    DNSSEC=yes
                    DNSOverTLS=yes

             */
             de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig publicdnsserverconfig = new de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig();
             String stdnsserver = ConfigPanel.stpubdnsserver;
             String stdns1 = null;
             String stdns2 = null;
             
             
             // returns string dnspulicserver ipdns1 ipdns2
             publicdnsserverconfig.publicdns(stdnsserver, stdns1, stdns2);
             
             // nameserver dnsip1
             ExecCreateCmdResponse execaddstringpublicdnsip1 = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + "nameserver " + stdns1 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip1.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             //nameserver dnsip2
             ExecCreateCmdResponse execaddstringpublicdnsip2 = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + "nameserver " + stdns2 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip2.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // add search with domainname
             ExecCreateCmdResponse execaddstringsearchdomain = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + "search " + ConfigPanel.styourdomainname + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringsearchdomain.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // interface name
             ExecCreateCmdResponse execaddstringinterface = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + "interface orange0 " + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringinterface.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // dnsec
             ExecCreateCmdResponse execaddstringdnssearch = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + "DNSSEC=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnssearch.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // dnsovertls
             ExecCreateCmdResponse execaddstringdnsovertls = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + "DNSOverTLS=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnsovertls.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
          
             
          } catch(Exception e)
          {
              System.out.print("Fehler " + e);
          }
    }
    
    
    public void openwrtlandingpage(String containerid)
    {
        
          try
          {
            /*
                
                clear files in the docker container 
                openwrt2305host
             */
             // cat /dev/null > /etc/hosts
             String clearhostsfile = "/etc/hosts";
             ExecCreateCmdResponse execlearhost = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "cat /dev/null > " + clearhostsfile).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearhost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
           
             // cat /dev/null > /etc/sysctl.conf
             String clearsysctl = "/etc/sysctl.conf";
             ExecCreateCmdResponse execlearsysctl = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "cat /dev/null > " + clearsysctl).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearsysctl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
                 
             // resolv.conf
             // cat /dev/null > /etc/resolv.conf
             String clearresolvconf = "/etc/resolv.conf";
             ExecCreateCmdResponse execlearresolvconf = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "cat /dev/null > " + clearresolvconf).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execlearresolvconf.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
                 
              // TZ
             // cat /dev/null > /etc/TZ
             String cleartimezone = "/etc/TZ";
             ExecCreateCmdResponse execleartimezone = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "cat /dev/null > " + cleartimezone).withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execleartimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
               
             
             
             /*
             
                write config to docker container 
                landingpage
             
             */
             
             // etc/hosts
             // add string with wanip and hostname
             String sthost = (String) ConfigPanel.stwanip + "  " + ConfigPanel.styourdomainname;
             ExecCreateCmdResponse execaddstringtohost = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + sthost + " >> /etc/hosts").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtohost.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             
             // /etc/sysctl.conf
             // add string to /etc/sysctl.conf
             // net.ipv6.conf.all.disable_ipv6=0
             // net.ipv4.conf.all.src_valid_mark=0
             // net.ipv4.ip_forward=0   
             String noforward = "net.ipv4.ip_forward=0";
             ExecCreateCmdResponse execaddstringtosyscontl = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + noforward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtosyscontl.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             String nosrcvalid = "net.ipv4.conf.all.src_valid_mark=0";
             ExecCreateCmdResponse execaddstringnosourcevalid = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + nosrcvalid + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnosourcevalid.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             String noipv6forward = "net.ipv6.conf.all.disable_ipv6=0";
             ExecCreateCmdResponse execaddstringnoipv6forward = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + noipv6forward + " >> /etc/sysctl.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringnoipv6forward.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
         
             
             // get Timezone String 
             String sttimezonecountry = ConfigPanel.stcombotimezone.toString();
             
             //load timezoneconfig
             // write timezone string to /etc/TZ
             de.jgsoftwares.guiserverpanel.config.Timezoneconfig tmconfig = new de.jgsoftwares.guiserverpanel.config.Timezoneconfig();     
             String openwrttimezone = null;
             String stgettimezone = tmconfig.timezoneopenwrt(sttimezonecountry, openwrttimezone);
             ExecCreateCmdResponse execaddstringtotimezone = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + openwrttimezone + " >> /etc/TZ").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringtotimezone.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
          
             
             
             // resolv.conf
             /*
             
                    nameserver 95.85.95.85
                    nameserver 2.56.220.2
                    search demogitjava.ddns.net
                    interface orange0
                    DNSSEC=yes
                    DNSOverTLS=yes

             */
             de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig publicdnsserverconfig = new de.jgsoftwares.guiserverpanel.config.PublicDNSServerconfig();
             String stdnsserver = ConfigPanel.stpubdnsserver;
             String stdns1 = null;
             String stdns2 = null;
             
             
             // returns string dnspulicserver ipdns1 ipdns2
             publicdnsserverconfig.publicdns(stdnsserver, stdns1, stdns2);
             
             // nameserver dnsip1
             ExecCreateCmdResponse execaddstringpublicdnsip1 = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + "nameserver " + stdns1 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip1.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             //nameserver dnsip2
             ExecCreateCmdResponse execaddstringpublicdnsip2 = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + "nameserver " + stdns2 + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringpublicdnsip2.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // add search with domainname
             ExecCreateCmdResponse execaddstringsearchdomain = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + "search " + ConfigPanel.styourdomainname + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringsearchdomain.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // interface name
             ExecCreateCmdResponse execaddstringinterface = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + "interface orange0 " + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringinterface.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // dnsec
             ExecCreateCmdResponse execaddstringdnssearch = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + "DNSSEC=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnssearch.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
             // dnsovertls
             ExecCreateCmdResponse execaddstringdnsovertls = dockerClient.execCreateCmd(containerid).withCmd("sh", "-c", "echo " + "DNSOverTLS=yes" + " >> /etc/resolv.conf").withAttachStdout(true).withAttachStderr(true).exec();
             dockerClient.execStartCmd(execaddstringdnsovertls.getId()).exec(new ExecStartResultCallback(System.out, System.err)).awaitCompletion();
          
          } catch(Exception e)
          {
              System.out.print("Fehler " + e);
          }
    }
    
}
