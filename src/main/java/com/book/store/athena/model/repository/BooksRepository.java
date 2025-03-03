package com.book.store.athena.model.repository;

import com.book.store.athena.model.entities.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository <Books, Long> {

    List<Books> findAllByActive (Boolean active);

}
