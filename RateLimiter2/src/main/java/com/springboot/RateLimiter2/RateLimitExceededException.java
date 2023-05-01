package com.springboot.RateLimiter2;

public class RateLimitExceededException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    private final long retryAfterMillis;

    public RateLimitExceededException(String message, long retryAfterMillis) {
        super(message);
        this.retryAfterMillis = retryAfterMillis;
    }

    public long getRetryAfterMillis() {
        return retryAfterMillis;
    }
}
