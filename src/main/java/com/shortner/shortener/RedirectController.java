package com.shortner.shortener;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RedirectController {
    private final ShortUrlService shortUrlService;

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {
        ShortUrl shortUrl = shortUrlService.getByShortCode(shortCode);
        shortUrlService.recordClick(shortUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, shortUrl.getOriginalUrl());
        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
