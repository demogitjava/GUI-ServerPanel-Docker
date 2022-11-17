package de.jgsoftwares.guiserverpanel.dao;


import com.fasterxml.jackson.databind.deser.std.JsonNodeDeserializer;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.*;
import com.github.dockerjava.api.model.BuildResponseItem;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.Image;
import com.github.dockerjava.core.*;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;

import com.github.dockerjava.transport.DockerHttpClient;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

public class dockerclient
{

    DefaultDockerClientConfig clientConfig;
    public static DockerClient client;

    private DockerCmdExecFactory dockerCmdExecFactory;

    private DockerHttpClient dockerHttpClient;

    public dockerclient()
    {

    }

    public void listContainers()
    {

        client.listContainersCmd();
        System.out.print("Container images");
    }

    public void startdockerclient(String username, String password)
    {
       clientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost("tcp://127.0.0.1:2375")
                .withRegistryEmail(username)
                .withRegistryPassword(password)
                .withDockerTlsVerify(false)
                .build();
       // client = DockerClientBuilder.getInstance(clientConfig).build();

        DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
                .dockerHost(clientConfig.getDockerHost())
                .maxConnections(100)
                .connectionTimeout(Duration.ofSeconds(30))
                .responseTimeout(Duration.ofSeconds(45))
                .build();
        DockerClient dockerClient = DockerClientBuilder.getInstance(clientConfig).build();

        CreateContainerCmd createContainerCmd = dockerClient.createContainerCmd("")
                .withStdinOpen(true)
                .withTty(true)
                .withCmd("test_cmd");

       // CreateContainerResponse container = createContainerCmd.exec();
       // dockerClient.startContainerCmd(container.getId()).exec();

      //  listContainers();

    }


    public void pullImage(String imageName)
    {
        client.pullImageCmd(imageName)
                .exec(new PullImageResultCallback());

    }
}
