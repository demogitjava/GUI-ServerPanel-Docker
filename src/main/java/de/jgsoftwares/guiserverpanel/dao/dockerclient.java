package de.jgsoftwares.guiserverpanel.dao;


import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class dockerclient
{

    DefaultDockerClientConfig clientConfig;

    public dockerclient()
    {

    }


    public void startdockerclient(String username, String password)
    {
       clientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://127.0.0.1:2375")
                .withRegistryEmail(username)
                .withRegistryPassword(password)
                .withDockerTlsVerify(false)
                .build();


    }
}
