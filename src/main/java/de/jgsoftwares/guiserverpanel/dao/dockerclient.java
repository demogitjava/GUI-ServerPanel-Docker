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

import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;

import com.github.dockerjava.transport.DockerHttpClient;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;

public class dockerclient
{

    DefaultDockerClientConfig clientConfig;
    public static DockerClient dockerClient;

    private DockerCmdExecFactory dockerCmdExecFactory;
    private DockerHttpClient dockerHttpClient;

    public dockerclient()
    {

    }

    public void listContainers()
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

        dockerClient = DockerClientBuilder.getInstance(clientConfig).build();


    }


    public void pullImage(String imageName)
    {


    }
}
