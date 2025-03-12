package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.books.FindAllBooksDto;
import com.book.store.athena.model.dto.books.CreateBooksDto;
import com.book.store.athena.model.dto.books.UpdateBooksDto;
import com.book.store.athena.services.BooksService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;

    public BooksController(BooksService booksService) {

        this.booksService = booksService;

    }

    @Transactional // rollback
    @PostMapping("/create")
    protected ResponseEntity <Void> createBook (@RequestBody @Valid CreateBooksDto books) {

        booksService.registerBook(books);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/collection")
    protected ResponseEntity <Set<FindAllBooksDto>> findAllBooks () {

        var bookList = booksService.findAll();

        return ResponseEntity.ok(bookList);

    }

    @Transactional
    @PutMapping("/update/{id}")
    protected ResponseEntity <Void> updateBook (@PathVariable Long id, @Valid @RequestBody UpdateBooksDto books) {

        var book = booksService.updateById(id, books);

        if (book == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();

    }

    @Transactional
    @PutMapping("/reactivate/{id}") // admin
    protected ResponseEntity <Void> reactivateBook (@PathVariable Long id) {

        var book = booksService.reactivateById(id);

        if (book == null) return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();

    }

    @Transactional
    @DeleteMapping("/delete/{id}") // admin
    protected ResponseEntity <Void> deleteBook (@PathVariable Long id) {

        var book = booksService.disableById(id);

        if (book == null) return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();

    }

}
