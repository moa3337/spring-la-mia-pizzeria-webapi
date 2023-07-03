package org.learning.springPizzeriaCrud.model.repository;

import org.learning.springPizzeriaCrud.model.Ingredients;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredients, Integer> {
}
