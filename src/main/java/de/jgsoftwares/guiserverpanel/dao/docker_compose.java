package de.jgsoftwares.guiserverpanel.dao;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateServiceResponse;
import com.github.dockerjava.api.model.ContainerSpec;
import com.github.dockerjava.api.model.EndpointSpec;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Mount;
import com.github.dockerjava.api.model.MountType;
import com.github.dockerjava.api.model.NetworkAttachmentConfig;
import com.github.dockerjava.api.model.PortConfig;
import com.github.dockerjava.api.model.PortConfigProtocol;
import com.github.dockerjava.api.model.ServiceSpec;
import com.github.dockerjava.api.model.TaskSpec;
import com.github.dockerjava.core.DockerClientBuilder;
import static de.jgsoftwares.guiserverpanel.dao.dockerclient.dockerClient;
import de.jgsoftwares.guiserverpanel.frames.ConfigPanel;
import java.util.Collections;
import java.util.List;
import javax.swing.JList;

import com.github.dockerjava.api.model.ContainerSpec;
import com.github.dockerjava.api.model.NetworkSettings;
import com.github.dockerjava.api.model.ServiceModeConfig;
import com.github.dockerjava.api.model.ServiceReplicatedModeOptions;

import com.github.dockerjava.api.model.TaskSpec;
import com.github.dockerjava.api.model.ServiceSpec;

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


    }
    
}
