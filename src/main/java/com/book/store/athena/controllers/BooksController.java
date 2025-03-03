package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.FindAllBooksDto;
import com.book.store.athena.model.dto.CreateBooksDto;
import com.book.store.athena.model.dto.UpdateBooksDto;
import com.book.store.athena.model.entities.Books;
import com.book.store.athena.model.repository.BooksRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BooksRepository booksRepository;
    @Autowired
    private ResourcePatternResolver resourcePatternResolver;

    @PostMapping
    @Transactional // rollback
    protected void postBooks (@RequestBody @Valid CreateBooksDto books) {

        booksRepository.save(new Books(books));

    }

    @GetMapping("/collection")
    protected List <FindAllBooksDto> findAll () {

        return booksRepository.findAll().stream().map(FindAllBooksDto::new).collect(Collectors.toList());

    }

    @Transactional
    @PutMapping("/update/{id}")
    protected void update (@PathVariable Long id, @Valid @RequestBody UpdateBooksDto books) {

        var queriedBook = booksRepository.findById(id);

        if (queriedBook.isPresent()) {

            var bookToUpdate = queriedBook.get();

            bookToUpdate.updateBooks(books);

            booksRepository.save(bookToUpdate);

        }

    }

}
