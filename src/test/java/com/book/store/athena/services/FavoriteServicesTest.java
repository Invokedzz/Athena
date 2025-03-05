package com.book.store.athena.services;

import com.book.store.athena.model.dto.favorite.FindAllFavoritesDto;
import com.book.store.athena.model.entities.Books;
import com.book.store.athena.model.entities.Favorite;
import com.book.store.athena.model.entities.User;
import com.book.store.athena.model.repository.FavoriteRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class FavoriteServicesTest {

    @Autowired
    private FavoriteServices favoriteServices;

    @MockitoBean
    private FavoriteRepository favoriteRepository;

    @Test
    void findFavoriteByActive() {

        Mockito.when(favoriteRepository.findAllByActive(Mockito.anyBoolean())).thenReturn(List.of(new Favorite(new User(), new Books())));

        List <FindAllFavoritesDto> favoriteList = favoriteServices.findFavoriteByActive(true);

        Assertions.assertThat(favoriteList.size()).isEqualTo(1);

        Assertions.assertThat(favoriteList).hasSize(1).isNotNull();

    }

    @Test
    void saveBook() {
    }

    @Test
    void reactivateFavorite() {
    }

    @Test
    void disableFavorite() {
    }
}