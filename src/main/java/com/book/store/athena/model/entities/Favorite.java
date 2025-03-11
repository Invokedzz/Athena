package com.book.store.athena.model.entities;

import com.book.store.athena.model.dto.favorite.FindAllFavoritesDto;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode()
@Table(name = "favorites")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
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
