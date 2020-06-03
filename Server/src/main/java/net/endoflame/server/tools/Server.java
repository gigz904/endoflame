package net.endoflame.server.tools;

public class Server {

    String name;
    String template;
    int players;
    int maxplayers;


    public void setName(String name) {
        this.name = name;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public void setMaxplayers(int maxplayers) {
        this.maxplayers = maxplayers;
    }

    public String getName() {
        return name;
    }

    public String getTemplate() {
        return template;
    }

    public int getPlayers() {
        return players;
    }

    public int getMaxplayers() {
        return maxplayers;
    }
}
