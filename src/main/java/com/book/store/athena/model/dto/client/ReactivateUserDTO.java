package com.book.store.athena.model.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ReactivateUserDTO (

        @Email(message = "please, enter a proper e-mail you idiot!")
        @NotBlank(message = "please, don't leave the input blank")
        String email

    ) {}
