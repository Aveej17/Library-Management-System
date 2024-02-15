package com.jeeva.LibraryManagementSystem.request;

import com.jeeva.LibraryManagementSystem.model.Author;
import com.jeeva.LibraryManagementSystem.model.Book;
import com.jeeva.LibraryManagementSystem.model.BookType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookCreateRequest {

    @NotBlank(message = "Book name must not be blank")
    private String bookName;
    private String bookNo;
    @Positive(message = "Deposit amount should be greater than 0")
    private int cost;
    private BookType type;
    private String authorName;
    private String authorEmail;

    public Author toAuthor(){
        return Author.builder().name(this.authorName).email(this.authorEmail).build();
    }

    public Book toBook() {
        return Book.builder().
                name(this.bookName).
                bookNo(this.bookNo).
                cost(this.cost).
                type(this.type).
                build();
    }

}
