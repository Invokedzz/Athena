package com.book.store.athena.model.dto.favorite;

import com.book.store.athena.model.entities.Favorite;

public record FindAllFavoritesDto (String email, String name, String pdfPath) {

    public FindAllFavoritesDto (Favorite favorite) {

        this (favorite.getUsers().getEmail(), favorite.getBooks().getName(), favorite.getBooks().getPdfPath());

    }

}
