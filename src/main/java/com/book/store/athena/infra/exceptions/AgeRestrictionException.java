package com.book.store.athena.infra.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AgeRestrictionException extends RuntimeException {
    public AgeRestrictionException(String message) {
        super(message);
    }
}
