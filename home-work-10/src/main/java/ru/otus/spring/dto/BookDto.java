package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private long id;
    private String name;
    private List<AuthorDto> authors = new ArrayList<>();
    private List<GenreDto> genres = new ArrayList<>();
    private List<CommentDto> comments = new ArrayList<>();

    public BookDto(String name, List<AuthorDto> authors, List<GenreDto> genres) {
        this.name = name;
        this.authors = authors;
        this.genres = genres;
    }

    public BookDto(String name, List<AuthorDto> authors, List<GenreDto> genres, List<CommentDto> comments) {
        this.name = name;
        this.authors = authors;
        this.genres = genres;
        this.comments = comments;
    }

    public BookDto(long id) {
        this.id = id;
    }
}
