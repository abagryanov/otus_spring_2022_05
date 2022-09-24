package ru.otus.spring.writer;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;
import ru.otus.spring.model.mongo.AuthorDocument;
import ru.otus.spring.model.mongo.BookDocument;
import ru.otus.spring.model.mongo.GenreDocument;
import ru.otus.spring.repository.AuthorDocumentRepository;
import ru.otus.spring.repository.BookDocumentRepository;
import ru.otus.spring.repository.GenreDocumentRepository;

import java.util.List;
import java.util.stream.Collectors;

@StepScope
@Component
@RequiredArgsConstructor
public class BookWriter implements ItemWriter<BookDocument> {
    private final AuthorDocumentRepository authorDocumentRepository;

    private final GenreDocumentRepository genreDocumentRepository;

    private final BookDocumentRepository bookDocumentRepository;


    @Override
    public void write(List<? extends BookDocument> books) {
        List<AuthorDocument> authors = books.stream()
                .flatMap(book -> book.getAuthors().stream())
                .collect(Collectors.toList());

        List<GenreDocument> genres = books.stream()
                .flatMap(book -> book.getGenres().stream())
                .collect(Collectors.toList());

        authors.forEach(authorDocument -> {
            if (!authorDocumentRepository.existsById(authorDocument.getId())) {
                authorDocumentRepository.save(authorDocument);
            }
        });

        genres.forEach(genreDocument -> {
            if (!genreDocumentRepository.existsById(genreDocument.getId())) {
                genreDocumentRepository.save(genreDocument);
            }
        });

        bookDocumentRepository.saveAll(books);
    }
}
