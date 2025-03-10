package com.book.store.athena.model.dto.client;

import com.book.store.athena.model.entities.Books;
import com.book.store.athena.model.entities.Favorite;
import com.book.store.athena.model.entities.User;
import org.antlr.v4.runtime.misc.OrderedHashSet;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public record FindUserBooksByIdDto (Books books, User user) {

    public FindUserBooksByIdDto (Favorite favorite) {

        this (favorite.getBooks(), favorite.getUsers());

    }

}
