package net.endoflame.proxy;

import net.endoflame.proxy.jedis.PubSub;
import net.endoflame.proxy.manager.ConfigManager;
import net.md_5.bungee.api.plugin.Plugin;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public class EndoProxy extends Plugin {

    Jedis jedis;
    ConfigManager configManager;

    @Override
    public void onEnable() {
        configManager = new ConfigManager();
        configManager.generateConfig();

        jedis = new Jedis(configManager.getRedisHost());
        jedis.auth(configManager.getRedisPassword());

        JedisPubSub jedisPubSub = new PubSub();
    }

    @Override
    public void onDisable() {
        jedis.disconnect();
    }
}
