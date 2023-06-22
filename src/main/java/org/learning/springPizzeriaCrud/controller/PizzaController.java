package org.learning.springPizzeriaCrud.controller;

import org.learning.springPizzeriaCrud.model.Pizza;
import org.learning.springPizzeriaCrud.model.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {
    // Questo dipende da PizzaRepository
    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String list(Model model) {
        // Recupero la lista di pizze dal db
        List<Pizza> pizzas = pizzaRepository.findAll();
        // Passo la lista di libri alla view
        model.addAttribute("pizzalist", pizzas);
        return "/pizzas/list";
    }
}
