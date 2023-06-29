package org.learning.springPizzeriaCrud.model.repository;

import org.learning.springPizzeriaCrud.model.Offerta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OffertaRepository extends JpaRepository<Offerta, Integer> {
}
