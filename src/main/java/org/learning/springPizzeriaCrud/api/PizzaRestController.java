package org.learning.springPizzeriaCrud.api;

import org.learning.springPizzeriaCrud.model.Pizza;
import org.learning.springPizzeriaCrud.model.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    // Servizio per il dettaglio della singola pizza
    @GetMapping("/{id}")
    public Pizza get(@PathVariable Integer id) {
        // Cerco la pizza per id su db
        Optional<Pizza> pizza = pizzaRepository.findById(id);
        if (pizza.isPresent()) {
            return pizza.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    // Servizio per creare una nuova pizza
    @PostMapping
    public Pizza create(@RequestBody Pizza pizza) {
        return pizzaRepository.save(pizza);
    }

    // Servizio per cancellare una nuova pizza
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        pizzaRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public Pizza update(@PathVariable Integer id, @RequestBody Pizza pizza) {
        pizza.setId(id);
        return pizzaRepository.save(pizza);
    }
}
