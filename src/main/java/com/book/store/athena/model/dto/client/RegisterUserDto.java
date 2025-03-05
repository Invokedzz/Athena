package com.book.store.athena.model.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.Length;

public record RegisterUserDto (

        @NotBlank(message = "username can't be blank!")
        @Length(min = 3, max = 21, message = "username length must have 3 to 21 characters!")
        String username,

        @Email(message = "please, enter a valid e-mail!")
        String email,

        @Past(message = "please, enter a valid birth date!")
        String birthDate

        ) {}
