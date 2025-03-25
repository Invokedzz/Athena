package com.book.store.athena.services;

import com.book.store.athena.api.SafeBrowsingService;
import com.book.store.athena.exceptions.BadRequestException;
import com.book.store.athena.exceptions.NotFoundException;
import com.book.store.athena.model.dto.books.CreateBooksDTO;
import com.book.store.athena.model.dto.books.FindAllBooksDTO;
import com.book.store.athena.model.dto.books.UpdateBooksDTO;
import com.book.store.athena.model.dto.client.FindUserBooksByIdDTO;
import com.book.store.athena.model.entities.Books;
import com.book.store.athena.model.repository.BooksRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BooksService {

    private final BooksRepository booksRepository;

    private final ProfanityContentService profanityContentService;

    private final SafeBrowsingService safeBrowsingService;

    public BooksService (BooksRepository booksRepository, ProfanityContentService profanityContentService, SafeBrowsingService safeBrowsingService) {

        this.booksRepository = booksRepository;

        this.profanityContentService = profanityContentService;

        this.safeBrowsingService = safeBrowsingService;

    }

    @Transactional
    public void create (CreateBooksDTO createBooksDto) {

        verifyIfURLIsValid(createBooksDto.pdfPath());

        profanityContentService.checkProfanityLevel(createBooksDto.toString());

        safeBrowsingService.verifyIfUrlIsUnsafe(createBooksDto.pdfPath());

        profanityContentService.checkHarmfulLinksAndUrls(createBooksDto.pdfPath());

        booksRepository.save(new Books(createBooksDto));

    }

    public Set<FindAllBooksDTO> findAll () {

        return booksRepository.findAllByActive(true).stream()
                .map(FindAllBooksDTO::new).collect(Collectors.toSet());

    }

    public Set <FindUserBooksByIdDTO> findUserBooksById (Long userId) {

        Set <FindUserBooksByIdDTO> books =  booksRepository.findFavoriteBooksByUserId(userId)
                .stream().map(FindUserBooksByIdDTO::new)
                .collect(Collectors.toSet());

         verifyIfBookCollectionExists(books);

        return books;

    }

    @Transactional
    public void update (Long id, UpdateBooksDTO updateBooksDto) {

        var queriedBook = booksRepository.findById(id);

        if (queriedBook.isPresent()) {

            var bookToUpdate = queriedBook.get();

            bookToUpdate.updateBooks(updateBooksDto);

            booksRepository.save(bookToUpdate);

        }

        verifyIfBookExists(queriedBook);

    }

    @Transactional
    public void reactivate (Long id) {

        var queriedBook = booksRepository.findById(id);

        if (queriedBook.isPresent()) {

            var bookAboutToUpdate = queriedBook.get();

            bookAboutToUpdate.activate();

            booksRepository.save(bookAboutToUpdate);

        }

        verifyIfBookExists(queriedBook);

    }

    @Transactional
    public void disable (Long id) {

        var queriedBook = booksRepository.findById(id);

        if (queriedBook.isPresent()) {

            var bookToDelete = queriedBook.get();

            bookToDelete.disable();

            booksRepository.save(bookToDelete);

        }

        verifyIfBookExists(queriedBook);

    }

    private void verifyIfURLIsValid (String url) {

        UrlValidator validator = new UrlValidator();

        if (!validator.isValid(url)) {

            throw new BadRequestException("Invalid URL. Try another one.");

        }

    }

    private void verifyIfBookCollectionExists (Set <FindUserBooksByIdDTO> books) {

        if (books.isEmpty()) {

            throw new NotFoundException("Book collection not found");

        }

    }

    private void verifyIfBookExists (Optional <?> book) {

        if (book.isEmpty()) {

            throw new NotFoundException("Book not found");

        }

    }

}
