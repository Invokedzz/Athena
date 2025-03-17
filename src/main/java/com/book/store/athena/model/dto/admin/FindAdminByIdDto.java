package com.book.store.athena.model.dto.admin;

import com.book.store.athena.model.entities.Admin;

public record FindAdminByIdDto (Long adminId, String name, String email) {

    public FindAdminByIdDto (Admin admin) {

        this (admin.getId(), admin.getName(), admin.getEmail());

    }

}
