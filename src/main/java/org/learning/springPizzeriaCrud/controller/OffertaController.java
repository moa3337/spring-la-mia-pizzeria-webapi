package org.learning.springPizzeriaCrud.controller;

import org.learning.springPizzeriaCrud.model.Offerta;
import org.learning.springPizzeriaCrud.model.Pizza;
import org.learning.springPizzeriaCrud.model.repository.OffertaRepository;
import org.learning.springPizzeriaCrud.model.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/offers")
public class OffertaController {
    @Autowired
    PizzaRepository pizzaRepository;
    @Autowired
    OffertaRepository offertaRepository;

    @GetMapping("/create")
    public String create(@RequestParam("pizzaId") Integer pizzaId, Model model) {
        // Creo una nuova offerta
        Offerta offerta = new Offerta();
        // Precarico la data d'inizio con la data odierna
        offerta.setInizioOfferta(LocalDate.now());
        // Precarico la pizza con quella passata come parametro
        Optional<Pizza> pizza = pizzaRepository.findById(pizzaId);
        if (pizza.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "pizza con id " + pizzaId + " non trovata");
        }
        offerta.setPizza(pizza.get());
        // Aggiungo al model l'attributo con l'offerta
        model.addAttribute("offerta", offerta);
        return "offers/form";
    }
}
