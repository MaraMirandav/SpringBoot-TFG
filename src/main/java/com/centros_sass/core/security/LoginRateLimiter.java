package com.centros_sass.core.security;

import com.centros_sass.app.exception.TooManyRequestsException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginRateLimiter {

    private static final Logger log = LoggerFactory.getLogger(LoginRateLimiter.class);

    private static final int MAX_ATTEMPTS = 5;

    private static final Duration REFILL_DURATION = Duration.ofMinutes(15);

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public void checkLimit(String ip) {
        Bucket bucket = buckets.computeIfAbsent(ip, this::newBucket);

        if (!bucket.tryConsume(1)) {
            log.warn("RATE_LIMIT_EXCEEDED ip={}", ip);
            throw new TooManyRequestsException(
                "Demasiados intentos fallidos. Inténtalo en 15 minutos.");
        }
    }

    private Bucket newBucket(String ip) {
        Bandwidth limit = Bandwidth.builder()
                .capacity(MAX_ATTEMPTS)
                .refillGreedy(MAX_ATTEMPTS, REFILL_DURATION)
                .build();
        return Bucket.builder().addLimit(limit).build();
    }

    @Scheduled(fixedDelay = 3_600_000)
    public void clearStaleEntries() {
        int size = buckets.size();
        buckets.clear();
        log.info("RATE_LIMITER_CLEANUP: {} entradas eliminadas", size);
    }
}