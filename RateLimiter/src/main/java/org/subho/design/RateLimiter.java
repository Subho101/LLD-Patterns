package org.subho.design;

public interface RateLimiter {
    boolean allowRequest(String clientId);
    void updateConfig(int maxRequest, long windowSizeInMillis);
}
