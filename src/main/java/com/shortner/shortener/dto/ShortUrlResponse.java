package com.shortner.shortener.dto;

import java.time.Instant;

public record ShortUrlResponse(
        String shortCode,
        String shortUrl,
        String originalUrl,
        Instant createdAt,
        Instant expiresAt,
        Long clickCount
) {
}
