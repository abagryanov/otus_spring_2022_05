package ru.otus.spring.writer;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public interface CollectionWriter <T> extends ItemWriter<T> {
    List<T> getValues();
}
