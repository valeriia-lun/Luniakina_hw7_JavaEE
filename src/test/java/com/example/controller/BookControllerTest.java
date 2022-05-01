package com.example.controller;

import com.javaee.web.starter.model.Book;
import com.javaee.web.starter.service.BookService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.mockito.Mockito.when;

import java.util.List;

@SpringBootTest
@AutoConfigureWebTestClient
public class BookControllerTest {

  @MockBean
  private BookService bookService;

  @Autowired
  private WebTestClient webTestClient;

  @Test
  public void getAllBooks() {

    Book book1 = prepareBook();
    Book book2 = prepareBook();

    when(bookService.getAllBooks()).thenReturn(List.of(book1, book2));

    webTestClient.get()
        .uri("/books")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Book.class)
        .contains(book1, book2);
  }

  private Book prepareBook(){
    long currentTime = System.currentTimeMillis();

    return Book.builder()
        .name("name-" + currentTime)
        .isbn("ISBN-" + currentTime)
        .author("cool-author")
        .build();
  }

}
