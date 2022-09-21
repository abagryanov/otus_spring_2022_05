package ru.otus.spring.model.mongo;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "comment")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class CommentDocument {
    @Id
    @EqualsAndHashCode.Include
    @Getter
    private String id;

    @Field(name = "text")
    @Getter
    @Setter
    private String text;

    public CommentDocument(String text) {
        this.text = text;
        this.id = new ObjectId().toString();
    }
}
