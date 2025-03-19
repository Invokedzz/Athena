package com.book.store.athena.infra.aspect;

import com.book.store.athena.model.dto.books.UpdateBooksDto;
import com.book.store.athena.model.dto.client.UpdateUserDto;
import com.book.store.athena.services.AdminService;
import com.book.store.athena.services.BooksService;
import com.book.store.athena.services.FavoriteServices;
import com.book.store.athena.services.UserServices;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Aspect
@Component
public class GeneralAspect {

    private final FavoriteServices favoriteServices;

    private final UserServices userServices;

    private final BooksService booksService;

    public GeneralAspect(FavoriteServices favoriteServices, UserServices userServices, BooksService booksService) {

        this.favoriteServices = favoriteServices;

        this.userServices = userServices;

        this.booksService = booksService;

    }

    private void validateServiceExistence (Supplier<Object> service) {

        var book = service.get();

        if (book == null) {

            throw new NotFoundException("Service not found");

        }

    }

    @Before(value = "execution(* com.book.store.athena.controllers.UserController.*(..)) &&" +
            " args (id, updateUserDto)", argNames = "id, updateUserDto")
    public void invalidUserUpdate (Long id, UpdateUserDto updateUserDto) {

        validateServiceExistence(() -> userServices.update(id, updateUserDto));

    }

    @Before(value = "execution(* com.book.store.athena.controllers.BooksController.*(..)) &&" +
            " args (id, updateBooksDto)", argNames = "id, updateBooksDto")
    public void invalidBookUpdate (Long id, UpdateBooksDto updateBooksDto) {

        validateServiceExistence(() -> booksService.update(id, updateBooksDto));

    }

    @Before(value = "execution(* com.book.store.athena.controllers.*.*(..)) && args (id, ..)")
    public void invalidDisable (Long id) {

        List <Supplier<Object>> services = Arrays.asList(

                () -> booksService.disable(id),

                () -> userServices.disable(id),

                () -> favoriteServices.disable(id)

        );

        for (Supplier<Object> service : services) {

            validateServiceExistence(service);

        }

    }

    @Before(value = "execution(* com.book.store.athena.controllers.*.*(..)) && args (id, ..)")
    public void invalidReactivation (Long id) {

        List <Supplier<Object>> services = Arrays.asList(

                () -> booksService.reactivate(id),

                () -> userServices.reactivate(id),

                () -> favoriteServices.reactivate(id)

        );

        for (Supplier<Object> service : services) {

            validateServiceExistence(service);

        }

    }

}
