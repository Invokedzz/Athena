package com.book.store.athena.model.repository;

import com.book.store.athena.model.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository <Favorite, Long> {

    List <Favorite> findAllByActive (Boolean active);

}
