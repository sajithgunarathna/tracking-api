package com.example.tracking.service;

import com.example.tracking.util.TrackingNumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TrackingService {

    private final StringRedisTemplate redisTemplate;

    public String generateUniqueTrackingNumber(String origin, String destination, BigDecimal weight, OffsetDateTime createdAt,
                                               UUID customerId, String customerName, String customerSlug) {
        SetOperations<String, String> ops = redisTemplate.opsForSet();
        String tracking;
        int attempts = 0;
        do {
            tracking = TrackingNumberGenerator.generate(origin, destination, weight, createdAt, customerId, customerName, customerSlug);
            attempts++;
            if (attempts > 10) throw new IllegalStateException("Unable to generate unique tracking number");
        } while (Boolean.TRUE.equals(ops.isMember("tracking_numbers", tracking)));

        ops.add("tracking_numbers", tracking);
        return tracking;
    }
}