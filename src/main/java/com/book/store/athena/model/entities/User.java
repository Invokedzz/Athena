package com.book.store.athena.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String name;

    private String email;

    private String password;

    private LocalDate birthDate;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
    private List <Favorite> favoriteBooks;

}
