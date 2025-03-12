package com.book.store.athena.model.dto.client;

import com.book.store.athena.model.entities.Books;
import java.util.Set;

public record FindUserBooksByIdDto (Set <Books> books) {

    public FindUserBooksByIdDto(Books books) {

        this(Set.of(books));

    }

}
