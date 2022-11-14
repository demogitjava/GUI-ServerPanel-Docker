package de.jgsoftwares.guiserverpanel.dao;


import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

import java.io.File;
import java.io.IOException;

public class dockerclient
{

    public dockerclient()
    {

    }


    public void startdockerclient()
    {
        try {
            DefaultDockerClientConfig clientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
                    .withDockerHost("tcp://127.0.0.1:2375")
                    .withDockerTlsVerify(false)
                    .build();


            DockerClient dockerClient = DockerClientBuilder.getInstance(clientConfig).build();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
