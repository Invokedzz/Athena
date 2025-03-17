package com.book.store.athena.model.repository;

import com.book.store.athena.model.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminRepository extends JpaRepository <Admin, Long> {

    List<Admin> findAdminById (Long id);

}
