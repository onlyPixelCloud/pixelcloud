package de.haizon.pixelcloud.api.service;

import de.haizon.pixelcloud.api.player.ICloudPlayer;
import de.haizon.pixelcloud.api.service.group.ICloudGroup;

import java.util.List;
import java.util.UUID;

public interface ICloudService {

    String getName();

    int getPort();

    int getId();

    UUID getUniqueId();

    ICloudGroup getCloudGroup();

    List<ICloudPlayer> getPlayers();

    void start();

    void stop();

}
