package net.endoflame.server.com;

import net.endoflame.server.Main;

public class ProxyCom {

    public static void addServer(String serverName){
        Main.getRedisManager().pub("AddServer", serverName);
    }

    public static void removeServer(String serverName){
        Main.getRedisManager().pub("RemoveServer", serverName);
    }
}
