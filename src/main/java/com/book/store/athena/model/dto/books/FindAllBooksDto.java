package com.book.store.athena.model.dto.books;

import com.book.store.athena.model.entities.Books;
import com.book.store.athena.model.enums.Genre;
import com.book.store.athena.model.enums.Publisher;

public record FindAllBooksDto(

        String name,

        String author,

        Genre genre,

        Publisher publisher,

        String description,

        String pdfPath

        ) {

            public FindAllBooksDto(Books books) {

                this (books.getName(), books.getAuthor(), books.getGenre(),
                        books.getPublisher(), books.getDescription(), books.getPdfPath());

            }

        }
