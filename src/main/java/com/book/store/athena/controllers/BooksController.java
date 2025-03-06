package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.books.FindAllBooksDto;
import com.book.store.athena.model.dto.books.CreateBooksDto;
import com.book.store.athena.model.dto.books.UpdateBooksDto;
import com.book.store.athena.model.repository.BooksRepository;
import com.book.store.athena.services.BooksService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BooksService booksService;

    @Transactional // rollback
    @PostMapping("/create")
    protected ResponseEntity <Void> createBook (@RequestBody @Valid CreateBooksDto books) {

        booksService.registerBook(books);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/collection")
    protected ResponseEntity <List <FindAllBooksDto>> findAllBooks () {

        var bookList = booksService.findAll();

        return ResponseEntity.ok(bookList);

    }

    @Transactional
    @PutMapping("/update/{id}")
    protected ResponseEntity <Void> updateBook (@PathVariable Long id, @Valid @RequestBody UpdateBooksDto books) {

        booksService.updateById(id, books);

        return ResponseEntity.ok().build();

    }

    @Transactional
    @PutMapping("/reactivate/{id}")
    protected ResponseEntity <Void> reactivateBook (@PathVariable Long id) {

        booksService.reactivateById(id);

        return ResponseEntity.noContent().build();

    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    protected ResponseEntity <Void> deleteBook (@PathVariable Long id) {

        booksService.disableById(id);

        return ResponseEntity.noContent().build();

    }

}
