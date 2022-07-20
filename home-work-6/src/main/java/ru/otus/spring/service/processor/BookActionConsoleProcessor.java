package ru.otus.spring.service.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.dto.AuthorDto;
import ru.otus.spring.dto.BookDto;
import ru.otus.spring.dto.CommentDto;
import ru.otus.spring.dto.GenreDto;
import ru.otus.spring.service.io.IoService;
import ru.otus.spring.service.BookService;
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
        List<AuthorDto> availableAuthors = bookService.getAuthorsDto();
        ioService.printString("Select authors (comma seperated):");
        String separatedAuthorIndexes = ioService
                .readStringWithPrompts(formatAuthorsSelectionBody(availableAuthors));
        List<AuthorDto> selectedAuthors = getSelectedValues(separatedAuthorIndexes, availableAuthors);
        List<GenreDto> availableGenres = bookService.getGenresDto();
        ioService.printString("Select genres (comma seperated):");
        String separatedGenreIndexes = ioService
                .readStringWithPrompts(formatGenresSelectionBody(availableGenres));
        List<GenreDto> selectedGenres = getSelectedValues(separatedGenreIndexes, availableGenres);
        BookDto book = new BookDto(bookName, selectedAuthors, selectedGenres);
        bookService.createBook(book);
    }

    @Override
    public void processUpdate(long bookId) {
        BookDto book = bookService.findBookById(bookId);
        String currentName = book.getName();
        List<AuthorDto> currentAuthors = book.getAuthors();
        List<GenreDto> currentGenres = book.getGenres();
        String newName = ioService.readStringWithPrompts(
                String.format("Enter new book name, press enter to use old value: (%s)", currentName));
        newName = newName.isEmpty() ? currentName : newName;
        List<AuthorDto> availableAuthors = bookService.getAuthorsDto();
        String separatedAuthorIndexes = ioService.readStringWithPrompts(
                String.format("Select authors (comma separated), press enter to use old values: (%s)",
                        formatAuthorsBody(currentAuthors)),
                formatAuthorsSelectionBody(availableAuthors));
        List<AuthorDto> newAuthors = separatedAuthorIndexes.isEmpty() ? currentAuthors :
                getSelectedValues(separatedAuthorIndexes, availableAuthors);
        List<GenreDto> availableGenres = bookService.getGenresDto();
        String separatedGenreIndexes = ioService.readStringWithPrompts(
                String.format("Select genres (comma separated), press enter to use old values: (%s)",
                        formatGenresBody(currentGenres)),
                formatGenresSelectionBody(availableGenres));
        List<GenreDto> newGenres = separatedGenreIndexes.isEmpty() ? currentGenres :
                getSelectedValues(separatedGenreIndexes, availableGenres);
        book.setName(newName);
        book.setAuthors(newAuthors);
        book.setGenres(newGenres);
        bookService.updateBook(book);
    }

    @Override
    public void processDelete(long bookId) {
        BookDto book = bookService.findBookById(bookId);
        bookService.deleteBook(book);
    }

    @Override
    public void processShowAll() {
        ioService.printString(
                formatBooksBody(bookService.getBooksDto()));
    }

    @Override
    public void processGetComments(long bookId) {
        BookDto book = bookService.findBookById(bookId);
        ioService.printString(formatCommentsBody(bookService.getBookCommentsDto(book)));
    }

    @Override
    public void processAddComment(long bookId) {
        BookDto book = bookService.findBookById(bookId);
        String newComment = ioService.readStringWithPrompts("Enter new comment:");
        List<CommentDto> comments = book.getComments();
        comments.add(new CommentDto(newComment));
        bookService.updateBook(book);
    }

    @Override
    public void processDeleteComments(long bookId) {
        BookDto book = bookService.findBookById(bookId);
        List<CommentDto> currentComments = bookService.getBookCommentsDto(book);
        String separatedCommentIndexes = ioService.readStringWithPrompts(
                "Enter comments (comma separated) to delete, press enter to skip:",
                formatCommentsBody(currentComments));
        if (separatedCommentIndexes.isEmpty()) {
            return;
        }
        List<CommentDto> selectedComments = getSelectedValues(separatedCommentIndexes, currentComments);
        selectedComments.forEach(bookService::deleteComment);
    }

    private <T> List<T> getSelectedValues(String separatedSelectedIndexes, List<T> availableValues) {
        return Arrays.stream(separatedSelectedIndexes.split(","))
                .map(Integer::parseInt)
                .map(index -> userInputValidator.validateInputIndex(index, availableValues))
                .map(availableValues::get)
                .collect(Collectors.toList());
    }

    private String formatAuthorsSelectionBody(List<AuthorDto> availableAuthors) {
        return IntStream.range(0, availableAuthors.size())
                .mapToObj(i -> {
                    AuthorDto author = availableAuthors.get(i);
                    return String.format("%d - %s, %s", i, author.getFirstName(), author.getLastName());
                })
                .collect(Collectors.joining("; "));
    }

    private String formatAuthorsBody(List<AuthorDto> authors) {
        return authors.stream()
                .map(author ->
                        String.format("%s, %s", author.getFirstName(), author.getLastName())
                ).collect(Collectors.joining("; "));
    }

    private String formatGenresSelectionBody(List<GenreDto> availableGenres) {
        return IntStream.range(0, availableGenres.size())
                .mapToObj(i -> String.format("%d - %s",
                        i,
                        availableGenres.get(i).getName()))
                .collect(Collectors.joining("; "));
    }

    private String formatGenresBody(List<GenreDto> genres) {
        return genres.stream()
                .map(GenreDto::getName)
                .collect(Collectors.joining("; "));
    }

    private String formatBooksBody(List<BookDto> books) {
        return books.stream().map(bookDto -> {
            String authors = bookDto.getAuthors().stream()
                    .map(author ->
                            String.format("%s, %s", author.getFirstName(), author.getLastName()))
                    .collect(Collectors.joining("; "));
            String genres = bookDto.getGenres().stream()
                    .map(GenreDto::getName)
                    .collect(Collectors.joining("; "));
            return String.format("--- Book # %d ---\n Name: %s\n Authors: %s\n Genres: %s",
                    bookDto.getId(), bookDto.getName(), authors, genres);
        }).collect(Collectors.joining("\n"));
    }

    private String formatCommentsBody(List<CommentDto> comments) {
        return IntStream.range(0, comments.size())
                .mapToObj(i -> {
                    CommentDto commentDto = comments.get(i);
                    return String.format("--- Comment # %d ---\n %s",
                            i,
                            commentDto.getComment());
                })
                .collect(Collectors.joining("\n"));
    }
}
