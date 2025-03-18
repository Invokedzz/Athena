package com.book.store.athena.model.dto.admin;

import com.book.store.athena.model.entities.User;

public record FindAdminByIdDto (Long adminId, String name, String email) {

    public FindAdminByIdDto (User admin) {

        this (admin.getId(), admin.getName(), admin.getEmail());

    }

}
