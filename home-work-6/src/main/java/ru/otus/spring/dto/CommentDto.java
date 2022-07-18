package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private long id;
    private String comment;
    private BookDto book;

    public CommentDto(String comment, BookDto book) {
        this.comment = comment;
        this.book = book;
    }
}
