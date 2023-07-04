package org.learning.springPizzeriaCrud.api;

import org.learning.springPizzeriaCrud.model.Pizza;
import org.learning.springPizzeriaCrud.model.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api/v1/pizzas")
public class PizzaRestController {
    // Controller per la risorsa pizza

    @Autowired
    private PizzaRepository pizzaRepository;

    // Servizio per avere la lista delle pizze
    @GetMapping
    public List<Pizza> index() {
        return pizzaRepository.findAll();
    }
}
