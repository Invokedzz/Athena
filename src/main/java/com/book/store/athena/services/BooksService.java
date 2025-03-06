package com.book.store.athena.services;

import com.book.store.athena.model.dto.books.CreateBooksDto;
import com.book.store.athena.model.dto.books.FindAllBooksDto;
import com.book.store.athena.model.dto.books.UpdateBooksDto;
import com.book.store.athena.model.entities.Books;
import com.book.store.athena.model.repository.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService {

    @Autowired
    private BooksRepository booksRepository;

    public void registerBook (CreateBooksDto createBooksDto) {

        booksRepository.save(new Books(createBooksDto));

    }

    public List <FindAllBooksDto> findAll () {

        return booksRepository.findAllByActive(true).stream()
                .map(FindAllBooksDto::new).toList();

    }

    public Books updateById (Long id, UpdateBooksDto updateBooksDto) {

        var queriedBook = booksRepository.findById(id);

        if (queriedBook.isPresent()) {

            var bookToUpdate = queriedBook.get();

            bookToUpdate.updateBooks(updateBooksDto);

            booksRepository.save(bookToUpdate);

            return bookToUpdate;

        }

        return null;

    }

    public Books reactivateById (Long id) {

        var queriedBook = booksRepository.findById(id);

        if (queriedBook.isPresent()) {

            var bookAboutToUpdate = queriedBook.get();

            bookAboutToUpdate.activate();

            booksRepository.save(bookAboutToUpdate);

            return bookAboutToUpdate;

        }

        return null;

    }

    public Books disableById (Long id) {

        var queriedBook = booksRepository.findById(id);

        if (queriedBook.isPresent()) {

            var bookToDelete = queriedBook.get();

            bookToDelete.inactive();

            return bookToDelete;

        }

        return null;

    }

}
