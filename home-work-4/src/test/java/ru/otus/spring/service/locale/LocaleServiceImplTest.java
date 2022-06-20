package ru.otus.spring.service.locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.config.ApplicationProperties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {LocaleServiceImpl.class})
public class LocaleServiceImplTest {

    @MockBean
    private ApplicationProperties properties;

    @Autowired
    private LocaleService localeService;

    @BeforeEach
    void setUp() {
        when(properties.getTestLocale()).thenReturn("ru_RU");
    }

    @Test
    void whenDoLocaleServiceInstanceWithString_thenReturnLocaleWithCurrentLanguageAndCountry() {
        assertEquals("ru", localeService.getLocale().getLanguage());
        assertEquals("RU", localeService.getLocale().getCountry());
    }
}
