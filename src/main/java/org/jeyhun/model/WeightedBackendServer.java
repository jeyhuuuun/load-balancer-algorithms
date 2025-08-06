package org.jeyhun.model;

public class WeightedBackendServer {
    private final BackendServer backendServer;
    private final int weight;

    public WeightedBackendServer(BackendServer backendServer, int weight) {
        if (weight <= 0) {
            throw new IllegalArgumentException("Weight must be positive");
        }
        this.backendServer = backendServer;
        this.weight = weight;
    }

    public BackendServer getBackendServer() {
        return backendServer;
    }

    public int getWeight() {
        return weight;
    }

}
