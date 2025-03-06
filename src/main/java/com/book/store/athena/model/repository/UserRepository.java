package com.book.store.athena.model.repository;

import com.book.store.athena.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@SuppressWarnings("NullableProblems")
public interface UserRepository extends JpaRepository <User, Long> {

    Optional <User> findById (Long id);

    List <User> findUserById (Long id);

    UserDetails findUserByName(String name);

}
