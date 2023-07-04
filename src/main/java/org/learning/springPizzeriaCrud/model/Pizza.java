package org.learning.springPizzeriaCrud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pizzas")
public class Pizza {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Il nome della pizza è obligatorio")
    @Size(min = 8, max = 100)
    @Column(nullable = false)
    private String nome;
    @NotBlank(message = "La descrizione della pizza è obligatoria")
    @Lob
    @Column(nullable = false)
    private String descrizione;
    @Column(nullable = false)
    private String image;
    @DecimalMin(value = "0.0", inclusive = false)
    @NotNull(message = "Il prezzo è obbligatorio")
    @Column(nullable = false)
    private BigDecimal prezzo;

    private LocalDateTime createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "pizza", cascade = {CascadeType.REMOVE})
    // Relazione con le offerte
    private List<Offerta> offertaList = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Offerta> getOffertaList() {
        return offertaList;
    }

    public void setOffertaList(List<Offerta> offertaList) {
        this.offertaList = offertaList;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
