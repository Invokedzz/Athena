package com.book.store.athena.model.dto.client;

import com.book.store.athena.model.entities.User;

import java.time.LocalDate;

public record FindAllActiveUsersDTO(Long id, String username, String email, LocalDate birthDate) {

    public FindAllActiveUsersDTO(User user) {

        this (user.getId(), user.getName(), user.getEmail(), user.getBirthDate());

    }

}
