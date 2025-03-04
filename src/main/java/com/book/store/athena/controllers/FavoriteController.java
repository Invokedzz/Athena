package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.client.FindUserBooksByIdDto;
import com.book.store.athena.model.dto.favorite.FindAllFavoritesDto;
import com.book.store.athena.model.dto.favorite.RequestFavoriteDto;
import com.book.store.athena.model.repository.UserRepository;
import com.book.store.athena.services.FavoriteServices;
import com.book.store.athena.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteServices favoriteServices;

    @Autowired
    private UserServices userServices;

    @PostMapping("/insert")
    protected ResponseEntity <Void> saveFavorite (@RequestBody RequestFavoriteDto requestFavoriteDto) {

        favoriteServices.saveBook(requestFavoriteDto.userId(), requestFavoriteDto.bookId());

        return ResponseEntity.ok().build();

    }

    @GetMapping("/display/{id}")
    protected ResponseEntity<List<FindUserBooksByIdDto>>findAllFavorites (@PathVariable Long id) {

        var favorites = userServices.findUserBooksById(id);

        return ResponseEntity.ok(favorites);

    }

    @GetMapping("/display")
    protected ResponseEntity <List<FindAllFavoritesDto>> findAllFavoritesByActive () {

        var favorites = favoriteServices.findFavoriteByActive(true);

        return ResponseEntity.ok(favorites);

    }

    @PutMapping("/reactivate/{id}")
    protected ResponseEntity <Void> reactivateFavorite (@PathVariable Long id) {

        favoriteServices.reactivateFavorite(id);

        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/disable/{id}")
    protected ResponseEntity <Void> removeFavorite (@PathVariable Long id) {

        favoriteServices.disableFavorite(id);

        return ResponseEntity.noContent().build();

    }

}
