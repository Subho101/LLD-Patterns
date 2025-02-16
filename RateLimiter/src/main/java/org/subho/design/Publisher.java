package org.subho.design;

public interface Publisher {
    void addObserver(RateLimiter rateLimiter);
    void removeObserver(RateLimiter rateLimiter);
    void notifyObserver();
}
