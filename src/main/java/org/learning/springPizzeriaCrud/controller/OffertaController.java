package org.learning.springPizzeriaCrud.controller;

import jakarta.validation.Valid;
import org.learning.springPizzeriaCrud.model.Offerta;
import org.learning.springPizzeriaCrud.model.Pizza;
import org.learning.springPizzeriaCrud.model.repository.OffertaRepository;
import org.learning.springPizzeriaCrud.model.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
        //model.addAttribute("pizzaId", pizzaId);
        return "offers/form";
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("offerta") Offerta formOfferta,
                           BindingResult bindingResult) {
        // Valido
        if (bindingResult.hasErrors()) {
            // Se ci sono errori ricreo il form
            return "offers/form";
        }
        // Se non ci sono errori salvo l'offerta
        offertaRepository.save(formOfferta);
        // Redirect alla pagina di dettagli di quella pizza
        return "redirect:/pizzas/" + formOfferta.getPizza().getId();
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        // Cerco l'offerta sul db
        Optional<Offerta> offerta = offertaRepository.findById(id);
        if (offerta.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        // Passo l'offerta al model
        model.addAttribute("offerta", offerta.get());
        return "offers/form";
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id,
                         @Valid @ModelAttribute("offerta") Offerta formOfferta, BindingResult bindingResult) {
        // Verifico che esista l'offerta da modificare
        Optional<Offerta> offertaToEdit = offertaRepository.findById(id);
        if (offertaToEdit.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // Setto l'id dell'offerta al formOfferta
        formOfferta.setId(id);
        // Salvo il formOfferta su db (UPDATE)
        offertaRepository.save(formOfferta);
        // Redirect alla detail della pizza
        return "redirect:/pizzas/" + formOfferta.getPizza().getId();
    }

    @PostMapping("/delete{id}")
    public String delete(@PathVariable Integer id) {
        // Verifico che esista l'offerta da modificare
        Optional<Offerta> offertaToDelete = offertaRepository.findById(id);
        if (offertaToDelete.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // Se esiste lo cancello
        offertaRepository.delete(offertaToDelete.get());
        // Redirect alla detail della pizza
        return "redirect:/pizzas/" + offertaToDelete.get().getPizza().getId();
    }
}
