package com.jeeva.LibraryManagementSystem.repository;


import com.jeeva.LibraryManagementSystem.model.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    private Author author;

    @BeforeEach
    public void setUp(){
        author = Author.builder().id(1).email("author@gmail.com").build();
        authorRepository.save(author);  // saves in h2
    }

    @Test
    public void testFindByEmail(){
        Author a = authorRepository.findByEmail("author@gmail.com");
        Assertions.assertEquals(author.getEmail(), a.getEmail());
    }

}
