package ru.otus.spring.model.entity;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Genre implements Entity {
    @EqualsAndHashCode.Include
    private long id;
    private String name;

    public Genre(String name) {
        this.name = name;
    }

    public Genre(long id) {
        this.id = id;
    }
}
