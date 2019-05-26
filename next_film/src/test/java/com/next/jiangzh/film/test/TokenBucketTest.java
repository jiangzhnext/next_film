package com.next.jiangzh.film.test;

import com.google.common.util.concurrent.RateLimiter;
import com.next.jiangzh.film.test.bucket.TokenBucket;
import org.junit.Test;

public class TokenBucketTest {

    @Test
    public void bucketTest(){
        TokenBucket tokenBucket = new TokenBucket();
        for (int i=0 ;i<=20 ; i++){
            tokenBucket.hasToken();
        }
    }

    @Test
    public void rateLimitTest(){
        RateLimiter rateLimiter = RateLimiter.create(0.5);
        // 字节
        System.out.println(rateLimiter.acquire(5));
        System.out.println(rateLimiter.acquire(2));
        System.out.println(rateLimiter.acquire(1));
        System.out.println(rateLimiter.acquire(3));

    }

}
