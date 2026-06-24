package com.shortner.shortener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ShortUrlServiceTest {
    @Mock
    private ShortUrlRepository shortUrlRepository;

    @InjectMocks
    private ShortUrlService shortUrlService;

    @Test
    void getByShortCode_returnsShortUrl_whenFoundAndActive() {
        ShortUrl existingUrl = new ShortUrl("https://example.com", null, null);
        when(shortUrlRepository.findByShortCode("abc123")).thenReturn(Optional.of(existingUrl));
        ShortUrl result = shortUrlService.getByShortCode("abc123");
        assertEquals("https://example.com", result.getOriginalUrl());
    }

    @Test
    void getByShortCode_throwsException_whenNotFound() {
        when(shortUrlRepository.findByShortCode("missing")).thenReturn(Optional.empty());
        assertThrows(ShortUrlNotFoundException.class,()->{
            shortUrlService.getByShortCode("missing");
        });
    }

    @Test
    void getByShortCode_throwsException_whenExpired() {
        Instant pastTime = Instant.now().minusSeconds(3600);
        ShortUrl expiredUrl = new ShortUrl("https://example.com", pastTime, null);
        when(shortUrlRepository.findByShortCode("expired123")).thenReturn(Optional.of(expiredUrl));
        assertThrows(ShortUrlNotFoundException.class, ()->{
            shortUrlService.getByShortCode("expired123");
        });
    }
}
