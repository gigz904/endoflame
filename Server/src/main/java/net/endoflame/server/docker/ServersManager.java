package net.endoflame.server.docker;

import net.endoflame.server.Main;
import net.endoflame.server.cache.RedisManager;
import net.endoflame.server.com.ProxyCom;

import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class ServersManager {

    RedisManager redisManager;

    public ServersManager(RedisManager redisManager) {
        this.redisManager = redisManager;
    }


    public void startDockerTask(){
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                deleteUnUsedServers();
                startUsefulsServers();
            }
        }, 5000, 5000);
    }

    public void deleteUnUsedServers(){

        Set<String> names=Main.getRedisManager().jedis.keys("SERVER:*");
        for (String server : names){
            String[] servername = server.split(":");
            String status = redisManager.get(server);
            if (status.equals("DELETED")){
                Main.getDockerAPI().deleteContainer(servername[1]);
                redisManager.remove(server);
            }
        }

    }

    public void startUsefulsServers(){
        for (String game : Main.getConfigManager().getGames()){
            int count = 0;
            Set<String> names=Main.getRedisManager().jedis.keys("SERVER:*");
            for (String server : names){
                if (server.contains(game)){
                    String[] servername = server.split(":");
                    String status = redisManager.get(server);

                    if (!status.equals("DELETED") && !status.equals("CLOSED")) {
                        if (!status.equals("DELETED")){
                            count++;
                        }

                    }
                }
            }

            if (count < Main.getConfigManager().getMinimalsServers()){
                startGame(game);
            }
        }
    }

    public void startLobby(){
        String name = getName("hub");
        Main.getDockerAPI().createandRunContainer(name, "hub");
        ProxyCom.addServer(name);
    }

    public boolean isStarted(String serv){

        if (Main.getRedisManager().jedis.exists("SERVER:" + serv)){
            return true;
        }else {
            return false;
        }

    }

    public void startGame(String g){
        String name = getName(g);
        Main.getDockerAPI().createandRunContainer(name, g);
        storeServer(name);
        System.out.println("Starting a new "+g+" game (Game Id: "+name+")");
        ProxyCom.addServer(name);
    }

    public void storeServer(String server){
        RedisManager redisManager = Main.getRedisManager();
        redisManager.set("SERVER:" + server, "0/0");
    }

    public String getName(String prefix){
        int i = 1;
        while(isStarted(prefix+i)){
            i++;
        }
        return prefix+i;
    }
    public void stopAll(){
        Set<String> names=Main.getRedisManager().jedis.keys("SERVER:*");
        for (String server : names){
            String[] servername = server.split(":");
            Main.getRedisManager().remove(servername[1]);
            Main.getDockerAPI().deleteContainer(servername[1]);
        }
    }

}