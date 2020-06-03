package net.endoflame.proxy.jedis;

import net.md_5.bungee.api.ProxyServer;
import redis.clients.jedis.JedisPubSub;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class PubSub extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        if(channel.equals("AddServer")){
            ProxyServer.getInstance().getServers().put(message,ProxyServer.getInstance().constructServerInfo(message, InetSocketAddress.createUnresolved(message,25565), "Endoflame Server | Dev by Gigzay & NoxiDor", false));
        }else if(channel.equals("RemoveServer")){
            ProxyServer.getInstance().getServers().remove(message);
        }
    }

}
