package com.book.store.athena.infra;

import org.springframework.validation.FieldError;

public record ErrorsHandlerDto (String field, String message) {

    public ErrorsHandlerDto (FieldError fieldError) {

        this (fieldError.getField(), fieldError.getDefaultMessage());

    }

}
