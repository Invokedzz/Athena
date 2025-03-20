package com.book.store.athena.model.repository;

import com.book.store.athena.model.entities.Books;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BooksRepository extends JpaRepository <Books, Long> {

    @Transactional
    @Query("SELECT f.books FROM Favorite f WHERE f.users.id = :userId AND f.active = true")
    List<Books> findFavoriteBooksByUserId (@Param("userId") Long userId);

    List <Books> findAllByActive (Boolean active);

}
