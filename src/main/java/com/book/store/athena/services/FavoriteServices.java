package com.book.store.athena.services;

import com.book.store.athena.model.dto.favorite.FindAllFavoritesDto;
import com.book.store.athena.model.entities.Favorite;
import com.book.store.athena.model.repository.BooksRepository;
import com.book.store.athena.model.repository.FavoriteRepository;
import com.book.store.athena.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FavoriteServices {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BooksRepository booksRepository;

    public List <FindAllFavoritesDto> findFavoriteByActive (Boolean active) {

        return favoriteRepository.findAllByActive(active).stream().map(FindAllFavoritesDto::new).toList();

    }

    public void saveBook (Long userId, Long bookId) {

        var user = userRepository.findById(userId);

        var book = booksRepository.findById(bookId);

        if (book.isPresent() && user.isPresent()) {

            Favorite favorite = new Favorite(user.get(), book.get());

            favoriteRepository.save(favorite);

        }

    }

    public void disableBook (Long id) {

        var favorite = favoriteRepository.findById(id);

        if (favorite.isPresent()) {

            var obtainedFav = favorite.get();

            obtainedFav.disable();

        }

    }

}
