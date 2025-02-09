package org.subho.tokenbucket;

import java.util.concurrent.locks.ReentrantLock;

public class TokenBucketRateLimiterDaemon {
    private final int capacity;
    private final double refillRate;
    private final int refillIntervalMs;
    private double tokens;
    private final ReentrantLock lock;

    public TokenBucketRateLimiterDaemon(int capacity, double refillRate, int refillIntervalMs) {
        this.capacity = capacity;
        this.refillRate = refillRate;
        this.refillIntervalMs = refillIntervalMs;
        this.tokens = capacity;
        this.lock = new ReentrantLock();

        Thread refillThread = new Thread(this::continuosRefill);
        refillThread.setDaemon(true);
        refillThread.start();

    }

    private void continuosRefill() {
        while(true) {
            try {
                Thread.sleep(refillIntervalMs);
                refill();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void refill() {
        lock.lock();
        try {
            tokens = Math.min(capacity, tokens + refillRate * (refillIntervalMs / 1000.0));
        } finally {
            lock.unlock();
        }
    }

    public boolean request(int tokensNeeded) {
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
