package de.haizon.pixelcloud.networking.client;

import de.princessheaven.aether.netty.bootstrap.client.NettyClient;
import de.princessheaven.aether.netty.event.EventRegistry;
import de.princessheaven.aether.netty.registry.SimplePacketRegistry;

import java.net.InetSocketAddress;

public class CloudClient {

    private static CloudClient instance;
    private final NettyClient nettyClient;

    public CloudClient() {
        instance = this;

        nettyClient = new NettyClient(new InetSocketAddress("127.0.0.1", 1330), new SimplePacketRegistry(), new EventRegistry(), future -> {
            if (future.isDone()) {
                System.out.println("Cloud netty client started.");
            }
        });

    }

    public NettyClient getNettyClient() {
        return nettyClient;
    }

    public static CloudClient getInstance() {
        return instance;
    }
}
