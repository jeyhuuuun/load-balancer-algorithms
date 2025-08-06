package org.jeyhun.balancer.impl;

import org.jeyhun.balancer.LoadBalancer;
import org.jeyhun.model.BackendServer;

import java.util.List;
import java.util.Random;

public class RandomLoadBalancer implements LoadBalancer {
    private final Random random = new Random();

    private final List<BackendServer> servers;

    public RandomLoadBalancer(List<BackendServer> servers){
        this.servers = servers;
    }
    @Override
    public BackendServer selectServer() {

        List<BackendServer> healthyServers = servers
                .stream()
                .filter(BackendServer::isHealthy)
                .toList();

        if (healthyServers.isEmpty()) {
            return null;
        }

        return healthyServers.get(random.nextInt(servers.size()-1));
    }
}
