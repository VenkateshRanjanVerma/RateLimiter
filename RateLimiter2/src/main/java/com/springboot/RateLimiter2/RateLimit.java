package com.springboot.RateLimiter2;

import java.util.HashMap;
import java.util.Map;

public class RateLimit {

    private static final int DEFAULT_LIMIT = 7;
    static final int DEFAULT_WINDOW_SIZE = 60;
    private final Map<String, Map<String, LimitCounter>> userApiMap;
    private final int defaultLimit;
    private final int defaultWindowSize;

    public RateLimit() {
        this(DEFAULT_LIMIT, DEFAULT_WINDOW_SIZE);
    }

    public RateLimit(int defaultLimit, int defaultWindowSize) {
        this.userApiMap = new HashMap<>();
        this.defaultLimit = defaultLimit;
        this.defaultWindowSize = defaultWindowSize;
    }

    public boolean checkRateLimit(String apiKey, String userId) {
        return checkRateLimit(apiKey, userId, defaultLimit, defaultWindowSize);
    }

    public boolean checkRateLimit(String apiKey, String userId, int limit, int windowSize) {
        Map<String, LimitCounter> apiMap = userApiMap.computeIfAbsent(userId, k -> new HashMap<>());
        LimitCounter limitCounter = apiMap.computeIfAbsent(apiKey, k -> new LimitCounter(limit, windowSize));
        return limitCounter.tryAcquire();
    }

    private static class LimitCounter {
        private final int limit;
        private final int windowSize;
        private final long[] timestamps;
        private int index;

        LimitCounter(int limit, int windowSize) {
            this.limit = limit;
            this.windowSize = windowSize;
            this.timestamps = new long[limit];
            this.index = 0;
        }

        synchronized boolean tryAcquire() {
            long now = System.currentTimeMillis();
            if (now - timestamps[index] > windowSize * 1000) {
                timestamps[index] = now;
                index = (index + 1) % limit;
                return true;
            }
            return false;
        }
    }
}
