package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.books.FindAllBooksDTO;
import com.book.store.athena.model.dto.books.CreateBooksDTO;
import com.book.store.athena.model.dto.books.UpdateBooksDTO;
import com.book.store.athena.model.dto.client.FindUserBooksByIdDTO;
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
    protected ResponseEntity <Void> createBook (@RequestBody @Valid CreateBooksDTO books) {

        booksService.create(books);

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/collection")
    protected ResponseEntity <Set<FindAllBooksDTO>> findAllBooks () {

        var bookList = booksService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(bookList);

    }

    @GetMapping("/profile/books/{id}")
    protected ResponseEntity <Set<FindUserBooksByIdDTO>> findAllFavorites (@PathVariable Long id) {

        var favorites = booksService.findUserBooksById(id);

        return ResponseEntity.status(HttpStatus.OK).body(favorites);

    }

    @Transactional
    @PutMapping("/update/{id}")
    protected ResponseEntity <Void> updateBook (@PathVariable Long id, @Valid @RequestBody UpdateBooksDTO books) {

        booksService.update(id, books);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @Transactional
    @PutMapping("/reactivate/{id}") // admin
    protected ResponseEntity <Void> reactivateBook (@PathVariable Long id) {

        booksService.reactivate(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @Transactional
    @DeleteMapping("/delete/{id}") // admin
    protected ResponseEntity <Void> deleteBook (@PathVariable Long id) {

        booksService.disable(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
