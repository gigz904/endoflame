package net.endoflame.server.cache;

import net.endoflame.server.tools.ConfigManager;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.params.SetParams;

public class RedisManager {

    public Jedis jedis;
    public RedisManager(Jedis jedis){
        this.jedis = jedis;
    }

    public String get(String key){
        String returned = jedis.get(key);
        jedis.close();
        return returned;
    }

    public void set(String key, String value){
        jedis.set(key, value);
        jedis.close();
    }

    public void remove(String key){

        jedis.del(key);
        jedis.close();
    }

    public void pub(String canal, String message){
        jedis.publish(canal, message);
    }

}
