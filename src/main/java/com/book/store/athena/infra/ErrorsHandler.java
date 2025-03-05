package com.book.store.athena.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorsHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity <?> handle404 () {

        return ResponseEntity.notFound().build();

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity <?> handle400 (MethodArgumentNotValidException ex) {

        return ResponseEntity.badRequest().body(ex.getFieldErrors().stream().map(ErrorsHandlerDto::new).toList());

    }

}