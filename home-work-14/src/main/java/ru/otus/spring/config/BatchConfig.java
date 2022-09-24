package ru.otus.spring.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.converter.CachedBookConverter;
import ru.otus.spring.model.mongo.BookDocument;
import ru.otus.spring.model.sql.Book;

import javax.persistence.EntityManagerFactory;

@EnableBatchProcessing
@Configuration
public class BatchConfig {
    @SuppressWarnings("all")
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @SuppressWarnings("all")
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @SuppressWarnings("all")
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @SuppressWarnings("all")
    @Autowired
    private CachedBookConverter cachedBookConverter;

    @SuppressWarnings("all")
    @Autowired
    private ItemWriter<BookDocument> bookWriter;

    @StepScope
    @Bean
    public JpaPagingItemReader<Book> bookReader() {
        JpaPagingItemReader<Book> bookReader = new JpaPagingItemReader<>();
        bookReader.setQueryString("select b from Book b");
        bookReader.setPageSize(10);
        bookReader.setEntityManagerFactory(entityManagerFactory);
        return bookReader;
    }

    @Bean
    public ItemProcessor<Book, BookDocument> bookProcessor() {
        return cachedBookConverter::convert;
    }

    @Bean
    public Step readBooks() {
        return stepBuilderFactory.get("readBooks")
                .<Book, BookDocument> chunk(10)
                .reader(bookReader())
                .processor(bookProcessor())
                .writer(bookWriter)
                .build();
    }

    @Bean
    public Job importBooksJob() {
        return jobBuilderFactory.get("importBooksJob")
                .start(readBooks())
                .build();
    }
}
