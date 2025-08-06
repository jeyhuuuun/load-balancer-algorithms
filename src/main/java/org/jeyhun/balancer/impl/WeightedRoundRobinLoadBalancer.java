package org.jeyhun.balancer.impl;

import org.jeyhun.balancer.LoadBalancer;
import org.jeyhun.model.BackendServer;
import org.jeyhun.model.WeightedBackendServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WeightedRoundRobinLoadBalancer implements LoadBalancer {

    private final List<BackendServer> expandedWeightedList;

    private final AtomicInteger currentIndex = new AtomicInteger(0);

    public WeightedRoundRobinLoadBalancer(List<WeightedBackendServer> weightedBackendServers) {
        if (weightedBackendServers == null || weightedBackendServers.isEmpty()) {
            throw new IllegalArgumentException("Server list cannot be null or empty");
        }
        this.expandedWeightedList = Collections.unmodifiableList(buildServerList(weightedBackendServers));
    }

    @Override
    public BackendServer selectServer() {
        int index = Math.abs(currentIndex.getAndIncrement() % expandedWeightedList.size());
        return expandedWeightedList.get(index);
    }

    private List<BackendServer> buildServerList(List<WeightedBackendServer> weightedBackendServers) {
        List<BackendServer> weightedList = new ArrayList<>();
        for (WeightedBackendServer server : weightedBackendServers) {
            BackendServer backendServer = server.getBackendServer();
            if (backendServer.isHealthy()) {
                for (int i = 0; i < server.getWeight(); i++) {
                    weightedList.add(server.getBackendServer());
                }
            }
        }
        if (weightedList.isEmpty()) {
            throw new IllegalStateException("No healthy servers available");
        }
        return weightedList;
    }


}
