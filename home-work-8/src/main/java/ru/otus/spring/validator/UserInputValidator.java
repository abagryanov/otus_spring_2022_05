package ru.otus.spring.validator;

import java.util.Collection;

public interface UserInputValidator {
    int validateInputIndex(int index, Collection<?> validatedCollection);
}
