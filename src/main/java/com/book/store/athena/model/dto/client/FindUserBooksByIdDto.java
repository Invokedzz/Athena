package com.book.store.athena.model.dto.client;

import com.book.store.athena.model.entities.Books;
import com.book.store.athena.model.entities.Favorite;
import com.book.store.athena.model.entities.User;

import java.util.List;

public record FindUserBooksByIdDto (List <Favorite> favorites) {

    public FindUserBooksByIdDto (User user) {

        this (user.getFavoriteBooks());

    }

}
