package com.shortner.shortener;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class Base62EncoderTest {
    @Test
    void encode_zeroReturnsFirstAlphabetCharacter() {
        String result = Base62Encoder.encode(0);
        assertEquals("0",result);
    }

    @Test
    void encode_smallNumberReturnsCorrectCode() {
        String result = Base62Encoder.encode(61);
        assertEquals("z", result);
    }

    @Test
    void encode_largerNumberProducesShortString() {
        String result = Base62Encoder.encode(1000000);
        assertNotNull(result);
        assertTrue(result.length() < String.valueOf(1000000).length());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 1",
            "10, A",
            "62, 10",
            "125, 21"
    })
    void encode_knownValuesMatchExpected(long input, String expected) {
        assertEquals(expected, Base62Encoder.encode(input));
    }

    @Test
    void encode_neverReturnsNullOrEmpty() {
        for (long i = 0; i < 1000; i++) {
            String result = Base62Encoder.encode(i);
            assertNotNull(result);
            assertFalse(result.isEmpty());
        }
    }
}
