package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.FindAllBooksDto;
import com.book.store.athena.model.dto.CreateBooksDto;
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
    protected void postBooks (@RequestBody @Valid CreateBooksDto books) {

        booksRepository.save(new Books(books));

    }

    @GetMapping("/collection")
    protected List <FindAllBooksDto> findAll () {

        return booksRepository.findAll().stream().map(FindAllBooksDto::new).collect(Collectors.toList());

    }

    @PutMapping("/update/{id}")
    protected void updateBooks (@PathVariable Long id, @Valid @RequestBody CreateBooksDto books) {

        //booksRepository.findById(id).ifPresent(booksRepository.save(new Books(books)));

    }

}
