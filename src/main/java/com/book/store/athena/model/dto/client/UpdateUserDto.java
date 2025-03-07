package com.book.store.athena.model.dto.client;

import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public record UpdateUserDto (

        @Length(min = 3, max = 21, message = "username input length must be between 3 to 21 characters")
        String username,

        @Email(message = "please, enter a valid e-mail!")
        String email,

        @Length(min = 6, max = 50, message = "password input length must be between 6 to 50 characters")
        String password

        ) {}
