package org.learning.springPizzeriaCrud.model.repository;

import org.learning.springPizzeriaCrud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
    // Metodo per cercare le pizze tramite nome
    List<Pizza> findByNome(String nome);
}
