package com.jeeva.LibraryManagementSystem.Controller;


import com.jeeva.LibraryManagementSystem.Service.BookService;
import com.jeeva.LibraryManagementSystem.model.Book;
import com.jeeva.LibraryManagementSystem.model.FilterType;
import com.jeeva.LibraryManagementSystem.model.Operator;
import com.jeeva.LibraryManagementSystem.request.BookCreateRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/create")
    public Book createBook(@RequestBody @Valid BookCreateRequest bookCreateRequest) throws Exception {
        return bookService.createBook(bookCreateRequest);
    }

    @GetMapping("/filter")
    public List<Book> filter(@RequestParam("filterBy") FilterType filterBy,
                             @RequestParam("operator") Operator operator,
                             @RequestParam("value") String value){
        return  bookService.filter(filterBy, operator, value);
    }

}
