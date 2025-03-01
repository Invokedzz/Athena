package com.book.store.athena.model.entities;

import com.book.store.athena.model.dto.BooksDto;
import com.book.store.athena.model.enums.Genre;
import com.book.store.athena.model.enums.Publisher;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@Table(name = "stored_books")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String name;

    String author;

    @Enumerated(EnumType.STRING)
    Genre genre;

    @Column(name = "release_date")
    LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    Publisher publisher;

    String description;

    public Books (BooksDto booksDto) {

        this.name = booksDto.name();

        this.author = booksDto.author();

        this.genre = booksDto.genre();

        this.releaseDate = booksDto.releaseDate();

        this.publisher = booksDto.publisher();

        this.description = booksDto.description();

    }

}
