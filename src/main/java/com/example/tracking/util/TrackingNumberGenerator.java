package com.example.tracking.util;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.OffsetDateTime;
import java.util.Base64;
import java.util.UUID;

public class TrackingNumberGenerator {

    public static String generate(String origin, String destination, BigDecimal weight, OffsetDateTime createdAt,
                                  UUID customerId, String customerName, String customerSlug) {
        try {
            // Combine metadata and UUID to create a unique source string
            String raw = origin + destination + weight + createdAt + customerId + customerName + customerSlug + UUID.randomUUID();

            // Create SHA-256 hash
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(raw.getBytes(StandardCharsets.UTF_8));

            // Convert to upper-case alphanumeric hex string
            String hex = bytesToHex(hash);

            // Truncate to exactly 16 characters
            return hex.substring(0, 16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b)); // uppercase hex: 0-9, A-F
        }
        return sb.toString().replaceAll("[^A-Z0-9]", "");
    }
}