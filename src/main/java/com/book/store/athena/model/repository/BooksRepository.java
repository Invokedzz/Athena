package com.book.store.athena.model.repository;

import com.book.store.athena.model.entities.Books;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BooksRepository extends JpaRepository <Books, Long> {}
