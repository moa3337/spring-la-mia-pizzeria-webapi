package org.learning.springPizzeriaCrud.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "offers")
public class Offerta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private LocalDate inizioOfferta;

    private LocalDate fineOfferta;

    private String nomeOfferta;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    // Attributo che contiene la relazione con pizza
    private Pizza pizza;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getInizioOfferta() {
        return inizioOfferta;
    }

    public void setInizioOfferta(LocalDate inizioOfferta) {
        this.inizioOfferta = inizioOfferta;
    }

    public LocalDate getFineOfferta() {
        return fineOfferta;
    }

    public void setFineOfferta(LocalDate fineOfferta) {
        this.fineOfferta = fineOfferta;
    }

    public String getNomeOfferta() {
        return nomeOfferta;
    }

    public void setNomeOfferta(String nomeOfferta) {
        this.nomeOfferta = nomeOfferta;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
}
