package org.subho.design;

import java.util.HashMap;
import java.util.Map;

public class FixedWindowRateLimiterTemplate extends AbstractRateLimiter{

    private final Map<String, Integer> requestCounts;
    private final Map<String, Long> windowStartTimes;

    public FixedWindowRateLimiterTemplate(int maxRequests, long windowSizeInMillis) {
        super(maxRequests, windowSizeInMillis);
        this.requestCounts = new HashMap<>();
        this.windowStartTimes = new HashMap<>();
    }

    @Override
    protected boolean isRequestAllowed(String clientId) {
        long currentTime = System.currentTimeMillis();
        requestCounts.putIfAbsent(clientId, 0);
        windowStartTimes.putIfAbsent(clientId, currentTime);

        long windowStartTime = windowStartTimes.get(clientId);
        if(currentTime - windowStartTime >= windowSizeInMillis ) {
            windowStartTimes.put(clientId, currentTime);
            requestCounts.put(clientId, 0);
        }

        int requestCount = requestCounts.get(clientId) ;
        if(requestCount < maxRequests) {
            requestCounts.put(clientId, requestCount+1);
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
