package de.haizon.pixelcloud.master.console.setups;

import de.haizon.pixelcloud.api.setup.ISetup;
import de.haizon.pixelcloud.api.setup.Question;
import de.haizon.pixelcloud.master.CloudMaster;

public class GroupSetup implements ISetup {

    private String name;
    private int maxServiceCount;

    @Question(id = 1, question = "What is the name of the group?")
    public boolean handle(String input){
        if(input.equals("du hurensohn")){
            name = input;
            return true;
        }
        return false;
    }

    @Question(id = 2, question = "What is the max service count?")
    public boolean handle2(String input){
        if(input.matches("[0-9]+")){
            maxServiceCount = Integer.parseInt(input);
            return true;
        }
        return false;
    }

    @Override
    public void finished() {
        CloudMaster.getInstance().getCloudLogger().info("Setup finished!");
        CloudMaster.getInstance().getCloudLogger().info("Name: " + name);
        CloudMaster.getInstance().getCloudLogger().info("MaxServiceCount: " + maxServiceCount);
    }

}
