package ru.otus.spring.service.l10n;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.service.locale.LocaleService;

import java.util.Locale;

@Service
public class LocalizationServiceImpl implements LocalizationService {
    private final Locale locale;

    private final MessageSource messageSource;


    public LocalizationServiceImpl(LocaleService localeService,
                                   MessageSource messageSource) {
        this.locale = localeService.getLocale();
        this.messageSource = messageSource;
    }

    @Override
    public String getMessage(String key) {
        return messageSource.getMessage(key, null, locale);
    }
}
