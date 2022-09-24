package ru.otus.spring.model.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "genre")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class GenreDocument {
    @Id
    @EqualsAndHashCode.Include
    @Getter
    private String id;

    @Field(name = "name")
    @Getter
    @Setter
    private String name;

    public GenreDocument(String name) {
        this.name = name;
    }
}
