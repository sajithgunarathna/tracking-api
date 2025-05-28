package com.example.tracking.util;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class TrackingNumberGeneratorTest {

    @Test
    void testGeneratedFormat() {
        String tracking = TrackingNumberGenerator.generate(
                "MY", "ID", new BigDecimal("1.234"),
                OffsetDateTime.parse("2023-05-28T10:00:00+08:00"),
                UUID.randomUUID(), "RedBox Logistics", "redbox-logistics");

        assertNotNull(tracking);
        assertEquals(16, tracking.length());
        assertTrue(tracking.matches("^[A-Z0-9]{16}$"));
    }

    @RepeatedTest(100)
    void testGeneratedUniqueness() {
        Set<String> seen = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            String tracking = TrackingNumberGenerator.generate(
                    "MY", "ID", new BigDecimal("1.234"),
                    OffsetDateTime.now(),
                    UUID.randomUUID(), "RedBox Logistics", "redbox-logistics");
            assertFalse(seen.contains(tracking), "Duplicate found: " + tracking);
            seen.add(tracking);
        }
    }
}