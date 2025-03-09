package com.book.store.athena.model.repository;

import com.book.store.athena.model.entities.Role;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@SuppressWarnings("NullableProblems")
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findById(Long id);

    @Modifying
    @Transactional
    @Query(value = "insert into user_roles (user_id, role_id) values (:user_id, :role_id)", nativeQuery = true)
    void insertUserRole (@Param("user_id") Long userId, @Param("role_id") Long roleId);

}
