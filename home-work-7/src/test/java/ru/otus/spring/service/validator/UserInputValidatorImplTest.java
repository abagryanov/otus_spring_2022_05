package ru.otus.spring.service.validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.validator.UserInputValidatorImpl;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest(classes = {UserInputValidatorImpl.class})
public class UserInputValidatorImplTest {
    @Autowired
    private UserInputValidatorImpl userInputValidator;

    @Test
    void whenValidIndexIsSet_thenReturnIndex() {
        int index = 2;
        List<String> collection = List.of("One", "Two", "Three");
        assertThat(userInputValidator.validateInputIndex(index, collection)).isEqualTo(index);
    }

    @Test
    void whenInvalidIndexIsSet_thenIndexOutOfBoundsExceptionIsThrown() {
        int index = 5;
        List<String> collection = List.of("One", "Two", "Three");
        assertThatThrownBy(() -> userInputValidator.validateInputIndex(index, collection))
                .isInstanceOf(IndexOutOfBoundsException.class);
    }

}
