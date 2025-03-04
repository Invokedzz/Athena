package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.favorite.FindAllFavoritesDto;
import com.book.store.athena.model.dto.favorite.RequestFavoriteDto;
import com.book.store.athena.model.entities.Favorite;
import com.book.store.athena.model.repository.FavoriteRepository;
import com.book.store.athena.services.FavoriteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteServices favoriteServices;

    @PostMapping("/insert")
    protected ResponseEntity <Void> saveFavorite (@RequestBody RequestFavoriteDto requestFavoriteDto) {

        favoriteServices.saveBook(requestFavoriteDto.userId(), requestFavoriteDto.bookId());

        return ResponseEntity.ok().build();

    }

    @GetMapping("/display")
    protected ResponseEntity <List<FindAllFavoritesDto>> findAllFavoritesByActive () {

        var favorites = favoriteServices.findFavoriteByActive(true);

        return ResponseEntity.ok(favorites);

    }

    @DeleteMapping("/disable/{id}")
    protected ResponseEntity <Void> removeFavorite (@PathVariable Long id) {

        favoriteServices.disableBook(id);

        return ResponseEntity.noContent().build();

    }

}
