package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.spring.service.processor.BookActionProcessor;

@ShellComponent
@RequiredArgsConstructor
public class BookWorks {
    private final BookActionProcessor bookActionProcessor;

    @ShellMethod(value = "Create book command", key = {"cb", "creteBook"})
    public String createBook() {
        bookActionProcessor.processCreate();
        return "Book is created!";
    }

    @ShellMethod(value = "Update book command", key = {"ub", "updateBook"})
    public String updateBook() {
        bookActionProcessor.processUpdate();
        return "Book is updated!";
    }

    @ShellMethod(value = "Delete book command", key = {"db", "deleteBook"})
    public String deleteBook() {
        bookActionProcessor.processDelete();
        return "Selected books are deleted!";
    }

    @ShellMethod(value = "List books command", key = {"lb", "listBooks"})
    public String listBooks() {
        bookActionProcessor.processShowAll();
        return "";
    }
}
