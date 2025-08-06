package org.jeyhun.balancer;

import org.jeyhun.balancer.impl.RandomLoadBalancer;
import org.jeyhun.model.BackendServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RandomLoadBalancerTest extends BaseLoadBalancerTest {

    @Test
    @DisplayName("Should return servers in random order")
    void testReturnServersRandom() {
        List<BackendServer> givenServers = List.of(
                healthy("http://localhost:8080"),
                unHealthy("http://localhost:8081"),
                healthy("http://localhost:8082")
        );

        LoadBalancer loadBalancer = new RandomLoadBalancer(givenServers);

        for (int i = 0; i < 100; i++) {
            BackendServer server = loadBalancer.selectServer();
            assertTrue(server.isHealthy());
            assertTrue(
                    server.getUrl().equals("http://localhost:8080") ||
                            server.getUrl().equals("http://localhost:8082"));
        }
    }
}
