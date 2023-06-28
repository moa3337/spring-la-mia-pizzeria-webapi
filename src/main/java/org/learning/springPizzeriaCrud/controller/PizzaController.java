package org.learning.springPizzeriaCrud.controller;

import jakarta.validation.Valid;
import org.learning.springPizzeriaCrud.model.Pizza;
import org.learning.springPizzeriaCrud.model.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
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
        public String list(Model model){            // Recupero la lista di pizze dal db
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
        /*// Cerco dal db i dettagli di pizza tramite id
        Optional<Pizza> result = pizzaRepository.findById(pizzaId);
        if (result.isPresent()) {
            // Passo la pizza alla view
            model.addAttribute("pizza", result.get());
            // Ritorno il nome del template della view
            return "/pizzas/detail";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "La pizza: " + pizzaId + "non trovata");
        }*/
        Pizza pizza = getPizzaById(pizzaId);
        // Passo la pizza alla view
        model.addAttribute("pizza", pizza);
        // Ritorno il nome del template della view
        return "/pizzas/detail";
    }

    // Controller che ritorna il form per creazione pizza
    @GetMapping("/create")
    public String create(Model model) {
        // Aggiungi al model un attributo pizza contenente una pizza vuota
        model.addAttribute("pizza", new Pizza());
        // Form per creazione nuova pizza
        return "/pizzas/edit"; // Ritorno il form unico create/edit
    }

    // Controller per gestire la post del form con i dati di pizza
    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        // I dati di pizza sono dentro all'oggetto formPizza
        // Verifico in validazione se ci sono stati errori
        if (bindingResult.hasErrors()) {
            // Se ci sono stati errori
            //return "/pizzas/edit"; // Ritorno il form unico create/edit
            return "/pizzas/edit"; // Ritorno il form unico create/edit
        }
        // Setto il timestamp di creazione
        formPizza.setCreatedAt(LocalDateTime.now());
        // Persisto formPizza su db
        pizzaRepository.save(formPizza);
        // Se tutto va bene rimando alla lista delle pizze
        return "redirect:/pizzas";
    }

    // Metodo per l'edit
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        /*// Verifico se esiste quel pizza id
        Optional<Pizza> result = pizzaRepository.findById(id);
        // Se no ritorno un error 404
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza con id " + id + "non trovata");
        }*/
        Pizza pizza = getPizzaById(id);
        // Cerco i dati di quella pizza sul db
        // Aggiungo la pizza al model
        model.addAttribute("pizza", pizza);
        // Ritorno il template form edit
        return "/pizzas/edit";
    }

    @PostMapping("/edit/{id}")
    public String doEdit(
            @PathVariable Integer id,
            @Valid @ModelAttribute("pizza") Pizza formPizza,
            BindingResult bindingResult
    ) {
        // Cerco la pizza per id
        Pizza pizzaToEdit = getPizzaById(id); // Vecchia versione di pizza
        // La nuova verisone di pizza è formPizza
        // Valido il
        if (bindingResult.hasErrors()) {
            // Se ci sono errori ritorno il form
            return "/pizzas/edit";
        }
        // Trasferisco su formPizza i valori dei campi non presenti nel form
        formPizza.setId(pizzaToEdit.getId());
        formPizza.setCreatedAt(pizzaToEdit.getCreatedAt());
        // Salvo idati
        pizzaRepository.save(formPizza);
        return "redirect:/pizzas";
    }

    // Metodo per la delete
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        // Verifo che esista quel id pizza
        Pizza pizzaToDelete = getPizzaById(id);
        // Lo cancello
        pizzaRepository.delete(pizzaToDelete);
        // Aggiungo messaggio successo come flashAttribute
        redirectAttributes.addFlashAttribute(
                "message",
                "Pizza" + pizzaToDelete.getNome() + "eliminata con successo"
        );
        // Redirect alla lista pizze
        return "redirect:/pizzas";
    }

    private Pizza getPizzaById(Integer id) {
        // Verifico se esiste quel pizza id
        Optional<Pizza> result = pizzaRepository.findById(id);
        // Se no ritorno un error 404
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza con id " + id + "non trovata");
        }
        return result.get();
    }
}
