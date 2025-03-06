package com.book.store.athena.controllers;

import com.book.store.athena.model.dto.favorite.FindAllFavoritesDto;
import com.book.store.athena.model.dto.favorite.RequestFavoriteDto;
import com.book.store.athena.services.FavoriteServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteServices favoriteServices;

    @Transactional
    @PostMapping("/insert")
    protected ResponseEntity <Void> saveFavorite (@RequestBody RequestFavoriteDto requestFavoriteDto) {

        var favorite = favoriteServices.saveBook(requestFavoriteDto.userId(), requestFavoriteDto.bookId());

        if (favorite == null) return ResponseEntity.notFound().build();

        return ResponseEntity.ok().build();

    }

    @GetMapping("/display")
    protected ResponseEntity <List<FindAllFavoritesDto>> findAllFavoritesByActive () {

        var favorites = favoriteServices.findFavoriteByActive(true);

        return ResponseEntity.ok(favorites);

    }

    @Transactional
    @PutMapping("/reactivate/{id}")
    protected ResponseEntity <Void> reactivateFavorite (@PathVariable Long id) {

        var favorite = favoriteServices.reactivateFavorite(id);

        if (favorite == null) return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();

    }

    @Transactional
    @DeleteMapping("/disable/{id}")
    protected ResponseEntity <Void> removeFavorite (@PathVariable Long id) {

        var favorite = favoriteServices.disableFavorite(id);

        if (favorite == null) return ResponseEntity.notFound().build();

        return ResponseEntity.noContent().build();

    }

}
