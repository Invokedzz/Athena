package com.book.store.athena.infra.aspect;

import com.book.store.athena.model.dto.favorite.RequestFavoriteDto;
import com.book.store.athena.services.FavoriteServices;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Supplier;

@Aspect
@Component
public class FavoriteAspect {

    private final FavoriteServices favoriteServices;

    public FavoriteAspect(FavoriteServices favoriteServices) {

        this.favoriteServices = favoriteServices;

    }

    private void validateServiceExistence (Supplier <Object> service) {

        var list = service.get();

        if (list == null) {

            throw new NotFoundException("Service not found");

        }

    }

    @Before(value = "execution(* com.book.store.athena.controllers.FavoriteController.*(..)) &&" +
            " args (requestFavoriteDto)", argNames = "requestFavoriteDto")
    public void invalidUserToBookRelation (RequestFavoriteDto requestFavoriteDto) {

        Long userId = requestFavoriteDto.userId();

        Long bookId = requestFavoriteDto.bookId();

        List <Supplier<Object>> favorites = List.of(

                () -> favoriteServices.save(userId, bookId)

        );

        for (Supplier<Object> favorite : favorites) {

            validateServiceExistence(favorite);

        }

    }

}
