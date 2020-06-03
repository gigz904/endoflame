package net.endoflame.server;


import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DockerClientBuilder;

import net.endoflame.server.cache.RedisManager;
import net.endoflame.server.docker.DockerAPI;
import net.endoflame.server.docker.DockerTools;
import net.endoflame.server.docker.ServersManager;
import net.endoflame.server.tools.ConfigManager;
import redis.clients.jedis.Jedis;

import java.util.Scanner;

public class Main {
    /**
     * TODO LIST
     * alors mtn renseigne oti comment get et set avec jedis

     */

    private static String version = "0.1-Dev";
    private static Jedis jedis;
    private static DockerClient dockerClient;
    private static DockerAPI dockerAPI;
    private static ConfigManager configManager;
    private static RedisManager redisManager;
    private static ServersManager serverManager;
    public static void main(String[] args){

        System.out.println(" _____           _       _____ _\n| ____|_ __   __| | ___ |  ___| | __ _ _ __ ___   ___\n|  _| | '_ \\ / _` |/ _ \\| |_  | |/ _` | '_ ` _ \\ / _ \\\n| |___| | | | (_| | (_) |  _| | | (_| | | | | | |  __/\n|_____|_| |_|\\__,_|\\___/|_|   |_|\\__,_|_| |_| |_|\\___|\n");
        System.out.println("==============================");
        System.out.println("Starting Endoflame Server version " + version + "...");
        System.out.println("Devs : NoxiDor & Gigzay (@NoxiDor_) (@GigzayOfficiel)");
        System.out.println("Help Discord : https://endoflame.net/discord");
        System.out.println("Website: https://endoflame.net");
        System.out.println("==============================");

        try {
            System.out.println("Initializing config...");
            configManager = new ConfigManager();
            configManager.generateConfig();
            System.out.println("Config loaded");
        }catch (Exception e){
            System.out.println("We encountered an error during configuration initialization");
        }
        configManager = new ConfigManager();
        configManager.generateConfig();

        try{
            System.out.println("Trying to establish the connection with redis... ("+configManager.getRedisHost()+":" + configManager.getRedisPort()+")");
            jedis = new Jedis(configManager.getRedisHost());
            jedis.auth(configManager.getRedisPassword());
            System.out.println("Connection with redis established");
        }catch(Exception e){
            System.out.println("We encountered an error during the connection with redis");
        }


        try{
            System.out.println("Trying to establish the connection with docker...");
            dockerClient = DockerClientBuilder.getInstance("unix:///var/run/docker.sock").build();
            System.out.println("Connection with docker established");
        }catch (Exception e ){
            System.out.println("We encountered an error during the connection with docker");

        }

        dockerAPI = new DockerAPI(dockerClient);
        redisManager = new RedisManager(jedis);


        //DockerTools.startDefaultContainers();


        serverManager = new ServersManager(redisManager);
        serverManager.startDockerTask();

        Scanner sc = new Scanner(System.in);
        System.out.println(">");

        String[] str = sc.nextLine().split(" ");
        if (str[0].equals("stop")){
            System.out.println("Stopping Endoflame Server...");

            serverManager.stopAll();

        }else if(str[0].equals("help")){
            System.out.println("Endoflame Server commands list :\nhelp : to show all commands\n" +
                    "stop : to stop Endoflame Server\n" +
                    "list : to show all started servers\n" +
                    "start <name> <image> : to start a server from a docker image" +
                    "deleteserver <name> : to delete a server");
            System.out.println(">");

        }else {
            System.out.println("Invalid command ! type help to list all commands");
            System.out.println(">");
        }

    }

    public static RedisManager getRedisManager() {
        return redisManager;
    }

    public static String getVersion() {
        return version;
    }

    public static Jedis getJedis() {
        return jedis;
    }

    public static DockerClient getDockerClient() {
        return dockerClient;
    }

   public static DockerAPI getDockerAPI() {
        return dockerAPI;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }
}
