package ru.otus.spring.service.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import ru.otus.spring.model.Author;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;
import ru.otus.spring.model.Genre;
import ru.otus.spring.service.BookService;
import ru.otus.spring.service.io.IoService;
import ru.otus.spring.validator.UserInputValidator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class BookActionConsoleProcessor implements BookActionProcessor {
    private final IoService ioService;
    private final UserInputValidator userInputValidator;
    private final BookService bookService;

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
        Book book = new Book(bookName, selectedAuthors, selectedGenres);
        bookService.createBook(book);
    }

    @Override
    public void processUpdate(String bookId) {
        Book book = bookService.findBookById(bookId);
        String currentName = book.getName();
        List<Author> currentAuthors = book.getAuthors();
        List<Genre> currentGenres = book.getGenres();
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
        book.setName(newName);
        book.setAuthors(newAuthors);
        book.setGenres(newGenres);
        bookService.updateBook(book);
    }

    @Override
    public void processDelete(String bookId) {
        Book book = bookService.findBookById(bookId);
        bookService.deleteBook(book);
    }

    @Override
    public void processShowAll() {
        ioService.printString(
                formatBooksBody(bookService.getBooks()));
    }

    @Override
    public void processGetComments(String bookId) {
        Book book = bookService.findBookById(bookId);
        ioService.printString(formatCommentsBody(bookService.getBookComments(book)));
    }

    @Override
    public void processAddComment(String bookId) {
        Book book = bookService.findBookById(bookId);
        String newComment = ioService.readStringWithPrompts("Enter new comment:");
        bookService.createComment(book, new Comment(newComment));
    }

    @Override
    public void processDeleteComments(String bookId) {
        Book book = bookService.findBookById(bookId);
        List<Comment> currentComments = bookService.getBookComments(book);
        String separatedCommentIndexes = ioService.readStringWithPrompts(
                "Enter comments (comma separated) to delete, press enter to skip:",
                formatCommentsBody(currentComments));
        if (separatedCommentIndexes.isEmpty()) {
            return;
        }
        List<Comment> selectedComments = getSelectedValues(separatedCommentIndexes, currentComments);
        selectedComments.forEach(comment -> bookService.deleteComment(book, comment));
    }

    private <T> List<T> getSelectedValues(String separatedSelectedIndexes, List<T> availableValues) {
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
        return books.stream().map(bookDto -> {
            String authors = bookDto.getAuthors().stream()
                    .map(author ->
                            String.format("%s, %s", author.getFirstName(), author.getLastName()))
                    .collect(Collectors.joining("; "));
            String genres = bookDto.getGenres().stream()
                    .map(Genre::getName)
                    .collect(Collectors.joining("; "));
            return String.format("--- Book # %s ---\n Name: %s\n Authors: %s\n Genres: %s",
                    bookDto.getId(), bookDto.getName(), authors, genres);
        }).collect(Collectors.joining("\n"));
    }

    private String formatCommentsBody(List<Comment> comments) {
        return IntStream.range(0, comments.size())
                .mapToObj(i -> {
                    Comment comment = comments.get(i);
                    return String.format("--- Comment # %d ---\n %s",
                            i,
                            comment.getText());
                })
                .collect(Collectors.joining("\n"));
    }
}
