package org.subho.design;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedWindowRateLimiter implements RateLimiter{

    private int maxRequests;
    private long windowSizeInMillis;
    private ConcurrentHashMap<String, AtomicInteger> requestCounts;
    private ConcurrentHashMap<String, Long> windowStartTimes;

    public FixedWindowRateLimiter(int maxRequests, long windowSizeInMillis) {
        this.maxRequests = maxRequests;
        this.windowSizeInMillis = windowSizeInMillis;
        requestCounts = new ConcurrentHashMap<>();
        windowStartTimes = new ConcurrentHashMap<>();
    }

    @Override
    public boolean allowRequest(String clientId) {
        System.out.println(requestCounts);
        long currentTime = System.currentTimeMillis();
        windowStartTimes.putIfAbsent(clientId, currentTime);
        requestCounts.putIfAbsent(clientId, new AtomicInteger(0));

        long windowStartTime = windowStartTimes.get(clientId);
        if(currentTime - windowStartTime >= windowSizeInMillis) {
            windowStartTimes.put(clientId, currentTime);
            requestCounts.get(clientId).set(0);
        }

        //AtomicInteger requestCount = requestCounts.get(clientId);
        if(requestCounts.get(clientId).get() < maxRequests) {
            requestCounts.get(clientId).incrementAndGet();
            return true;
        }
        return false;
    }

    @Override
    public void updateConfig(int maxRequest, long windowSizeInMillis) {
        this.maxRequests = maxRequest;
        this.windowSizeInMillis = windowSizeInMillis;
    }
}
