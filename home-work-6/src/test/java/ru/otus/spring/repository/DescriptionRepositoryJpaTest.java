package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.model.Description;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import({DescriptionRepositoryJpa.class})
public class DescriptionRepositoryJpaTest {
    @Autowired
    private DescriptionRepositoryJpa descriptionRepositoryJpa;

    private static final int EXPECTED_DESCRIPTIONS_COUNT = 2;
    private static final int EXISTING_DESCRIPTION_ID = 1;
    private static final String EXISTING_DESCRIPTION = "Book about world and war";

    @Test
    void shouldReturnDescriptionById() {
        Description expectedDescription = new Description(
                EXISTING_DESCRIPTION_ID,
                EXISTING_DESCRIPTION);
        Optional<Description> actualDescriptionOptional = descriptionRepositoryJpa.findById(EXISTING_DESCRIPTION_ID);
        assertThat(actualDescriptionOptional).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedDescription);
    }

    @Test
    void shouldSaveDescription() {
        Description expectedDescription = new Description(0, "Description 1");
        expectedDescription = descriptionRepositoryJpa.save(expectedDescription);
        Optional<Description> actualDescriptionOptional = descriptionRepositoryJpa.findById(expectedDescription.getId());
        assertThat(actualDescriptionOptional).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedDescription);
    }

    @Test
    void shouldUpdateDescription() {
        Description expectedDescription = new Description(0, "Description 1");
        expectedDescription = descriptionRepositoryJpa.save(expectedDescription);
        expectedDescription.setDescription("Description 2");
        descriptionRepositoryJpa.save(expectedDescription);
        Optional<Description> actualDescriptionOptional = descriptionRepositoryJpa.findById(expectedDescription.getId());
        assertThat(actualDescriptionOptional).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedDescription);
    }

    @Test
    void shouldDeleteDescription() {
        List<Description> currentDescriptions = descriptionRepositoryJpa.findAll();
        assertThat(currentDescriptions.size()).isEqualTo(EXPECTED_DESCRIPTIONS_COUNT);
        currentDescriptions.forEach(author -> descriptionRepositoryJpa.delete(author));
        assertThat(descriptionRepositoryJpa.findAll().size()).isEqualTo(0);
    }

    @Test
    void shouldReturnExpectedAllDescriptions() {
        List<Description> currentDescriptions = descriptionRepositoryJpa.findAll();
        assertThat(currentDescriptions.size()).isEqualTo(EXPECTED_DESCRIPTIONS_COUNT);
    }
}
