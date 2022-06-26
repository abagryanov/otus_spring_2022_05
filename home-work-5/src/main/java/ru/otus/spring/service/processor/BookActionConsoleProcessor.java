package ru.otus.spring.service.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.dao.entity.AuthorDao;
import ru.otus.spring.dao.entity.BookDao;
import ru.otus.spring.dao.entity.GenreDao;
import ru.otus.spring.model.entity.Author;
import ru.otus.spring.model.entity.Book;
import ru.otus.spring.model.entity.Entity;
import ru.otus.spring.model.entity.Genre;
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
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    @Override
    public void processCreate() {
        String bookName = ioService.readStringWithPrompts("Enter book name:");
        List<Author> availableAuthors = authorDao.getAll();
        ioService.printString("Select authors (comma seperated):");
        String separatedAuthorIndexes = ioService
                .readStringWithPrompts(formatAuthorsSelectionBody(availableAuthors));
        List<Author> selectedAuthors = getSelectedValues(separatedAuthorIndexes, availableAuthors);
        List<Genre> availableGenres = genreDao.getAll();
        ioService.printString("Select genres (comma seperated):");
        String separatedGenreIndexes = ioService
                .readStringWithPrompts(formatGenresSelectionBody(availableGenres));
        List<Genre> selectedGenres = getSelectedValues(separatedGenreIndexes, availableGenres);
        Book book = new Book(bookName, selectedAuthors, selectedGenres);
        bookDao.save(book);
    }

    @Override
    public void processUpdate() {
        ioService.printString("Select book number to update:");
        List<Book> books = bookDao.getAll();
        int bookIndex = ioService.readIntWithPrompts(formatBooksBody(books)) - 1;
        bookIndex = userInputValidator.validateInputIndex(bookIndex, books);
        Book book = books.get(bookIndex);
        String currentName = book.getName();
        List<Author> currentAuthors = book.getAuthors();
        List<Genre> currentGenres = book.getGenres();
        String newName = ioService.readStringWithPrompts(
                String.format("Enter new book name, press enter to use old value: (%s)", currentName));
        newName = newName.isEmpty() ? currentName : newName;
        List<Author> availableAuthors = authorDao.getAll();
        String separatedAuthorIndexes = ioService.readStringWithPrompts(
                String.format("Select authors (comma separated), press enter to use old values: (%s)",
                        formatAuthorsBody(currentAuthors)),
                formatAuthorsSelectionBody(availableAuthors));
        List<Author> newAuthors = separatedAuthorIndexes.isEmpty() ? currentAuthors :
                getSelectedValues(separatedAuthorIndexes, availableAuthors);
        List<Genre> availableGenres = genreDao.getAll();
        String separatedGenreIndexes = ioService.readStringWithPrompts(
                String.format("Select genres (comma separated), press enter to use old values: (%s)",
                        formatGenresBody(currentGenres)),
                formatGenresSelectionBody(availableGenres));
        List<Genre> newGenres = separatedGenreIndexes.isEmpty() ? currentGenres :
                getSelectedValues(separatedGenreIndexes, availableGenres);
        book.setName(newName);
        book.setAuthors(newAuthors);
        book.setGenres(newGenres);
        bookDao.update(book);
    }

    @Override
    public void processDelete() {
        ioService.printString("Select book numbers to delete (comma separated):");
        List<Book> books = bookDao.getAll();
        Arrays.stream(ioService
                        .readStringWithPrompts(formatBooksBody(books))
                        .split(","))
                .map(number -> Integer.parseInt(number) - 1)
                .map(index -> userInputValidator.validateInputIndex(index, books))
                .map(books::get)
                .forEach(bookDao::delete);
    }

    @Override
    public void processShowAll() {
        ioService.printString(formatBooksBody(bookDao.getAll()));
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
                    return String.format("--- Book # %d ---\n Name: %s\n Authors: %s\n Genres: %s",
                            i + 1, book.getName(), authors, genres);
                }).collect(Collectors.joining("\n"));
    }
}
