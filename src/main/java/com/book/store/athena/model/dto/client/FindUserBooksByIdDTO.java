package com.book.store.athena.model.dto.client;

import com.book.store.athena.model.entities.Books;
import java.util.Set;

public record FindUserBooksByIdDTO(Set <Books> books) {

    public FindUserBooksByIdDTO(Books books) {

        this(Set.of(books));

    }

}
