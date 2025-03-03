package com.book.store.athena.model.dto.books;

import org.hibernate.validator.constraints.Length;

public record UpdateBooksDto (

        @Length(min = 4, max = 50)
        String name,

        @Length(min = 4, max = 50)
        String author,

        @Length(max = 1025)
        String description

        ) {}
