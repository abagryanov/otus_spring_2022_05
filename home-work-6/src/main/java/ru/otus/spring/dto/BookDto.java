package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private long id;
    private String name;
    private List<AuthorDto> authors;
    private List<GenreDto> genres;

    public BookDto(String name, List<AuthorDto> authors, List<GenreDto> genres) {
        this.name = name;
        this.authors = authors;
        this.genres = genres;
    }
}
