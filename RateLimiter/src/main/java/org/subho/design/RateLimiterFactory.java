package org.subho.design;

public class RateLimiterFactory {
    public static RateLimiter createRateLimiter(String type, int maxRequests, long windowSizeInMillis) {
        return switch (type.toLowerCase()) {
            case "fixed" -> new FixedWindowRateLimiter(maxRequests, windowSizeInMillis);
            case "sliding" -> new SlidingWindowRateLimiter(maxRequests, windowSizeInMillis);
            default -> throw new IllegalArgumentException("Unknown rate limiter type");
        };
    }
}
