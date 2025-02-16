package org.subho.design;

public class RateLimiterManager {
    private static volatile RateLimiterManager instance;
    private RateLimiter rateLimiter;

    private RateLimiterManager(String type) {
        this.rateLimiter = RateLimiterFactory.createRateLimiter(type, 100, 60000);
    }

    public static RateLimiterManager getInstance(String type) {
        if(instance == null) {
            synchronized (RateLimiterManager.class) {
                if(instance == null) {
                    instance = new RateLimiterManager(type);
                }
            }
        }

        return instance;
    }

    public boolean allowRequest(String clientId) {
        return rateLimiter.allowRequest(clientId);
    }
}
