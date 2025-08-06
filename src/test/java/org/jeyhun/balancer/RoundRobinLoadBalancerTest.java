package org.jeyhun.balancer;

import org.jeyhun.balancer.impl.RoundRobinLoadBalancer;
import org.jeyhun.model.BackendServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoundRobinLoadBalancerTest extends BaseLoadBalancerTest {

    @Test
    @DisplayName("Should return servers in round robin order")
    void testReturnServersInRoundRobinOrder() {
        List<BackendServer> givenServers = List.of(
                healthy("http://localhost:8080"),
                healthy("http://localhost:8081"),
                healthy("http://localhost:8082")
        );

        LoadBalancer loadBalancer = new RoundRobinLoadBalancer(givenServers);

        assertEquals("http://localhost:8080", loadBalancer.selectServer().getUrl());
        assertEquals("http://localhost:8081", loadBalancer.selectServer().getUrl());
        assertEquals("http://localhost:8082", loadBalancer.selectServer().getUrl());
        assertEquals("http://localhost:8080", loadBalancer.selectServer().getUrl());
        assertEquals("http://localhost:8081", loadBalancer.selectServer().getUrl());
    }

    @Test
    @DisplayName("Should return healthy servers in round robin order")
    void testReturnServersInRoundRobinOrder_unHealthCase() {
        List<BackendServer> givenServers = List.of(
                healthy("http://localhost:8080"),
                unHealthy("http://localhost:8081"),
                healthy("http://localhost:8082")
        );

        LoadBalancer loadBalancer = new RoundRobinLoadBalancer(givenServers);

        assertEquals("http://localhost:8080", loadBalancer.selectServer().getUrl());
        assertEquals("http://localhost:8082", loadBalancer.selectServer().getUrl());
        assertEquals("http://localhost:8080", loadBalancer.selectServer().getUrl());
        assertEquals("http://localhost:8082", loadBalancer.selectServer().getUrl());
    }
}
