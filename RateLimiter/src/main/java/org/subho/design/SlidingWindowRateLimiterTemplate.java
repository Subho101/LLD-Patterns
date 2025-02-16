package org.subho.design;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class SlidingWindowRateLimiterTemplate extends AbstractRateLimiter{

    private Map<String, Queue<Long>> requestTimestamps;

    public SlidingWindowRateLimiterTemplate(int maxRequests, long windowSizeInMillis) {
        super(maxRequests, windowSizeInMillis);
        this.requestTimestamps = new HashMap<>();
    }

    @Override
    protected boolean isRequestAllowed(String clientId) {

        long currentTime = System.currentTimeMillis();
        requestTimestamps.putIfAbsent(clientId, new LinkedList<>());

        Queue<Long> timestamps = requestTimestamps.get(clientId);
        while(!timestamps.isEmpty() && currentTime - timestamps.peek() > windowSizeInMillis) {
            timestamps.poll();
        }

        if(timestamps.size() < maxRequests) {
            timestamps.add(currentTime);
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
