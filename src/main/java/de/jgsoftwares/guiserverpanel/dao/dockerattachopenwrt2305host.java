package de.jgsoftwares.guiserverpanel.dao;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.command.AttachContainerResultCallback;
import com.github.dockerjava.netty.NettyDockerCmdExecFactory;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 *
 * @author hoscho
 */
public class dockerattachopenwrt2305host implements Runnable
{
    String containerId;
    DockerClient dockerClientnetty;
    
    String stcontainername;

  
    
    public dockerattachopenwrt2305host(String stdmzcontainerattach, String stcontainername)
    {
        String stconnectdockerhost = "tcp://" + stdmzcontainerattach + ":2375";
  
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(stconnectdockerhost)
                .build();

      
        DockerCmdExecFactory nettyFactory = new NettyDockerCmdExecFactory();
                //.withConnectTimeout(5000) // Verbindungstimeout in ms
                //.withReadTimeout(30000);  // Read-Timeout in ms

       
        dockerClientnetty = DockerClientBuilder.getInstance(config)
                .withDockerCmdExecFactory(nettyFactory)
                .build();
        
        containerId = stcontainername;
        
       
        setStcontainername(stcontainername);
        //String containerId = "openwrt2305host";
        this.run();
        
    
    }
    
    @Override
    public void run()
    {
        containerId = getStcontainername();

        try {
            dockerClientnetty.attachContainerCmd(containerId)
                    .withStdIn(System.in)
                    .withStdOut(true)
                    .withStdErr(true)
                    .withFollowStream(true)
                    .exec(new AttachContainerResultCallback() 
                    {
                        @Override
                        public void onNext(Frame item) {
                            // Read output from STDOUT/STDERR here
                            System.out.print(new String(item.getPayload()));
                        }
                    })      
                    .awaitCompletion();
                   
        } catch (InterruptedException ex) {
            System.getLogger(dockerclient.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
       
        
    }
    
    private String getStcontainername() {
        return stcontainername;
    }

    private void setStcontainername(String stcontainername) {
        this.stcontainername = stcontainername;
    }
    
    

   
    
}
