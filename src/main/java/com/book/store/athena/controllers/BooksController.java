package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.BooksCollectionDto;
import com.book.store.athena.model.dto.BooksDto;
import com.book.store.athena.model.entities.Books;
import com.book.store.athena.model.repository.BooksRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BooksRepository booksRepository;

    @PostMapping
    @Transactional // rollback
    private void postBooks (@RequestBody @Valid BooksDto books) {

        booksRepository.save(new Books(books));

    }

    @GetMapping("/collection")
    public List <BooksCollectionDto> findAll () {

        return booksRepository.findAll().stream().map(BooksCollectionDto::new).collect(Collectors.toList());

    }

}
