package ru.otus.spring.service.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import ru.otus.spring.model.Book;
import ru.otus.spring.model.Comment;
import ru.otus.spring.repository.BookRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataMongoTest
public class BookRepositoryTest {
    private static final int EXPECTED_BOOKS_COUNT = 2;

    private static final String EXPECTED_FIRST_BOOK_NAME = "White book";

    public static final String EXPECTED_SECOND_BOOK_NAME = "Black book";

    private static final int EXPECTED_FIRST_BOOK_COMMENTS_COUNT = 2;

    @Autowired
    private BookRepository bookRepository;


    @Test
    public void shouldReturnAllBooks() {
        List<Book> books = bookRepository.findAll();
        assertThat(books.size()).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @Test
    public void shouldReturnBookByName() {
        List<Book> books = bookRepository.findByName(EXPECTED_FIRST_BOOK_NAME);
        assertThat(books.size()).isEqualTo(1);
        Book book = books.get(0);
        assertThat(book.getName()).isEqualTo(EXPECTED_FIRST_BOOK_NAME);
    }

    @Test
    public void shouldSaveBook() {
        Book newBook = new Book("New Book", Collections.emptyList(), Collections.emptyList());
        bookRepository.save(newBook);
        List<Book> actualBooks = bookRepository.findByName("New Book");
        assertThat(actualBooks.size()).isEqualTo(1);
    }

    @Test
    public void shouldDeleteBook() {
        List<Book> actualBooks = bookRepository.findByName(EXPECTED_FIRST_BOOK_NAME);
        assertThat(actualBooks.size()).isEqualTo(1);
        Book bookToDelete = actualBooks.get(0);
        bookRepository.delete(bookToDelete);
        List<Book> expectedBooksAfterDelete = bookRepository.findByName(EXPECTED_FIRST_BOOK_NAME);
        assertThat(expectedBooksAfterDelete.size()).isEqualTo(0);
    }

    @Test
    public void shouldGetComments() {
        List<Book> actualBooks = bookRepository.findByName(EXPECTED_SECOND_BOOK_NAME);
        assertThat(actualBooks.size()).isEqualTo(1);
        Book actualBook = actualBooks.get(0);
        List<Comment> actualComments = bookRepository.getComments(actualBook);
        assertThat(actualComments.size()).isEqualTo(EXPECTED_FIRST_BOOK_COMMENTS_COUNT);
    }

    @Test
    public void shouldAddComment() {
        String newCommentText = "New comment";
        Comment newComment = new Comment(newCommentText);
        List<Book> actualBooks = bookRepository.findByName(EXPECTED_SECOND_BOOK_NAME);
        assertThat(actualBooks.size()).isEqualTo(1);
        Book actualBook = actualBooks.get(0);
        bookRepository.addComment(actualBook, newComment);
        List<Comment> actualComments = bookRepository.getComments(actualBook);
        List<String> commentsText = actualComments.stream()
                .map(Comment::getText)
                .collect(Collectors.toList());
        assertThat(commentsText.contains(newCommentText)).isEqualTo(true);
    }

    @Test
    public void shouldDeleteComment() {
        List<Book> actualBooks = bookRepository.findByName(EXPECTED_SECOND_BOOK_NAME);
        assertThat(actualBooks.size()).isEqualTo(1);
        Book actualBook = actualBooks.get(0);
        List<Comment> actualComments = bookRepository.getComments(actualBook);
        assertThat(actualComments.size()).isEqualTo(EXPECTED_FIRST_BOOK_COMMENTS_COUNT);
        bookRepository.deleteComment(actualBook, actualComments.get(0));
        List<Comment> actualCommentsAfterDelete = bookRepository.getComments(actualBook);
        assertThat(actualCommentsAfterDelete.size()).isEqualTo(EXPECTED_FIRST_BOOK_COMMENTS_COUNT - 1);
    }
}
