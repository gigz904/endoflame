package net.endoflame.server.docker;

import net.endoflame.server.Main;
import net.endoflame.server.tools.ConfigManager;

public class DockerTools {

    public static void startDefaultContainers(){
        System.out.println("Tentative de démarrage des containers par défaut...");
        DockerAPI dockerAPI = Main.getDockerAPI();
        ConfigManager configManager = Main.getConfigManager();

        for (String container : configManager.getContainersToStart()){
            try {
                dockerAPI.startContainer(container);

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }


}
