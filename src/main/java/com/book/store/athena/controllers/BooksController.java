package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.books.FindAllBooksDto;
import com.book.store.athena.model.dto.books.CreateBooksDto;
import com.book.store.athena.model.dto.books.UpdateBooksDto;
import com.book.store.athena.model.entities.Books;
import com.book.store.athena.model.repository.BooksRepository;
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
    private BooksRepository booksRepository;

    @Transactional // rollback
    @PostMapping("/create")
    protected ResponseEntity <Void> createBook (@RequestBody @Valid CreateBooksDto books) {

        booksRepository.save(new Books(books));

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/collection")
    protected ResponseEntity <List <FindAllBooksDto>> findAllBooks () {

        var bookList = booksRepository.findAllByActive(true).stream()
                .map(FindAllBooksDto::new).toList();

        return ResponseEntity.ok(bookList);

    }

    @Transactional
    @PutMapping("/update/{id}")
    protected ResponseEntity <Void> updateBook (@PathVariable Long id, @Valid @RequestBody UpdateBooksDto books) {

        var queriedBook = booksRepository.findById(id);

        if (queriedBook.isPresent()) {

            var bookToUpdate = queriedBook.get();

            bookToUpdate.updateBooks(books);

            booksRepository.save(bookToUpdate);

            return ResponseEntity.ok().build();

        }

        return ResponseEntity.notFound().build();

    }

    @Transactional
    @PutMapping("/reactivate/{id}")
    protected ResponseEntity <Void> reactivateBook (@PathVariable Long id) {

        var queriedBook = booksRepository.findById(id);

        if (queriedBook.isPresent()) {

            var bookAboutToUpdate = queriedBook.get();

            bookAboutToUpdate.activate();

            booksRepository.save(bookAboutToUpdate);

            return ResponseEntity.noContent().build();

        }

        return ResponseEntity.notFound().build();

    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    protected ResponseEntity <Void> deleteBook (@PathVariable Long id) {

        var queriedBook = booksRepository.findById(id);

        if (queriedBook.isPresent()) {

            var bookToDelete = queriedBook.get();

            bookToDelete.inactive();

            return ResponseEntity.noContent().build();

        }

        return ResponseEntity.notFound().build();

    }

}
