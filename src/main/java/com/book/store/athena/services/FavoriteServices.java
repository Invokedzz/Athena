package com.book.store.athena.services;

import com.book.store.athena.exceptions.BadRequestException;
import com.book.store.athena.exceptions.NotFoundException;
import com.book.store.athena.model.dto.favorite.FindAllFavoritesDTO;
import com.book.store.athena.model.entities.Favorite;
import com.book.store.athena.model.repository.BooksRepository;
import com.book.store.athena.model.repository.FavoriteRepository;
import com.book.store.athena.model.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
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

    public Set <FindAllFavoritesDTO> findFavoriteByActive (Boolean active) {

        return favoriteRepository.findAllByActive(active).stream()
                .map(FindAllFavoritesDTO::new).collect(Collectors.toSet());

    }

    public void create (Long userId, Long bookId) {

        var user = userRepository.findById(userId);

        var book = booksRepository.findById(bookId);

        if (book.isPresent() && user.isPresent()) {

            Favorite favorite = new Favorite(user.get(), book.get());

            favoriteRepository.save(favorite);

        }

        verifyIfUserAndBookExists(user, book);

    }

    public void reactivate (Long id) {

        var favorite = favoriteRepository.findById(id);

        if (favorite.isPresent()) {

            var obtainedFav = favorite.get();

            obtainedFav.activate();

            favoriteRepository.save(obtainedFav);

        }

        verifyIfFavoriteRelationExists(favorite);

    }

    public void disable (Long id) {

        var favorite = favoriteRepository.findById(id);

        if (favorite.isPresent()) {

            var obtainedFav = favorite.get();

            obtainedFav.disable();

            favoriteRepository.save(obtainedFav);

        }

        verifyIfFavoriteRelationExists(favorite);

    }

    private void verifyIfFavoriteRelationExists (Optional <?> favorite) {

        if (favorite.isEmpty()) {

            throw new NotFoundException("Favorite relation does not exist");

        }

    }

    private void verifyIfUserAndBookExists (Optional <?> user, Optional <?> book) {

        if (user.isEmpty() || book.isEmpty()) {

            throw new BadRequestException("Unable to process request");

        }

    }

}
