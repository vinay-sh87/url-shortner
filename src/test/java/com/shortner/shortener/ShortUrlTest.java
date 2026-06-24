package com.shortner.shortener;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ShortUrlTest {
    @Test
    void isExpired_returnsFalse_whenExpiresAtIsNull() {
        ShortUrl shortUrl = new ShortUrl("https://example.com", null, null);
        boolean result = shortUrl.isExpired();
        assertFalse(result);
    }

    @Test
    void isExpired_returnsFalse_whenExpiredAtIsInFuture() {
        Instant futureTime = Instant.now().plus(1, ChronoUnit.DAYS);
        ShortUrl shortUrl = new ShortUrl("https://example.com", futureTime, null);
        boolean result = shortUrl.isExpired();
        assertFalse(result);
    }

    @Test
    void isExpired_returnsTrue_whenExpiresAtIsInPast() {
        Instant pastTime = Instant.now().minus(1, ChronoUnit.DAYS);
        ShortUrl shortUrl = new ShortUrl("https://example.com", pastTime, null);
        boolean result = shortUrl.isExpired();
        assertTrue(result);
    }
}
