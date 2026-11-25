package de.jgsoftwares.guiserverpanel.dao;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.command.AttachContainerResultCallback;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.netty.NettyDockerCmdExecFactory;

/**
 *
 * @author hoscho
 */
public class dockerattachopenwrt2305host //implements Runnable
{
    String containerId;
    DockerClient dockerClientnetty;
    
    String stcontainername;

    /**
     *
    
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
     */
    public dockerattachopenwrt2305host(String stdmzcontainerattach, String stcontainername)
    {
        String stconnectdockerhost = "tcp://" + stdmzcontainerattach + ":2375";
  
        DockerClientConfig config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(stconnectdockerhost)
                .build();

      
        DockerCmdExecFactory nettyFactory = new NettyDockerCmdExecFactory().withConnectTimeout(null);
                //.withConnectTimeout(5000) // Verbindungstimeout in ms
                //.withReadTimeout(30000);  // Read-Timeout in ms

       
        dockerClientnetty = DockerClientBuilder.getInstance(config)
                .withDockerCmdExecFactory(nettyFactory)
                .build();
        
       
        ExecCreateCmdResponse execlearhost = dockerClientnetty.execCreateCmd("openwrt2305host").withCmd("sh", "-c", "ip link set dev lo mtu 1500 && ifconfig lo down && ip addr del 127.0.0.1/8 dev lo").withAttachStdout(true).withAttachStderr(true).exec();
        dockerClientnetty.execStartCmd(execlearhost.getId()).exec(new ExecStartResultCallback(System.out, System.err));
        //System.out.print("flush file /etc/host");
        

        containerId = stcontainername;
        
       
        setStcontainername(stcontainername);
        //String containerId = "openwrt2305host";
        //this.run();
        
         Thread thread = new Thread("attach container host") 
         {
             
            @Override
            public void run(){
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
         };


         thread.start();
         System.out.println(thread.getName());
    
    }
    
 
    
    private String getStcontainername() {
        return stcontainername;
    }

    private void setStcontainername(String stcontainername) {
        this.stcontainername = stcontainername;
    }
    
    
    
    // set config for docker_gwbridge
    // run commands to disable network interfaces
    // the container is only over vpn available
    public void commandforattachcontainer()
    {
         
      
    }

   
    
}
