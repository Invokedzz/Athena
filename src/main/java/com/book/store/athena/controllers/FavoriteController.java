package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.favorite.FindAllFavoritesDto;
import com.book.store.athena.model.dto.favorite.RequestFavoriteDto;
import com.book.store.athena.services.FavoriteServices;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    private final FavoriteServices favoriteServices;

    public FavoriteController(FavoriteServices favoriteServices) {

        this.favoriteServices = favoriteServices;

    }

    @Transactional
    @PostMapping("/insert") // user
    protected ResponseEntity <Void> saveFavorite (@RequestBody RequestFavoriteDto requestFavoriteDto) {

        var favorite = favoriteServices.save(requestFavoriteDto.userId(), requestFavoriteDto.bookId());

        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @GetMapping("/display") // admin
    protected ResponseEntity <Set<FindAllFavoritesDto>> findAllFavoritesByActive () {

        var favorites = favoriteServices.findFavoriteByActive(true);

        return ResponseEntity.ok(favorites);

    }

    @Transactional
    @PutMapping("/reactivate/{id}") // user
    protected ResponseEntity <Void> reactivateFavorite (@PathVariable Long id) {

        var favorite = favoriteServices.reactivate(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @Transactional
    @DeleteMapping("/disable/{id}") // user
    protected ResponseEntity <Void> removeFavorite (@PathVariable Long id) {

        var favorite = favoriteServices.disable(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

}
