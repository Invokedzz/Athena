package com.book.store.athena.model.dto.books;

import com.book.store.athena.model.enums.Genre;
import com.book.store.athena.model.enums.Publisher;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record CreateBooksDto(

        @NotBlank
        @Length(min = 4, max = 50)
        String name,

        @NotBlank
        @Length(min = 4, max = 50)
        String author,

        @Enumerated
        Genre genre,

        @Past
        @JsonProperty("release-date")
        LocalDate releaseDate,

        @Enumerated
        Publisher publisher,

        @NotBlank
        @Length(max = 1025)
        String description

        ) {}
