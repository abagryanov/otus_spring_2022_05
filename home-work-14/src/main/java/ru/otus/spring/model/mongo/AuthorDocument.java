package ru.otus.spring.model.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "author")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class AuthorDocument {
    @Id
    @EqualsAndHashCode.Include
    @Getter
    private String id;

    @Field(name = "firstName")
    @Getter
    @Setter
    private String firstName;

    @Field(name = "lastName")
    @Getter
    @Setter
    private String lastName;

    public AuthorDocument(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
