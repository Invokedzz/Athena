package com.book.store.athena.model.repository;

import com.book.store.athena.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@SuppressWarnings("NullableProblems")
public interface UserRepository extends JpaRepository <User, Long> {

    Optional <User> findById (Long id);

    Set <User> findUserById (Long id);

    UserDetails findUserByName (String name);

    Set<User> findAllByActive(Boolean active);

}
