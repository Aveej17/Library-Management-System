package com.jeeva.LibraryManagementSystem.controller;

import com.jeeva.LibraryManagementSystem.Controller.BookController;
import com.jeeva.LibraryManagementSystem.Service.BookService;
import com.jeeva.LibraryManagementSystem.model.Book;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@ContextConfiguration(classes = {BookController.class})
public class TestBookController {

    // Injecting the following Mock data into txn service in order to test that's why
    // i am using InjectMocks

    @InjectMocks
    private BookController bookController;
    @Mock
    private BookService bookService;

    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateBook() throws Exception {
        when(bookService.createBook(any())).thenReturn(new Book());
        RequestBuilder requestBuilder = post("/book/create");
        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testCreateBookWithValidParams() throws Exception {
        when(bookService.createBook(any())).thenReturn(new Book());
        // Creating a Json
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bookName", "book");
        jsonObject.put("cost", "100");
        jsonObject.put("bookNo", "no");
        RequestBuilder requestBuilder = post("/book/create").
                contentType(MediaType.APPLICATION_JSON).content(String.valueOf(jsonObject));
        mockMvc.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
    }
}
