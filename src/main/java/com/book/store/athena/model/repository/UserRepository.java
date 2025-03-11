package com.book.store.athena.model.repository;

import com.book.store.athena.model.entities.Books;
import com.book.store.athena.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    @Query("SELECT f.books FROM Favorite f WHERE f.users.id = :userId AND f.active = true")
    List<Books> findFavoriteBooksByUserId(@Param("userId") Long userId);

    UserDetails findUserByName (String name);

    List <User> findAllByActive (Boolean active);

}
