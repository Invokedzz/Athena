package com.book.store.athena.infra;

import com.book.store.athena.exceptions.AgeRestrictionException;
import com.book.store.athena.exceptions.NotFoundException;
import com.book.store.athena.model.dto.errors.ErrorDTO;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorsHandler {

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AgeRestrictionException.class)
    public ErrorDTO handleAgeRestrictionException(AgeRestrictionException e) {

        return new ErrorDTO(e.getMessage());

    }

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorDTO handleNotFoundException(NotFoundException e) {

        return new ErrorDTO(e.getMessage());

    }

}