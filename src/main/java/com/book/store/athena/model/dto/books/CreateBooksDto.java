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
        @Length(min = 4, max = 50, message = "name input length must be between 4 to 50 characters")
        String name,

        @NotBlank
        @Length(min = 4, max = 50, message = "author input length must be between 4 to 50 characters")
        String author,

        @Enumerated
        Genre genre,

        @Past
        @JsonProperty("release-date")
        LocalDate releaseDate,

        @Enumerated
        Publisher publisher,

        @NotBlank
        @Length(max = 1025, message = "description input must be equal or lower than 1025 characters")
        String description,

        @NotBlank
        @Length(max = 200, message = "pdf path must be equal or lower than 200 characters")
        String pdfPath

        ) {}
