package ru.otus.spring.service.locale;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class LocaleServiceImpl implements LocaleService {
    private final String localeSetting;

    public LocaleServiceImpl(@Value("${student.test.locale}")
                             String localeSetting) {
        this.localeSetting = localeSetting;
    }

    @Override
    public Locale getLocale() {
        String[] tokens = localeSetting.split("_");
        return tokens.length == 1 ?
                new Locale(tokens[0]) :
                new Locale(tokens[0], tokens[1]);
    }
}
