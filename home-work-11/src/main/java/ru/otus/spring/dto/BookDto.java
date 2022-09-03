package ru.otus.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Mono;
import ru.otus.spring.model.Author;
import ru.otus.spring.model.Genre;

import java.util.List;


@Data
@AllArgsConstructor
public class BookDto {
    private String id;
    private String name;
    private List<Author> authors;
    private List<Genre> genres;
}
