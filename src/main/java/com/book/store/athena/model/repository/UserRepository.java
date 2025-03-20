package com.book.store.athena.model.repository;

import com.book.store.athena.model.entities.Books;
import com.book.store.athena.model.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {

    UserDetails findUserByName (String name);

    @Query(value = """
        SELECT u.id, u.username, u.email, u.password, u.birth_date, u.active
        FROM users u
        JOIN user_roles ur ON u.id = ur.user_id
        JOIN roles r ON ur.role_id = r.id
        WHERE u.active = :active AND r.name = :name
        """, nativeQuery = true)
    List<User> findAllUsersByActive (@Param("active") Boolean active, @Param("name") String name);

    @Query(value = """
        SELECT u.id, u.username, u.email, u.password, u.birth_date, u.active
        FROM users u
        JOIN user_roles ur ON u.id = ur.user_id
        JOIN roles r ON ur.role_id = r.id
        WHERE u.id = :id AND u.active = :active AND r.name = :name
        """, nativeQuery = true)
    Optional <User> findUserAccordingToRole (@Param("id") Long id,
                                      @Param("active") Boolean active,
                                      @Param("name") String name);

}
