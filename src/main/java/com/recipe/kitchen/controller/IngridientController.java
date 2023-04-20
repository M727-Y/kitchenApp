//*********************************************************************************
// Project: < Kitchen >
//        * Assignment: < assignment #2 >
//        * Author(s): < author name Mikhail Yugay, Askar Bulabayev, Arnur Azangaliyev>
//        * Student Number: < student number 101312178, 101322619, 101322803 >
//        * Date:
//        * Description: <Controller for Ingredients >
//        *********************************************************************************//
package com.recipe.kitchen.controller;

import com.recipe.kitchen.model.Ingridient;
import com.recipe.kitchen.model.Recipe;
import com.recipe.kitchen.model.User;
import com.recipe.kitchen.repository.IngridientRepository;
import com.recipe.kitchen.repository.RecipeRepository;
import com.recipe.kitchen.repository.UserRepository;
import com.recipe.kitchen.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;

@Controller
public class IngridientController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    IngridientRepository ingridientRepository;
    @Autowired
    RecipeService recipeService;

    @PostMapping("/add_ingridient")
    public String addIngridient(Model model, Ingridient ingridient){
        ingridientRepository.save(ingridient);
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        User author;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();

        } else {
            username = principal.toString();
        }
        author = userRepository.findByEmail(username);
        author.addToShoppingList(ingridient);
        userRepository.save(author);
        model.addAttribute("ingridients", ingridientRepository.findAll());
        model.addAttribute("ingridient", new Ingridient());
        model.addAttribute("user",author);
        //model.addAttribute(recipeService.getAllRecipes());
        return "shoppingList";
    }
}
