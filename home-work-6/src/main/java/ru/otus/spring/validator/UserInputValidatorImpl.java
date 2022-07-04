package ru.otus.spring.validator;

import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserInputValidatorImpl implements UserInputValidator {
    @Override
    public int validateInputIndex(int index, Collection<?> validatedCollection) {
         if (index < 0 || index >= validatedCollection.size()) {
             throw new IndexOutOfBoundsException();
         }
         return index;
    }
}
