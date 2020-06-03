package net.endoflame.server.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Network;

import java.util.List;

public class DockerAPI {

    private DockerClient dockerClient;

    public DockerAPI(DockerClient dockerClient) {
        this.dockerClient = dockerClient;
    }

    public void createandRunContainer(String name, String template){
        CreateContainerResponse container = dockerClient.createContainerCmd(name)
                .withImage(template)
                .withName(name)
                .withNetworkMode("mc")
                .exec();

        dockerClient.startContainerCmd(container.getId()).exec();
    }
    public void deleteContainer(String name){
        dockerClient.stopContainerCmd(name).exec();
        dockerClient.removeContainerCmd(name).exec();

    }
    public void createandRunContainer(String name, String template, ExposedPort port){
        //TODO
    }
    public void startContainer(String id){

        try {
            dockerClient.startContainerCmd(id).exec();
        }catch (Exception e){
            e.printStackTrace();
        }

    }



}