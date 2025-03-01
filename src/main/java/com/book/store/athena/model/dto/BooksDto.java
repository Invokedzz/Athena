package com.book.store.athena.model.dto;

import com.book.store.athena.model.enums.Genre;
import com.book.store.athena.model.enums.Publisher;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public record BooksDto (

        String name,

        String author,

        Genre genre,

        @JsonProperty("release-date")

        LocalDate releaseDate,

        Publisher publisher

        ) {}
