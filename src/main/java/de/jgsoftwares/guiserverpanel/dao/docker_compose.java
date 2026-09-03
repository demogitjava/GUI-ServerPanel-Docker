package de.jgsoftwares.guiserverpanel.dao;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.ContainerSpec;
import com.github.dockerjava.api.model.NetworkAttachmentConfig;
import com.github.dockerjava.api.model.ServiceSpec;
import com.github.dockerjava.api.model.TaskSpec;
import com.github.dockerjava.core.DockerClientBuilder;
import java.util.Collections;

/**
 *
 * @author hoscho
 */
public class docker_compose 
{
    public docker_compose()
    {
        
    }
    
    public void composelandingpage()
    {
        
        // docker network create --scope=host --gateway 10.255.255.1 --subnet 10.255.255.1/32 --scope=global --attachable=true -d overlay netlandingpage
        // docker network connect netlandingpage openwrtlandingpagedebug
        
        /*
        DockerClient dockerClient = DockerClientBuilder.getInstance().build();

      
        ContainerSpec containerSpec = new ContainerSpec()
              
                .withImage("jgsoftwares/openwrt23.05landingpage:java25");

     
        TaskSpec taskSpec = (TaskSpec) new TaskSpec()
                .withContainerSpec(containerSpec)
                .withNetworks(Collections.singletonList(
                        new NetworkAttachmentConfig().withTarget("landingpage"))
                )
                .getNetworks();

    
        ServiceSpec serviceSpec = new ServiceSpec()
                .withName("service_landingpage") // Schema für Stacks: stackName_serviceName
                .withTaskTemplate(taskSpec);

       
        dockerClient.createServiceCmd(serviceSpec).exec();
        
               */   
    }
    
}
