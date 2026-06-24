package com.shortner.shortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.Instant;

public record CreateShortUrlRequest(
        @NotBlank(message = "original url must not be blank")
        @Pattern(
                regexp = "^(https?://).+",
                message = "original url must start with http:// or https://"
        )
        String originalUrl,
        Instant expiresAt
) {
}
