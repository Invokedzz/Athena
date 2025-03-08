package com.book.store.athena.model.dto.client;

import com.book.store.athena.model.entities.Favorite;
import com.book.store.athena.model.entities.User;

import java.util.List;
import java.util.Set;

public record FindUserBooksByIdDto (Set <Favorite> favorites) {

    public FindUserBooksByIdDto (User user) {

        this (user.getFavoriteBooks());

    }

}
