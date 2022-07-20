package ru.otus.spring.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.model.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@Import({CommentRepositoryJpa.class})
public class CommentRepositoryJpaTest {
    @Autowired
    private CommentRepositoryJpa commentRepositoryJpa;

    private static final int EXPECTED_COMMENTS_COUNT = 4;

    private static final int EXISTING_COMMENT_ID = 1;

    private static final String EXISTING_COMMENT = "Book about world and war";

    @Test
    void shouldReturnCommentById() {
        Optional<Comment> actualCommentOptional = commentRepositoryJpa.findById(EXISTING_COMMENT_ID);
        assertThat(actualCommentOptional).isPresent();
        Comment comment = actualCommentOptional.get();
        assertThat(comment.getComment()).isEqualTo(EXISTING_COMMENT);
    }

    @Test
    void shouldSaveComment() {
        Comment expectedComment = new Comment(0, "Description 1");
        expectedComment = commentRepositoryJpa.save(expectedComment);
        Optional<Comment> actualDescriptionOptional = commentRepositoryJpa.findById(expectedComment.getId());
        assertThat(actualDescriptionOptional).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    void shouldUpdateComment() {
        Comment expectedComment = new Comment(0, "Description 1");
        expectedComment = commentRepositoryJpa.save(expectedComment);
        expectedComment.setComment("Description 2");
        commentRepositoryJpa.save(expectedComment);
        Optional<Comment> actualDescriptionOptional = commentRepositoryJpa.findById(expectedComment.getId());
        assertThat(actualDescriptionOptional).isPresent().get()
                .usingRecursiveComparison().isEqualTo(expectedComment);
    }

    @Test
    void shouldDeleteComment() {
        List<Comment> currentComments = commentRepositoryJpa.findAll();
        assertThat(currentComments.size()).isEqualTo(EXPECTED_COMMENTS_COUNT);
        currentComments.forEach(author -> commentRepositoryJpa.delete(author));
        assertThat(commentRepositoryJpa.findAll().size()).isEqualTo(0);
    }

    @Test
    void shouldReturnExpectedAllComments() {
        List<Comment> currentComments = commentRepositoryJpa.findAll();
        assertThat(currentComments.size()).isEqualTo(EXPECTED_COMMENTS_COUNT);
    }
}
