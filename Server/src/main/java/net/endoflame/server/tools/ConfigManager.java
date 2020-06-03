package net.endoflame.server.tools;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class ConfigManager {

    public ConfigManager() {
    }

    public String getDefaultConfig(){
        JSONObject config = new JSONObject();
        config.put("lobbyimage", "hub");
        config.put("start-containers", Arrays.asList("proxy01", "proxy02"));
        config.put("images-games", Arrays.asList("pvpbox", "hikabrain"));
        config.put("redis-host", "127.0.0.1");
        config.put("redis-port", "6379");
        config.put("redis-password", "password");
        config.put("minnimal-servers", "1");
        return config.toJSONString();
    }

    public void generateConfig() {
        File file = new File("config.json");
        if (!file.exists()) {
            try {
                file.createNewFile();
                FileWriter writer = new FileWriter(file.getName());
                writer.write(getDefaultConfig());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public JSONObject getConfig(){

        File file = new File("config.json");

        try{
            BufferedReader reader = new BufferedReader(new FileReader(file.getName()));
            JSONParser parser = new JSONParser();
            try {
                return (JSONObject) parser.parse(reader.readLine());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    public String getRedisHost(){
       return (String) getConfig().get("redis-host");
    }

    public int getRedisPort(){
        int port =Integer.parseInt((String) getConfig().get("redis-port"));
        return port;
    }

    public String getRedisPassword(){
        String password = (String)getConfig().get("redis-password");
        return password;
    }

    public List<String> getContainersToStart(){
        return (List) getConfig().get("start-containers");
    }

    public List<String> getGames(){
        return (List) getConfig().get("images-games");
    }


    public int getMinimalsServers(){
        int port =Integer.parseInt((String) getConfig().get("minnimal-servers"));
        return port;
    }
}
