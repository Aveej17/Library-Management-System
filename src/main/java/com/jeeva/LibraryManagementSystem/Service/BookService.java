package com.jeeva.LibraryManagementSystem.Service;


import com.jeeva.LibraryManagementSystem.model.Author;
import com.jeeva.LibraryManagementSystem.model.Book;
import com.jeeva.LibraryManagementSystem.model.FilterType;
import com.jeeva.LibraryManagementSystem.model.BookType;
import com.jeeva.LibraryManagementSystem.model.Operator;
import com.jeeva.LibraryManagementSystem.repository.AuthorRepository;
import com.jeeva.LibraryManagementSystem.repository.BookRepository;
import com.jeeva.LibraryManagementSystem.repository.RedisDataRepository;
import com.jeeva.LibraryManagementSystem.request.BookCreateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service
public class BookService {


    @Autowired
    private BookRepository bookRepository;


    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private RedisDataRepository redisDataRepository;


    public Book createBook(BookCreateRequest bookCreateRequest) throws Exception {
        // check if either, which is coming to me from fe is already present in my db or not
        // if not present, add author into db

        // otherwise i will not add one more row inside my table
        Author authorFromDb = authorRepository.findByEmail(bookCreateRequest.getAuthorEmail());
        if(authorFromDb == null){
            // create a row inside the author table
            authorFromDb = authorRepository.save(bookCreateRequest.toAuthor());

        }
        //Check bookNo is same or not
        List<Book> books = bookRepository.findByBookNo(bookCreateRequest.getBookNo());
        if(!books.isEmpty() && books.get(0) != null){
            throw new Exception("Book no is already assigned to another Book");
        }

        // create a row inside my book
        Book book = bookCreateRequest.toBook();
        book.setAuthor(authorFromDb);
        book = bookRepository.save(book);

        // push the data into redis
        redisDataRepository.setBookToRedis(book);
        return book;
    }

    public List<Book> filter(FilterType filterBy, Operator operator, String value) {
        switch (operator){
            case EQUALS :
                switch (filterBy){
                    case BOOK_NO :
                        // we need to check if data is present in redis or not
                        List<Book> list = redisDataRepository.getBookByBookNo(value);
                        if(list!= null && !list.isEmpty()){
                            return list;
                        }
                        list = bookRepository.findByBookNo(value);
                        if(!list.isEmpty()){
                            redisDataRepository.setBookToRedisByBookNo(list.get(0));
                        }
                        return list;
                    case AUTHOR_NAME:
                        return bookRepository.findByAuthorName(value);
                    case COST:
                        return bookRepository.findByCost(Integer.valueOf(value));
                    case BOOKTYPE:
                        return bookRepository.findByType(BookType.valueOf(value));
                }
            case LESS_THAN:
                switch (filterBy){
                    case COST:
                        return bookRepository.findByCostLessThan(Integer.valueOf(value));
                }
            default:
                return new ArrayList<>();
        }

    }


    public void saveUpdate(Book book) {
        bookRepository.save(book);
    }
}
