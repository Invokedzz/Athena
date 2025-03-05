package com.book.store.athena.model.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;

public record RegisterUserDto (

        @NotBlank
        @Length(min = 3, max = 21, message = "username input length must be between 3 to 21 characters")
        String username,

        @Email(message = "please, enter a valid e-mail!")
        String email,

        @NotBlank
        @Length(min = 6, max = 50, message = "password input length must be between 6 to 50 characters")
        String password,

        @Past(message = "please, enter a valid birth date!")
        LocalDate birthDate

        ) {}
