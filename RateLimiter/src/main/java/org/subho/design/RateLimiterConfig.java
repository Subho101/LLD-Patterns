package org.subho.design;

import java.util.ArrayList;
import java.util.List;

public class RateLimiterConfig implements Publisher {
    private int maxRequest;
    private long windowSizeInMillis;
    private List<RateLimiter> observers;

    public RateLimiterConfig(int maxRequest, long windowSizeInMillis) {
        this();
        this.maxRequest = maxRequest;
        this.windowSizeInMillis = windowSizeInMillis;

    }

    public RateLimiterConfig() {
        this.observers = new ArrayList<>();
    }


    @Override
    public void addObserver(RateLimiter rateLimiter) {
        observers.add(rateLimiter);
    }

    @Override
    public void removeObserver(RateLimiter rateLimiter) {
        observers.remove(rateLimiter);
    }

    @Override
    public void notifyObserver() {
        observers.forEach(observer -> observer.updateConfig(maxRequest, windowSizeInMillis));
    }

    public void setMaxRequest(int maxRequest) {
        this.maxRequest = maxRequest;
        notifyObserver();
    }

    public void setWindowSizeInMillis(long windowSizeInMillis) {
        this.windowSizeInMillis = windowSizeInMillis;
        notifyObserver();
    }
}
