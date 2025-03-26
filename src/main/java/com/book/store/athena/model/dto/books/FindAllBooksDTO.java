package com.book.store.athena.model.dto.books;

import com.book.store.athena.model.entities.Books;
import com.book.store.athena.model.enums.Genre;
import com.book.store.athena.model.enums.Publisher;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.util.Set;

public record FindAllBooksDTO(

        String name,

        String author,

        @Enumerated(EnumType.STRING)
        Set <Genre> genre,

        Publisher publisher,

        String description,

        String pdfPath

        ) {

            public FindAllBooksDTO(Books books) {

                this (books.getName(), books.getAuthor(), books.getGenre(),
                        books.getPublisher(), books.getDescription(), books.getPdfPath());

            }

        }
