package com.book.store.athena.model.entities;

import com.book.store.athena.model.dto.favorite.FindAllFavoritesDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "favorites")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_id")
    private User users;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Books books;

    private Boolean active;

    public Favorite (User users, Books books) {

        this.active = true;

        this.users = users;

        this.books = books;

    }

    public void activate () {

        this.active = true;

    }

    public void disable () {

        this.active = false;

    }

}
