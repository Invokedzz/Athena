package com.book.store.athena.infra.aspect;

import com.book.store.athena.model.dto.books.UpdateBooksDto;
import com.book.store.athena.services.BooksService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Aspect
@Component
public class BookAspect {

    private final BooksService booksService;

    public BookAspect (BooksService booksService) {

        this.booksService = booksService;

    }

    private void validateServiceExistence (Supplier<Object> service) {

        var user = service.get();

        if (user == null) {

            throw new NotFoundException("Service not found");

        }

    }

    @Before(value = "execution(* com.book.store.athena.controllers.BooksController.*(..)) &&" +
            " args (id, updateBooksDto)", argNames = "id, updateBooksDto")
    public void invalidUpdate (Long id, UpdateBooksDto updateBooksDto) {

        validateServiceExistence(() -> booksService.updateById(id, updateBooksDto));

    }

    @Before(value = "execution(* com.book.store.athena.controllers.BooksController.*(..)) && args (id, ..)")
    public void invalidDisable (Long id) {

        validateServiceExistence(() -> booksService.disableById(id));

    }

    @Before(value = "execution(* com.book.store.athena.controllers.BooksController.*(..)) && args (id, ..)")
    public void invalidReactivation (Long id) {

        validateServiceExistence(() -> booksService.reactivateById(id));

    }

}
