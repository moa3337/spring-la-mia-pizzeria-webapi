package org.learning.springPizzeriaCrud.controller;

import jakarta.validation.Valid;
import org.learning.springPizzeriaCrud.model.Ingredients;
import org.learning.springPizzeriaCrud.model.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping
    public String index(Model model, RequestMapping("edit") Optional<Integer> ingredientId) {
        // Recupero da db tutti gli ingredienti
        List<Ingredients> ingredientsList = ingredientRepository.findAll();
        // Passo al model un attributo Ingredients con tutti gli ingredienti
        model.addAttribute("ingredients", ingredientsList);
        // Passo al model un attributo ingredientObj per mappare il form sun un ogetto Ingredients
        Ingredients ingredientObj;
        // Se ho il parametro IngredientsId allora cerco l'ingrediente su db
        if (ingredientId)
        return "/ingredients/index";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("ingredientObj") Ingredients formIngredient,
                       BindingResult bindingResult, Model model) {
        // Verifico se ci sono errori
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredients", ingredientRepository.findAll());
            return "/ingredients/index";
        }
        // Salvo l'ingrediente
        ingredientRepository.save(formIngredient);
        // Redirect alla index
        return "redirect:/ingredients";
    }
}
