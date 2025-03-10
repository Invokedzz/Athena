package com.book.store.athena.model.repository;

import com.book.store.athena.model.entities.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BooksRepository extends JpaRepository <Books, Long> {

    Set <Books> findAllByActive (Boolean active);

}
