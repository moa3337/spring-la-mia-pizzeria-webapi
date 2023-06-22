package org.learning.springPizzeriaCrud.model.repository;

import org.learning.springPizzeriaCrud.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {
}
