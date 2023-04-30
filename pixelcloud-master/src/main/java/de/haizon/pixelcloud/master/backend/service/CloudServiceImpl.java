package de.haizon.pixelcloud.master.backend.service;

import de.haizon.pixelcloud.api.player.ICloudPlayer;
import de.haizon.pixelcloud.api.service.ICloudService;
import de.haizon.pixelcloud.api.service.group.ICloudGroup;

import java.util.List;
import java.util.UUID;

public class CloudServiceImpl implements ICloudService {
    @Override
    public String getName() {
        return null;
    }

    @Override
    public int getPort() {
        return 0;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public UUID getUniqueId() {
        return null;
    }

    @Override
    public ICloudGroup getCloudGroup() {
        return null;
    }

    @Override
    public List<ICloudPlayer> getPlayers() {
        return null;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
