package com.book.store.athena.model.dto.client;

import com.book.store.athena.model.entities.User;

import java.time.LocalDate;

public record FindUserByIdDTO(Long userId, String username, String email, LocalDate birthDate) {

    public FindUserByIdDTO(User user) {

        this (user.getId(), user.getUsername(), user.getEmail(), user.getBirthDate());

    }
}
