package de.haizon.pixelcloud.networking.server;

import de.haizon.pixelcloud.api.logger.ICloudLogger;
import de.princessheaven.aether.netty.bootstrap.server.NettyServer;
import de.princessheaven.aether.netty.event.EventRegistry;
import de.princessheaven.aether.netty.registry.SimplePacketRegistry;

import java.net.InetSocketAddress;

public class CloudServer {

    private static CloudServer instance;
    private final NettyServer nettyServer;

    public CloudServer(ICloudLogger cloudLogger) {
        instance = this;

        nettyServer = new NettyServer(new InetSocketAddress("127.0.0.1", 1330), new SimplePacketRegistry(), new EventRegistry(), future -> {
            if(future.isDone()){
                cloudLogger.info("Cloud netty server started.");
            }
        });
    }

    public static CloudServer getInstance() {
        return instance;
    }

    public NettyServer getNettyServer() {
        return nettyServer;
    }

}
