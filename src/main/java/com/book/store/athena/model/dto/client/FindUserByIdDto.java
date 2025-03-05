package com.book.store.athena.model.dto.client;

import com.book.store.athena.model.entities.User;

import java.time.LocalDate;

public record FindUserByIdDto (Long userId, String username, String email, LocalDate birthDate) {

    public FindUserByIdDto (User user) {

        this (user.getId(), user.getName(), user.getEmail(), user.getBirthDate());

    }

}
