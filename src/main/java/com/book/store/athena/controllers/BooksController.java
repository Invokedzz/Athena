package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.BooksDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/books")
public class BooksController {

    @PostMapping
    private void postBooks (@RequestBody BooksDto booksJson) {

        System.out.println(booksJson);

    }

}
