package ru.otus.spring.service.locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class LocaleServiceImplTest {
    private final LocaleServiceImpl ruLocaleService = new LocaleServiceImpl("ru_RU");

    @Test
    void whenDoLocaleServiceInstanceWithString_thenReturnLocaleWithCurrentLanguageAndCountry() {
        assertEquals("ru", ruLocaleService.getLocale().getLanguage());
        assertEquals("RU", ruLocaleService.getLocale().getCountry());
    }
}
