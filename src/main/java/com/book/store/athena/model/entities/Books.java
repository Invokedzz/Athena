package com.book.store.athena.model.entities;

import com.book.store.athena.model.dto.CreateBooksDto;
import com.book.store.athena.model.dto.UpdateBooksDto;
import com.book.store.athena.model.enums.Genre;
import com.book.store.athena.model.enums.Publisher;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
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

    public Books (CreateBooksDto booksDto) {

        this.name = booksDto.name();

        this.author = booksDto.author();

        this.genre = booksDto.genre();

        this.releaseDate = booksDto.releaseDate();

        this.publisher = booksDto.publisher();

        this.description = booksDto.description();

    }

    public void updateBooks (@Valid UpdateBooksDto booksDto) {

        if (booksDto.name() != null) {

            this.name = booksDto.name();

        }

        if (booksDto.author() != null) {

            this.author = booksDto.author();

        }

        if (booksDto.description() != null) {

            this.description = booksDto.description();

        }

    }

}
