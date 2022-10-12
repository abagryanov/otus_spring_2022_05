package ru.otus.spring.writer;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.mongo.BookDocument;

import java.util.ArrayList;
import java.util.List;

@StepScope
@Component("bookCollectionWriter")
public class BookCollectionWriter implements CollectionWriter<BookDocument> {
    private final List<BookDocument> BOOKS = new ArrayList<>();

    @Override
    public void write(List<? extends BookDocument> books) {
        BOOKS.addAll(books);
    }

    @Override
    public List<BookDocument> getValues() {
        return new ArrayList<>(BOOKS);
    }
}
