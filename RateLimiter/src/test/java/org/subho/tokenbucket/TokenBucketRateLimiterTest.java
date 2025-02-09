package org.subho.tokenbucket;

import org.junit.jupiter.api.Test;

public class TokenBucketRateLimiterTest {

    @Test
    void testTokenBucketRateLimit() {
        TokenBucketRateLimiter rateLimiter = new TokenBucketRateLimiter(5, 1);

        for(int i=1; i<=15; i++) {
            if(rateLimiter.request(1)) {
                System.out.println("Request " + i + ": Allowed");
            } else {
                System.out.println("Request " + i + ": Denied");
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    void testTokenBucketRateLimit2Thread() {

        TokenBucketRateLimiter rateLimiter = new TokenBucketRateLimiter(10, 2);

        Thread t1 = new Thread(() -> {
            for(int i=1; i<=15; i++) {
                if(rateLimiter.request(1)) {
                    System.out.println(Thread.currentThread().getName() +": Request " + i + ": Allowed");
                } else {
                    System.out.println(Thread.currentThread().getName() +": Request " + i + ": Denied");
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-1");

        Thread t2 = new Thread(() -> {
            for(int i=1; i<=15; i++) {
                if(rateLimiter.request(1)) {
                    System.out.println(Thread.currentThread().getName() +": Request " + i + ": Allowed");
                } else {
                    System.out.println(Thread.currentThread().getName() +": Request " + i + ": Denied");
                }
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-2");

        t1.start();
        t2.start();


        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
