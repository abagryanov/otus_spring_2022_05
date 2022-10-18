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

    public CommentDto(String comment) {
        this.comment = comment;
    }

    public CommentDto(long id) {
        this.id = id;
    }
}
