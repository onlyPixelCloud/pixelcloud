package de.haizon.pixelcloud.master.backend.manager;

import de.haizon.pixelcloud.api.service.group.ICloudGroup;
import de.haizon.pixelcloud.master.CloudMaster;
import de.haizon.pixelcloud.master.backend.group.CloudGroupImpl;
import de.haizon.pixelcloud.master.backend.json.JsonLib;

import java.util.LinkedList;
import java.util.List;

public class CloudGroupManager {

    private final List<ICloudGroup> cloudGroups;

    public CloudGroupManager() {
        this.cloudGroups = new LinkedList<>();
    }

    public int loadGroups(){

        CloudMaster.getInstance().getFileManager().listFiles("groups").forEach(file -> {
            JsonLib jsonLib = JsonLib.fromJsonFile(file);

            if(jsonLib == null){
                CloudMaster.getInstance().getCloudLogger().severe("Could not load group " + file.getName() + " because the json file is invalid.");
                return;
            }

            String name = jsonLib.getString("name");
            String template = jsonLib.getString("template");
            String versionType = jsonLib.getString("versionType");
            String version = jsonLib.getString("version");
            String serviceType = jsonLib.getString("serviceType");
            int minServiceCount = jsonLib.getInt("minServiceCount");
            int maxServiceCount = jsonLib.getInt("maxServiceCount");
            int maxMemory = jsonLib.getInt("maxMemory");
            int maxPlayers = jsonLib.getInt("maxPlayers");
            int percentageToStartNewService = jsonLib.getInt("percentageToStartNewService");
            int startPort = jsonLib.getInt("startPort");

            cloudGroups.add(new CloudGroupImpl(name, template, versionType, version, serviceType, minServiceCount, maxServiceCount, maxMemory, maxPlayers, percentageToStartNewService, startPort));
        });

        return cloudGroups.size();
    }

    public List<ICloudGroup> getCloudGroups() {
        return cloudGroups;
    }
}
