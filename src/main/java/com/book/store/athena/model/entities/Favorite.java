package com.book.store.athena.model.entities;

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
    @JoinColumn(name = "user_id")
    private User users;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Books books;

    private Boolean active;

    public Favorite (User users, Books books) {

        this.users = users;

        this.books = books;

        this.active = true;

    }

    public void disable () {

        this.active = false;

    }

}
