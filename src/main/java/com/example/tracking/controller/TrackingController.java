package com.example.tracking.controller;

import com.example.tracking.dto.TrackingResponse;
import com.example.tracking.service.TrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/next-tracking-number")
@RequiredArgsConstructor
public class TrackingController {

    private final TrackingService trackingService;

    @GetMapping
    public ResponseEntity<TrackingResponse> getTrackingNumber(
        @RequestParam String origin_country_id,
        @RequestParam String destination_country_id,
        @RequestParam BigDecimal weight,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime created_at,
        @RequestParam UUID customer_id,
        @RequestParam String customer_name,
        @RequestParam String customer_slug
    ) {
        String trackingNumber = trackingService.generateUniqueTrackingNumber(
            origin_country_id, destination_country_id, weight, created_at, customer_id, customer_name, customer_slug);
        return ResponseEntity.ok(new TrackingResponse(trackingNumber, OffsetDateTime.now()));
    }
}