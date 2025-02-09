package org.subho.tokenbucket;

import java.util.concurrent.locks.ReentrantLock;

public class TokenBucketRateLimiter {
    private final int capacity;
    private final double refillRate;
    private double tokens;
    private long lastRefillTime;
    private final ReentrantLock lock;

    public TokenBucketRateLimiter(int capacity, int refillRate) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokens = capacity;
        this.lastRefillTime = System.nanoTime();
        this.lock = new ReentrantLock();
    }

    private void refill() {
        long now = System.nanoTime();
        double elapsedTimeInSeconds = (now - lastRefillTime) / 1_000_000_000.0;
        double newTokens = elapsedTimeInSeconds * refillRate;

        lock.lock();
        try {
            tokens = Math.min(capacity, tokens + newTokens);
            lastRefillTime = now;
        } finally {
            lock.unlock();
        }
    }

    public boolean request(int tokensNeeded) {
        refill();
        lock.lock();
        try {
            if(tokens >= tokensNeeded) {
                tokens -= tokensNeeded;
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }
}
