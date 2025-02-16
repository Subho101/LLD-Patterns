package org.subho.design;

import org.junit.jupiter.api.Test;

public class RateLimiterTest {

    @Test
    public void FixedWindowRateLimiterTest() {
        RateLimiter fixedWindowRateLimiter = RateLimiterFactory.createRateLimiter("fixed", 5, 1000);
        long currentTime = System.currentTimeMillis();
        for(int i=0; i<25; i++) {

            System.out.println("Time::" + (System.currentTimeMillis() - currentTime) / 1000 + " - " + fixedWindowRateLimiter.allowRequest("client1"));
            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    @Test
    public void SlidingWindowRateLimiterTest() {
        RateLimiter fixedWindowRateLimiter = RateLimiterFactory.createRateLimiter("sliding", 5, 1000);
        long currentTime = System.currentTimeMillis();
        for(int i=0; i<12; i++) {
            System.out.println("Time::" + (System.currentTimeMillis() - currentTime) / 1000 + " - " + fixedWindowRateLimiter.allowRequest("client1"));

            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
    }

    @Test
    public void RateLimiterTestObserver() {
        RateLimiterConfig rateLimiterConfig = new RateLimiterConfig();
        RateLimiter fixedWindowRateLimiter = RateLimiterFactory.createRateLimiter("fixed", 5, 1000);

        for(int i=0; i<50; i++) {
            System.out.println(fixedWindowRateLimiter.allowRequest("client1"));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        rateLimiterConfig.addObserver(fixedWindowRateLimiter);
        rateLimiterConfig.setMaxRequest(20);
        System.out.println("second run---");
        for(int i=0; i<50; i++) {
            System.out.println(fixedWindowRateLimiter.allowRequest("client1"));
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
