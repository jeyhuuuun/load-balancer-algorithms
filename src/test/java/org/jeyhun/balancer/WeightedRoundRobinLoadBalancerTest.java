package org.jeyhun.balancer;

import org.jeyhun.balancer.impl.WeightedRoundRobinLoadBalancer;
import org.jeyhun.model.WeightedBackendServer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WeightedRoundRobinLoadBalancerTest extends BaseLoadBalancerTest {

    @Test
    @DisplayName("Should return servers in weighted round robin order")
    void testReturnServerInWeightedOrder() {
        List<WeightedBackendServer> weightedBackendServers = List.of(
                new WeightedBackendServer(healthy("http://localhost:8080"), 1),
                new WeightedBackendServer(healthy("http://localhost:8082"), 3)
        );

        LoadBalancer loadBalancer = new WeightedRoundRobinLoadBalancer(weightedBackendServers);

        List<String> actual = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            actual.add(loadBalancer.selectServer().getUrl());
        }

        List<String> expectedList = List.of(
                "http://localhost:8080",
                "http://localhost:8082",
                "http://localhost:8082",
                "http://localhost:8082",
                "http://localhost:8080",
                "http://localhost:8082"
        );

        assertEquals(expectedList, actual);
    }
}
