package org.subho.design;

public interface RateLimiter {
    boolean allowRequest(String clientId);
}
