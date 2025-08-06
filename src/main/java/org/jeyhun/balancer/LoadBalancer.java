package org.jeyhun.balancer;

import org.jeyhun.model.BackendServer;

import java.util.List;

public interface LoadBalancer {

    BackendServer selectServer();

}
