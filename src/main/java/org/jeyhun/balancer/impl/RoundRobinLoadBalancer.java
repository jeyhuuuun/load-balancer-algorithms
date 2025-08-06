package org.jeyhun.balancer.impl;

import org.jeyhun.model.BackendServer;
import org.jeyhun.balancer.LoadBalancer;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinLoadBalancer implements LoadBalancer {

    private final AtomicInteger currentIndex = new AtomicInteger(0);

    private final List<BackendServer> servers;

    public RoundRobinLoadBalancer(List<BackendServer> servers) {
        this.servers = servers;
    }

    @Override
    public BackendServer selectServer() {

        int attempt = 0;

        while (attempt<servers.size()){
            int index = Math.abs(currentIndex.getAndIncrement() % servers.size());
            BackendServer backendServer = servers.get(index);
            if (backendServer.isHealthy()){
                return backendServer;
            }
            attempt++;
        }
        return null;
    }
}
