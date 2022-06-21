package ru.otus.spring.service.locale;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.ApplicationProperties;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LocaleServiceImpl implements LocaleService {
    private final ApplicationProperties properties;

    @Override
    public Locale getLocale() {
        String[] tokens = properties.getTestLocale().split("_");
        return tokens.length == 1 ?
                new Locale(tokens[0]) :
                new Locale(tokens[0], tokens[1]);
    }
}
