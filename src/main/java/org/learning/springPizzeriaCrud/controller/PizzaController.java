package org.learning.springPizzeriaCrud.controller;

import org.learning.springPizzeriaCrud.model.Pizza;
import org.learning.springPizzeriaCrud.model.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {
    // Questo dipende da PizzaRepository
    @Autowired
    private PizzaRepository pizzaRepository;

    /*
        @GetMapping
        public String list(Model model) {
            // Recupero la lista di pizze dal db
            List<Pizza> pizzas = pizzaRepository.findAll();
            // Passo la lista di libri alla view
            model.addAttribute("pizzalist", pizzas);
            return "/pizzas/list";
        }*/
    @GetMapping
    public String list(
            @RequestParam(name = "keyword", required = false) String searchString,
            Model model) {
        List<Pizza> pizzas;
        if (searchString == null || searchString.isBlank()) {
            // Se non ho il parametro RequestParam faccio la query con filtro
            // Recupero la lista di pizze dal db
            pizzas = pizzaRepository.findAll();
        } else {
            // Se ho il parametro RequestParam faccio la query con filtro
            pizzas = pizzaRepository.findByNome(searchString);
        }
        
        // Passo la lista di libri alla view
        model.addAttribute("pizzalist", pizzas);
        return "/pizzas/list";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Integer pizzaId, Model model) {
        // Cerco dal db i dettagli di pizza tramite id
        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {
            // Passo la pizza alla view
            model.addAttribute("pizza", result.get());
            // Ritorno il nome del template della view
            return "/pizzas/detail";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "La pizza: " + pizzaId + "non trovata");
        }
    }
}
