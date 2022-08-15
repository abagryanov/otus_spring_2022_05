package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import ru.otus.spring.service.processor.BookActionProcessor;

@ShellComponent
@RequiredArgsConstructor
public class BookWorks {
    private final BookActionProcessor bookActionProcessor;

    private String bookId;

    @ShellMethod(value = "Create book command", key = {"cb", "creteBook"})
    public String createBook() {
        bookActionProcessor.processCreate();
        return "Book is created!";
    }

    @ShellMethod(value = "Select book command", key = {"sb", "selectBook"})
    public String selectBook(String bookIdValue) {
        this.bookId = bookIdValue;
        return String.format("Book %s is selected", bookId);
    }

    @ShellMethod(value = "Update book command", key = {"ub", "updateBook"})
    @ShellMethodAvailability("isBookIdSelected")
    public String updateBook() {
        bookActionProcessor.processUpdate(bookId);
        return "Book is updated!";
    }

    @ShellMethod(value = "Delete book command", key = {"db", "deleteBook"})
    @ShellMethodAvailability("isBookIdSelected")
    public String deleteBook() {
        bookActionProcessor.processDelete(bookId);
        bookId = null;
        return "Selected book is deleted!";
    }

    @ShellMethod(value = "List books command", key = {"lb", "listBooks"})
    public String listBooks() {
        bookActionProcessor.processShowAll();
        return "";
    }

    @ShellMethod(value = "List book comments command", key = {"lc", "listComments"})
    @ShellMethodAvailability("isBookIdSelected")
    public String listComments() {
        bookActionProcessor.processGetComments(bookId);
        return "";
    }

    @ShellMethod(value = "Add book comment command", key = {"ac", "addComment"})
    @ShellMethodAvailability("isBookIdSelected")
    public String addComment() {
        bookActionProcessor.processAddComment(bookId);
        return "New comment is saved!";
    }

    @ShellMethod(value = "Add book comment command", key = {"dc", "deleteComments"})
    @ShellMethodAvailability("isBookIdSelected")
    public String deleteComments() {
        bookActionProcessor.processDeleteComments(bookId);
        return "Selected comments are deleted!";
    }

    private Availability isBookIdSelected() {
        return bookId == null || bookId.isEmpty() ?
                Availability.unavailable("Select book id") :
                Availability.available();
    }
}
