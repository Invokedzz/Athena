package com.book.store.athena.model.dto.client;

import com.book.store.athena.model.entities.Books;
import com.book.store.athena.model.entities.Favorite;

import java.util.*;
import java.util.stream.Collectors;

public record FindUserBooksByIdDto (Set <Books> books) {


    public FindUserBooksByIdDto(Books books) {

        this(Set.of(books));

    }

}
