package com.book.store.athena.model.dto.admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record LoginAdminDto (

        @Email
        String email,

        @NotBlank
        @Length(min = 6, max = 50, message = "password input length must be between 6 to 50 characters")
        String password

    ) {}
