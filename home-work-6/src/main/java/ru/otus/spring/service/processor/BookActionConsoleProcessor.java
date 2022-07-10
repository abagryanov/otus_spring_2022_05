package ru.otus.spring.service.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.model.*;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.io.IoService;
import ru.otus.spring.service.io.IoServiceImpl;
import ru.otus.spring.validator.UserInputValidator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class BookActionConsoleProcessor implements BookActionProcessor {
    private IoService ioService = new IoServiceImpl(System.in, System.out);
    private final UserInputValidator userInputValidator;
    private final BookService bookService;

    @Transactional
    @Override
    public void processCreate() {
        String bookName = ioService.readStringWithPrompts("Enter book name:");
        List<Author> availableAuthors = bookService.getAuthors();
        ioService.printString("Select authors (comma seperated):");
        String separatedAuthorIndexes = ioService
                .readStringWithPrompts(formatAuthorsSelectionBody(availableAuthors));
        List<Author> selectedAuthors = getSelectedValues(separatedAuthorIndexes, availableAuthors);
        List<Genre> availableGenres = bookService.getGenres();
        ioService.printString("Select genres (comma seperated):");
        String separatedGenreIndexes = ioService
                .readStringWithPrompts(formatGenresSelectionBody(availableGenres));
        List<Genre> selectedGenres = getSelectedValues(separatedGenreIndexes, availableGenres);
        String comment = ioService.readStringWithPrompts("Write comment (press enter to skip):");
        List<Comment> comments = comment.isEmpty() ?
                Collections.emptyList() :
                Collections.singletonList(new Comment(comment));
        Book book = new Book(bookName, selectedAuthors, selectedGenres, comments);
        bookService.createBook(book);
    }

    @Transactional
    @Override
    public void processUpdate() {
        ioService.printString("Select book number to update:");
        List<Book> books = bookService.getBooks();
        int bookIndex = ioService.readIntWithPrompts(formatBooksBody(books)) - 1;
        bookIndex = userInputValidator.validateInputIndex(bookIndex, books);
        Book book = books.get(bookIndex);
        String currentName = book.getName();
        List<Author> currentAuthors = book.getAuthors();
        List<Genre> currentGenres = book.getGenres();
        List<Comment> currentComments = book.getComments();
        String newName = ioService.readStringWithPrompts(
                String.format("Enter new book name, press enter to use old value: (%s)", currentName));
        newName = newName.isEmpty() ? currentName : newName;
        List<Author> availableAuthors = bookService.getAuthors();
        String separatedAuthorIndexes = ioService.readStringWithPrompts(
                String.format("Select authors (comma separated), press enter to use old values: (%s)",
                        formatAuthorsBody(currentAuthors)),
                formatAuthorsSelectionBody(availableAuthors));
        List<Author> newAuthors = separatedAuthorIndexes.isEmpty() ? currentAuthors :
                getSelectedValues(separatedAuthorIndexes, availableAuthors);
        List<Genre> availableGenres = bookService.getGenres();
        String separatedGenreIndexes = ioService.readStringWithPrompts(
                String.format("Select genres (comma separated), press enter to use old values: (%s)",
                        formatGenresBody(currentGenres)),
                formatGenresSelectionBody(availableGenres));
        List<Genre> newGenres = separatedGenreIndexes.isEmpty() ? currentGenres :
                getSelectedValues(separatedGenreIndexes, availableGenres);
        String separatedCommentIndexes = ioService.readStringWithPrompts(
                "Select comments to delete (comma separated), press enter to skip deleting:",
                formatCommentsSelectionBody(currentComments));
        if (!separatedCommentIndexes.isEmpty()) {
            deleteSelectedValues(separatedCommentIndexes, currentComments);
        }

        String comment = ioService.readStringWithPrompts("Add comment (press enter to skip):");
        if (!comment.isEmpty()) {
            currentComments.add(new Comment(comment));
        }
        book.setName(newName);
        book.setAuthors(newAuthors);
        book.setGenres(newGenres);
        bookService.updateBook(book);
    }

    @Transactional
    @Override
    public void processDelete() {
        ioService.printString("Select book numbers to delete (comma separated):");
        List<Book> books = bookService.getBooks();
        Arrays.stream(ioService
                        .readStringWithPrompts(formatBooksBody(books))
                        .split(","))
                .map(number -> Integer.parseInt(number) - 1)
                .map(index -> userInputValidator.validateInputIndex(index, books))
                .map(books::get)
                .forEach(bookService::deleteBook);
    }

    @Transactional(readOnly = true)
    @Override
    public void processShowAll() {
        ioService.printString(formatBooksBody(bookService.getBooks()));
    }

    @Override
    public void setIoService(IoService ioService) {
        this.ioService = ioService;
    }

    private <T> List<T> getSelectedValues(String separatedSelectedIndexes, List<T> availableValues) {
        return Arrays.stream(separatedSelectedIndexes.split(","))
                .map(Integer::parseInt)
                .map(index -> userInputValidator.validateInputIndex(index, availableValues))
                .map(availableValues::get)
                .collect(Collectors.toList());
    }

    private <T> void deleteSelectedValues(String separatedSelectedIndexes, List<T> currentValues) {
        List<T> valuesToRemove = Arrays.stream(separatedSelectedIndexes.split(","))
                .map(Integer::parseInt)
                .map(currentValues::get)
                .collect(Collectors.toList());
        currentValues.removeAll(valuesToRemove);
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

    private String formatCommentsSelectionBody(List<Comment> currentComments) {
        return IntStream.range(0, currentComments.size())
                .mapToObj(i -> String.format("%d - %s",
                        i,
                        currentComments.get(i).getComment()))
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
                    String comments = book.getComments().stream()
                            .map(Comment::getComment)
                            .collect(Collectors.joining("; "));
                    return String.format("--- Book # %d ---\n Name: %s\n Authors: %s\n Genres: %s\n Comments: %s",
                            i + 1, book.getName(), authors, genres, comments);
                }).collect(Collectors.joining("\n"));
    }
}
