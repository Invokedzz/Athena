package com.book.store.athena.model.dto;

import com.book.store.athena.model.entities.Books;
import com.book.store.athena.model.enums.Genre;
import com.book.store.athena.model.enums.Publisher;

public record BooksCollectionDto (

        String name,

        String author,

        Genre genre,

        Publisher publisher,

        String description

        ) {

            public BooksCollectionDto (Books books) {

                this (books.getName(), books.getAuthor(), books.getGenre(),
                        books.getPublisher(), books.getDescription());

            }

        }
