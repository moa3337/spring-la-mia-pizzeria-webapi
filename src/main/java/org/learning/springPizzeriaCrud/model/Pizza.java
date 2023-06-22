package org.learning.springPizzeriaCrud.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pizzas")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false, unique = true)
    private String nome;
    @Column(nullable = false)
    private String descrizione;
    @Column(nullable = false)
    private String image;
    @Column(nullable = false)
    private Float prezzo;

    private LocalDateTime createdAt;
}
