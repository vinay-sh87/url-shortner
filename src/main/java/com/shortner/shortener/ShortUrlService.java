package com.shortner.shortener;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Transactional
@RequiredArgsConstructor
public class ShortUrlService {
    private final ShortUrlRepository shortUrlRepository;

    public ShortUrl createShortUrl(String originalUrl, Instant expiresAt) {
        ShortUrl shortUrl = new ShortUrl(originalUrl, expiresAt, null);
        ShortUrl saved = shortUrlRepository.save(shortUrl);
        String code = Base62Encoder.encode(saved.getId());
        saved.setShortCode(code);
        return shortUrlRepository.save(saved);
    }

    @Transactional(readOnly = true)
    public ShortUrl getByShortCode(String shortCode) {
        ShortUrl shortUrl = shortUrlRepository.findByShortCode(shortCode).orElseThrow(() -> new ShortUrlNotFoundException(shortCode));
        if (shortUrl.isExpired() || !shortUrl.getIsActive()) {
            throw new ShortUrlNotFoundException(shortCode);
        }
        return shortUrl;
    }

    public void recordClick(ShortUrl shortUrl) {
        shortUrl.incrementClickCount();
        shortUrlRepository.save(shortUrl);
    }
}
