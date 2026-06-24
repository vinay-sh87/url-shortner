package com.shortner.shortener;

public class ShortUrlNotFoundException extends RuntimeException {
    public ShortUrlNotFoundException(String shortCode) {
        super("Short url not found or expired: " + shortCode);
    }
}
