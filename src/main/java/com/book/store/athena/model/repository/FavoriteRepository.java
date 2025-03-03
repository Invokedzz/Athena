package com.book.store.athena.model.repository;

import com.book.store.athena.model.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {}
