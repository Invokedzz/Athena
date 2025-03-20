package com.book.store.athena.model.dto.favorite;

import com.book.store.athena.model.entities.Favorite;

public record FindAllFavoritesDTO(String email, String name, String pdfPath) {

    public FindAllFavoritesDTO(Favorite favorite) {

        this (favorite.getUsers().getEmail(), favorite.getBooks().getName(), favorite.getBooks().getPdfPath());

    }

}
