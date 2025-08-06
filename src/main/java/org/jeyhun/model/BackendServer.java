package org.jeyhun.model;

public class BackendServer {

    private final String url;
    private volatile boolean healthy = true;

    public BackendServer(String url) {
        this.url = url;
    }

    public boolean isHealthy() {
        return healthy;
    }

    public String getUrl() {
        return url;
    }

    public void setHealthy(boolean healthy) {
        this.healthy = healthy;
    }
}
