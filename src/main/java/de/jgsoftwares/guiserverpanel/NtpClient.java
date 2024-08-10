/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.jgsoftwares.guiserverpanel;



import java.net.InetAddress;
import java.util.Date;
import org.apache.commons.net.ntp.NTPUDPClient; 
import org.apache.commons.net.ntp.TimeInfo;
import org.apache.commons.net.ntp.TimeStamp;



/**
 *
 * @author hoscho
 */
public class NtpClient {
  private static final String SERVER_NAME = "2.rhel.pool.ntp.org";

  private volatile TimeInfo timeInfo;
  private volatile Long offset;

  private TimeStamp systemNtpTime;
  
  public NtpClient() throws Exception {

    NTPUDPClient client = new NTPUDPClient();
    // We want to timeout if a response takes longer than 10 seconds
    client.setDefaultTimeout(10_000);

    InetAddress inetAddress = InetAddress.getByName(SERVER_NAME);
    TimeInfo timeInfo = client.getTime(inetAddress);
    timeInfo.computeDetails();
    if (timeInfo.getOffset() != null) {
        this.timeInfo = timeInfo;
        this.offset = timeInfo.getOffset();
    }

    // This system NTP time
    systemNtpTime = TimeStamp.getCurrentTime();
    System.out.println("System time:\t" + systemNtpTime + "  " + systemNtpTime.toDateString());

   
  }
  
  public TimeStamp getSystemTime()
  {
      return systemNtpTime;
  }

  public boolean isComputed()
  {
    return timeInfo != null && offset != null;
  }
}
