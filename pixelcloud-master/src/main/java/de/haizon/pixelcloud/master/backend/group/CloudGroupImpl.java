package de.haizon.pixelcloud.master.backend.group;

import de.haizon.pixelcloud.api.service.group.ICloudGroup;

public class CloudGroupImpl implements ICloudGroup {

    private final String name, template, versionType, version, serviceType;
    private final int minServiceCount, maxServiceCount, maxMemory, maxPlayers, percentageToStartNewService, startPort;

    public CloudGroupImpl(String name, String template, String versionType, String version, String serviceType, int minServiceCount, int maxServiceCount, int maxMemory, int maxPlayers, int percentageToStartNewService, int startPort) {
        this.name = name;
        this.template = template;
        this.versionType = versionType;
        this.version = version;
        this.serviceType = serviceType;
        this.minServiceCount = minServiceCount;
        this.maxServiceCount = maxServiceCount;
        this.maxMemory = maxMemory;
        this.maxPlayers = maxPlayers;
        this.percentageToStartNewService = percentageToStartNewService;
        this.startPort = startPort;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTemplate() {
        return template;
    }

    @Override
    public String getVersionType() {
        return versionType;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getServiceType() {
        return serviceType;
    }

    @Override
    public int getMinServiceCount() {
        return minServiceCount;
    }

    @Override
    public int getMaxServiceCount() {
        return maxServiceCount;
    }

    @Override
    public int getMaxMemory() {
        return maxMemory;
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public int getPercentageToStartNewService() {
        return percentageToStartNewService;
    }

    @Override
    public int getStartPort() {
        return startPort;
    }
}
