package ru.otus.spring.service.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.model.*;
import ru.otus.spring.repository.AuthorRepository;
import ru.otus.spring.repository.BookRepository;
import ru.otus.spring.repository.DescriptionRepository;
import ru.otus.spring.repository.GenreRepository;
import ru.otus.spring.service.io.IoService;
import ru.otus.spring.service.io.IoServiceImpl;
import ru.otus.spring.validator.UserInputValidator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class BookActionConsoleProcessor implements BookActionProcessor {
    private IoService ioService = new IoServiceImpl(System.in, System.out);
    private final UserInputValidator userInputValidator;
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;

    private final DescriptionRepository descriptionRepository;

    @Override
    @Transactional
    public void processCreate() {
        String bookName = ioService.readStringWithPrompts("Enter book name:");
        List<Author> availableAuthors = authorRepository.findAll();
        ioService.printString("Select authors (comma seperated):");
        String separatedAuthorIndexes = ioService
                .readStringWithPrompts(formatAuthorsSelectionBody(availableAuthors));
        List<Author> selectedAuthors = getSelectedValues(separatedAuthorIndexes, availableAuthors);
        List<Genre> availableGenres = genreRepository.findAll();
        ioService.printString("Select genres (comma seperated):");
        String separatedGenreIndexes = ioService
                .readStringWithPrompts(formatGenresSelectionBody(availableGenres));
        List<Genre> selectedGenres = getSelectedValues(separatedGenreIndexes, availableGenres);
        List<Description> availableDescriptions = descriptionRepository.findAll();
        ioService.printString("Select description:");
        int descriptionIndex = ioService
                .readIntWithPrompts(formatDescriptionSelectionBody(availableDescriptions));
        Description selectedDescription = availableDescriptions.get(descriptionIndex);
        Book book = new Book(bookName, selectedAuthors, selectedGenres, selectedDescription);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void processUpdate() {
        ioService.printString("Select book number to update:");
        List<Book> books = bookRepository.findAll();
        int bookIndex = ioService.readIntWithPrompts(formatBooksBody(books)) - 1;
        bookIndex = userInputValidator.validateInputIndex(bookIndex, books);
        Book book = books.get(bookIndex);
        String currentName = book.getName();
        List<Author> currentAuthors = book.getAuthors();
        List<Genre> currentGenres = book.getGenres();
        Description currentDescription = book.getDescription();
        String newName = ioService.readStringWithPrompts(
                String.format("Enter new book name, press enter to use old value: (%s)", currentName));
        newName = newName.isEmpty() ? currentName : newName;
        List<Author> availableAuthors = authorRepository.findAll();
        String separatedAuthorIndexes = ioService.readStringWithPrompts(
                String.format("Select authors (comma separated), press enter to use old values: (%s)",
                        formatAuthorsBody(currentAuthors)),
                formatAuthorsSelectionBody(availableAuthors));
        List<Author> newAuthors = separatedAuthorIndexes.isEmpty() ? currentAuthors :
                getSelectedValues(separatedAuthorIndexes, availableAuthors);
        List<Genre> availableGenres = genreRepository.findAll();
        String separatedGenreIndexes = ioService.readStringWithPrompts(
                String.format("Select genres (comma separated), press enter to use old values: (%s)",
                        formatGenresBody(currentGenres)),
                formatGenresSelectionBody(availableGenres));
        List<Genre> newGenres = separatedGenreIndexes.isEmpty() ? currentGenres :
                getSelectedValues(separatedGenreIndexes, availableGenres);
        List<Description> availableDescriptions = descriptionRepository.findAll();
        String descriptionIndex = ioService.readStringWithPrompts(
                String.format("Select description, press enter to use old value: (%s)",
                        currentDescription.getDescription()),
                formatDescriptionSelectionBody(availableDescriptions));
        Description newDescription = descriptionIndex.isEmpty() ? currentDescription :
                getSelectedValue(descriptionIndex, availableDescriptions);
        book.setName(newName);
        book.setAuthors(newAuthors);
        book.setGenres(newGenres);
        book.setDescription(newDescription);
        bookRepository.save(book);
    }

    @Override
    @Transactional
    public void processDelete() {
        ioService.printString("Select book numbers to delete (comma separated):");
        List<Book> books = bookRepository.findAll();
        Arrays.stream(ioService
                        .readStringWithPrompts(formatBooksBody(books))
                        .split(","))
                .map(number -> Integer.parseInt(number) - 1)
                .map(index -> userInputValidator.validateInputIndex(index, books))
                .map(books::get)
                .forEach(bookRepository::delete);
    }

    @Transactional(readOnly = true)
    @Override
    public void processShowAll() {
        ioService.printString(formatBooksBody(bookRepository.findAll()));
    }

    @Override
    public void setIoService(IoService ioService) {
        this.ioService = ioService;
    }

    private <T extends Entity> List<T> getSelectedValues(String separatedSelectedIndexes, List<T> availableValues) {
        return Arrays.stream(separatedSelectedIndexes.split(","))
                .map(Integer::parseInt)
                .map(index -> userInputValidator.validateInputIndex(index, availableValues))
                .map(availableValues::get)
                .collect(Collectors.toList());
    }

    private <T extends Entity> T getSelectedValue(String selectedIndex, List<T> availableValues) {
        return availableValues.get(Integer.parseInt(selectedIndex));
    }

    private String formatAuthorsSelectionBody(List<Author> availableAuthors) {
        return IntStream.range(0, availableAuthors.size())
                .mapToObj(i -> {
                    Author author = availableAuthors.get(i);
                    return String.format("%d - %s, %s", i, author.getFirstName(), author.getLastName());
                })
                .collect(Collectors.joining("; "));
    }

    private String formatAuthorsBody(List<Author> authors) {
        return authors.stream()
                .map(author ->
                        String.format("%s, %s", author.getFirstName(), author.getLastName())
                ).collect(Collectors.joining("; "));
    }

    private String formatGenresSelectionBody(List<Genre> availableGenres) {
        return IntStream.range(0, availableGenres.size())
                .mapToObj(i -> String.format("%d - %s",
                        i,
                        availableGenres.get(i).getName()))
                .collect(Collectors.joining("; "));
    }

    private String formatDescriptionSelectionBody(List<Description> availableDescriptions) {
        return IntStream.range(0, availableDescriptions.size())
                .mapToObj(i -> String.format("%d - %s",
                        i,
                        availableDescriptions.get(i).getDescription()))
                .collect(Collectors.joining("; "));
    }

    private String formatGenresBody(List<Genre> genres) {
        return genres.stream()
                .map(Genre::getName)
                .collect(Collectors.joining("; "));
    }

    private String formatBooksBody(List<Book> books) {
        return IntStream.range(0, books.size())
                .mapToObj(i -> {
                    Book book = books.get(i);
                    String authors = book.getAuthors().stream()
                            .map(author -> String.format("%s, %s", author.getFirstName(), author.getLastName()))
                            .collect(Collectors.joining("; "));
                    String genres = book.getGenres().stream()
                            .map(Genre::getName)
                            .collect(Collectors.joining("; "));
                    String description = book.getDescription().getDescription();
                    return String.format("--- Book # %d ---\n Name: %s\n Authors: %s\n Genres: %s\n Description: %s",
                            i + 1, book.getName(), authors, genres, description);
                }).collect(Collectors.joining("\n"));
    }
}
