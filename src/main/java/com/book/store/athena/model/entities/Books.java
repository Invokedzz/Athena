package com.book.store.athena.model.entities;

import com.book.store.athena.model.dto.books.CreateBooksDto;
import com.book.store.athena.model.dto.books.UpdateBooksDto;
import com.book.store.athena.model.enums.Genre;
import com.book.store.athena.model.enums.Publisher;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stored_books")
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String author;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Enumerated(EnumType.STRING)
    private Publisher publisher;

    private String description;

    @Column(name = "pdf")
    private String pdfPath;

    private Boolean active;

    public Books (CreateBooksDto booksDto) {

        this.active = true;

        this.name = booksDto.name();

        this.author = booksDto.author();

        this.genre = booksDto.genre();

        this.releaseDate = booksDto.releaseDate();

        this.publisher = booksDto.publisher();

        this.description = booksDto.description();

        this.pdfPath = booksDto.pdfPath();

    }

    public void activate () {

        this.active = true;

    }

    public void inactive () {

        this.active = false;

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

        if (booksDto.pdfPath() != null) {

            this.pdfPath = booksDto.pdfPath();

        }


    }

}
