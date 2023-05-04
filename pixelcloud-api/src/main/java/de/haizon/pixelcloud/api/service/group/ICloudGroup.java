package de.haizon.pixelcloud.api.service.group;

public interface ICloudGroup {

    String getName();

    String getTemplate();

    String getVersionType();

    String getVersion();

    String getServiceType();

    int getMinServiceCount();

    int getMaxServiceCount();

    int getMaxMemory();

    int getMaxPlayers();

    int getPercentageToStartNewService();

    int getStartPort();

}
