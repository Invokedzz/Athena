package com.book.store.athena.services;

import com.book.store.athena.model.dto.favorite.FindAllFavoritesDto;
import com.book.store.athena.model.entities.Favorite;
import com.book.store.athena.model.repository.BooksRepository;
import com.book.store.athena.model.repository.FavoriteRepository;
import com.book.store.athena.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FavoriteServices {

    private final FavoriteRepository favoriteRepository;

    private final UserRepository userRepository;

    private final BooksRepository booksRepository;

    public FavoriteServices (FavoriteRepository favoriteRepository, UserRepository userRepository,
                                BooksRepository booksRepository) {

        this.favoriteRepository = favoriteRepository;

        this.userRepository = userRepository;

        this.booksRepository = booksRepository;

    }

    public Set<FindAllFavoritesDto> findFavoriteByActive (Boolean active) {

        return favoriteRepository.findAllByActive(active).stream().map(FindAllFavoritesDto::new).collect(Collectors.toSet());

    }

    public Favorite saveBook (Long userId, Long bookId) {

        var user = userRepository.findById(userId);

        var book = booksRepository.findById(bookId);

        if (book.isPresent() && user.isPresent()) {

            Favorite favorite = new Favorite(user.get(), book.get());

            favoriteRepository.save(favorite);

            return favorite;

        }

        return null;

    }

    public Favorite reactivateFavorite (Long id) {

        var favorite = favoriteRepository.findById(id);

        if (favorite.isPresent()) {

            var obtainedFav = favorite.get();

            obtainedFav.activate();

            favoriteRepository.save(obtainedFav);

            return obtainedFav;

        }

        return null;

    }

    public Favorite disableFavorite (Long id) {

        var favorite = favoriteRepository.findById(id);

        if (favorite.isPresent()) {

            var obtainedFav = favorite.get();

            obtainedFav.disable();

            favoriteRepository.save(obtainedFav);

            return obtainedFav;

        }

        return null;

    }

}
