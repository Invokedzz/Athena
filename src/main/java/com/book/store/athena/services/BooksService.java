package com.book.store.athena.services;

import com.book.store.athena.model.dto.books.CreateBooksDto;
import com.book.store.athena.model.dto.books.FindAllBooksDto;
import com.book.store.athena.model.dto.books.UpdateBooksDto;
import com.book.store.athena.model.entities.Books;
import com.book.store.athena.model.repository.BooksRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BooksService {

    private final BooksRepository booksRepository;

    public BooksService (BooksRepository booksRepository) {

        this.booksRepository = booksRepository;

    }

    public void registerBook (CreateBooksDto createBooksDto) {

        booksRepository.save(new Books(createBooksDto));

    }

    public Set< FindAllBooksDto> findAll () {

        return booksRepository.findAllByActive(true).stream()
                .map(FindAllBooksDto::new).collect(Collectors.toSet());

    }

    public Books update (Long id, UpdateBooksDto updateBooksDto) {

        var queriedBook = booksRepository.findById(id);

        if (queriedBook.isPresent()) {

            var bookToUpdate = queriedBook.get();

            bookToUpdate.updateBooks(updateBooksDto);

            booksRepository.save(bookToUpdate);

            return bookToUpdate;

        }

        return null;

    }

    public Books reactivate (Long id) {

        var queriedBook = booksRepository.findById(id);

        if (queriedBook.isPresent()) {

            var bookAboutToUpdate = queriedBook.get();

            bookAboutToUpdate.activate();

            booksRepository.save(bookAboutToUpdate);

            return bookAboutToUpdate;

        }

        return null;

    }

    public Books disable (Long id) {

        var queriedBook = booksRepository.findById(id);

        if (queriedBook.isPresent()) {

            var bookToDelete = queriedBook.get();

            bookToDelete.disable();

            booksRepository.save(bookToDelete);

            return bookToDelete;

        }

        return null;

    }

}
