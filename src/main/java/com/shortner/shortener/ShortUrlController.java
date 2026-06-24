package com.shortner.shortener;

import com.shortner.shortener.dto.CreateShortUrlRequest;
import com.shortner.shortener.dto.ShortUrlResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
public class ShortUrlController {
    private final ShortUrlService shortUrlService;

    @PostMapping
    public ResponseEntity<ShortUrlResponse> createShortUrl(@Valid @RequestBody CreateShortUrlRequest request) {
        ShortUrl shortUrl = shortUrlService.createShortUrl(request.originalUrl(), request.expiresAt());
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(shortUrl));
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<ShortUrlResponse> getShortUrlDetails(@PathVariable String shortCode) {
        ShortUrl shortUrl = shortUrlService.getByShortCode(shortCode);
        return ResponseEntity.ok(toResponse(shortUrl));
    }

    private ShortUrlResponse toResponse(ShortUrl shortUrl) {
        return new ShortUrlResponse(
                shortUrl.getShortCode(),
                "http://localhost:8080/" + shortUrl.getShortCode(),
                shortUrl.getOriginalUrl(),
                shortUrl.getCreatedAt(),
                shortUrl.getExpiresAt(),
                shortUrl.getClickCount()
        );
    }

}
