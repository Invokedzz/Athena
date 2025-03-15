package com.book.store.athena.model.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record UpdateAdminDto (

        @Email
        String email,

        @Length(min = 3, max = 21, message = "username input length must be between 3 to 21 characters")
        String username,

        @Length(min = 6, max = 50, message = "password input length must be between 6 to 50 characters")
        String password

    ) {}
