package ru.otus.spring.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.spring.dto.*;
import ru.otus.spring.service.BookService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping()
    public String index(Model model) {
        List<BookDto> books = bookService.getBooksDto();
        model.addAttribute("books", books);
        return "index";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(name = "id") long id) {
        bookService.deleteBook(new BookDto(id));
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable(name = "id") long id,
                       Model model) {
        BookDto book = bookService.findBookById(id);
        List<AuthorDto> availableAuthors = bookService.getAuthorsDto();
        List<GenreDto> availableGenres = bookService.getGenresDto();
        model.addAttribute("book", book);
        model.addAttribute("availableAuthors", availableAuthors);
        model.addAttribute("availableGenres", availableGenres);
        return "editBook";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute("book") BookUpdateRequestDto bookUpdateRequestDto) {
        bookService.updateBook(bookUpdateRequestDto);
        return "redirect:/";
    }

    @GetMapping("/comments/{id}")
    public String comments(@PathVariable(name = "id") long id,
                           Model model) {
        BookDto book = bookService.findBookById(id);
        List<CommentDto> comments = bookService.getBookCommentsDto(book);
        model.addAttribute("bookId", id);
        model.addAttribute("bookComments", comments);
        return "comments";
    }

    @GetMapping("/create")
    public String create(Model model) {
        List<AuthorDto> availableAuthors = bookService.getAuthorsDto();
        List<GenreDto> availableGenres = bookService.getGenresDto();
        model.addAttribute("availableAuthors", availableAuthors);
        model.addAttribute("availableGenres", availableGenres);
        return "createBook";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("book") BookUpdateRequestDto bookUpdateRequestDto) {
        bookService.createBook(bookUpdateRequestDto);
        return "redirect:/";
    }

    @PostMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable(name = "id") long id) {
        bookService.deleteComment(new CommentDto(id));
        return "redirect:/";
    }

    @GetMapping("/comments/create/{id}")
    public String createComment(@PathVariable(name = "id") long id,
                                Model model) {
        model.addAttribute("bookId", id);
        return "createComment";
    }

    @PostMapping("/comments/create/{id}")
    public String createComment(@PathVariable(name = "id") long id,
                                @ModelAttribute("comment") String comment) {
        BookDto book = bookService.findBookById(id);
        List<CommentDto> comments = book.getComments();
        comments.add(new CommentDto(comment));
        bookService.updateBook(book);
        return "redirect:/comments/" + id;
    }
}
