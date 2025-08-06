package org.jeyhun.balancer;

import org.jeyhun.model.BackendServer;

public abstract class BaseLoadBalancerTest {
    protected BackendServer healthy(String url) {
        BackendServer backendServer = new BackendServer(url);
        backendServer.setHealthy(true);
        return backendServer;
    }


    protected BackendServer unHealthy(String url) {
        BackendServer backendServer = new BackendServer(url);
        backendServer.setHealthy(false);
        return backendServer;
    }
}
