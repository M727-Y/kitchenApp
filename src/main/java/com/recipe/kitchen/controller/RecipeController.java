//*********************************************************************************
// Project: < Kitchen >
//        * Assignment: < assignment #2 >
//        * Author(s): < author name Mikhail Yugay, Askar Bulabayev, Arnur Azangaliyev>
//        * Student Number: < student number 101312178, 101322619, 101322803 >
//        * Date:
//        * Description: <This file contains all the routes related to the recipes. Controller to recieve input, process and show view and model >
//        *********************************************************************************//
package com.recipe.kitchen.controller;

import com.recipe.kitchen.model.Ingridient;
import com.recipe.kitchen.model.Recipe;
import com.recipe.kitchen.model.User;
import com.recipe.kitchen.repository.IngridientRepository;
import com.recipe.kitchen.repository.UserRepository;
import com.recipe.kitchen.service.RecipeService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class RecipeController {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private IngridientRepository ingridientRepository;


    public RecipeController(RecipeService recipeService) {
        super();
        this.recipeService = recipeService;
    }

    // handler method to handle list recipes and return mode and view
    @GetMapping("/recipes")
    public String listRecipes(Model model) {
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "recipes";
    }

    @PostMapping("/search_recipe")
    public String findRecipe(@RequestParam("recipeId") Long id, Model model) {
        try{
            model.addAttribute("recipe", recipeService.getRecipeById(id));
            return "view_recipe";
        } catch (Exception ex){
            return "redirect:/recipes";
        }


    }

    @GetMapping("/recipes/new")
    public String createRecipeForm(@NotNull Model model) {

        // create recipe object to hold recipe form data
        model.addAttribute("recipe", new Recipe());
        return "create_recip";
    }
    @PostMapping("/process_recipe")
    public String processRecipe(Recipe recipe, Model model) {
//                                @RequestParam(value="ingridientss[]") Set<String> ingridients) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        User author;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();

        } else {
            username = principal.toString();
        }
        author = userRepo.findByEmail(username);
        recipe.setAuthor(author);

        recipeService.saveRecipe(recipe);
        model.addAttribute("recipe", recipe);
        model.addAttribute("ingridients", ingridientRepository.findAll());
        model.addAttribute("ingridient", new Ingridient());

        return "addIngridientToRecipe";
    }

    @PostMapping("/recipes")
    public String saveRecipe(@ModelAttribute("recipe") Recipe recipe) {
        recipeService.saveRecipe(recipe);
        return "redirect:/recipes";
    }

    @GetMapping("/recipes/edit/{id}")
    public String editRecipeForm(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.getRecipeById(id));
        return "view_recipe";
    }

    @PostMapping("/recipes/{id}")
    public String updaterecipe(@PathVariable Long id,
                               @ModelAttribute("recipe") Recipe recipe,
                               Model model) {

        // get recipe from database by id
        Recipe existingRecipe = recipeService.getRecipeById(id);
        existingRecipe.setId(id);
        existingRecipe.setName(recipe.getName());
        existingRecipe.setName(recipe.getName());

        // save updated recipe object
        recipeService.updateRecipe(existingRecipe);
        return "redirect:/recipes";
    }

    // handler method to handle delete recipe request

    @GetMapping("/recipes/{id}")
    public String deleterecipe(@PathVariable Long id) {
        recipeService.deleteRecipeById(id);
        return "redirect:/recipes";
    }
    @PostMapping("/process_recipe_add_ingridient")
    public String addIngridientToRecipe(Model model,Ingridient ingridient,@RequestParam("recipeId")Long recipeId){
        ingridientRepository.save(ingridient);
        Recipe recipe = recipeService.getRecipeById(recipeId);
        recipe.addIngridient(ingridient);
        recipeService.saveRecipe(recipe);
        model.addAttribute("ingridients", ingridientRepository.findAll());
        model.addAttribute("recipe",recipe);
        model.addAttribute("ingridient", new Ingridient());
        //model.addAttribute(recipeService.getAllRecipes());
        return "addIngridientToRecipe";
    }
    @PostMapping("/removeRecipe")
    public String removeRecipe(Model model,
                               @RequestParam("id") Long id){

        Recipe recipe = recipeService.getRecipeById(id);
        Set<User> users = recipe.getFavoriteUsers();
        for(User user:users){
            user.removeFromFavorites(recipe);
            userRepo.save(user);
        }
        recipeService.deleteRecipeById(id);
        User author = getCurrentUser();
        Set<Recipe> recipes = author.getMyRecipes();
        Set<Recipe> favorites = author.getFavoriteRecipes();
        model.addAttribute("recipes", recipes);
        model.addAttribute("favorites", favorites);
        return "main_menu";
    }
    @PostMapping("/updateRecipe")
    public String updateRecipe(Model model,
                               @RequestParam("id") Long id){

        Recipe recipe = recipeService.getRecipeById(id);
        model.addAttribute("recipe", recipe);
        return "updateRecipe";
    }
    @PostMapping("/updateRecipeContinue")
    public String updateRecipeContinue(Model model,
                                       @RequestParam("id") Long id,
                                       @RequestParam("name") String name,
                                       @RequestParam("description") String description,
                                       @RequestParam("recipeText") String recipeText ){

        Recipe recipe = recipeService.getRecipeById(id);
        recipe.setName(name);
        recipe.setDescription(description);
        recipe.setRecipeText(recipeText);
        recipeService.saveRecipe(recipe);
        model.addAttribute("recipe", recipe);
        model.addAttribute("ingridients", ingridientRepository.findAll());
        model.addAttribute("ingridient", new Ingridient());
        return "addIngridientToRecipe";
    }
    @PostMapping("/deleteIngridient")
    public String deleteIng(Model model,
                            @RequestParam("ingId") Long ingId,
                            @RequestParam("recipeId") Long recipeId){
        Recipe recipe = recipeService.getRecipeById(recipeId);
        recipe.deleteIngridient(ingridientRepository.getById(ingId));
        recipeService.saveRecipe(recipe);
        model.addAttribute("recipe", recipe);
        model.addAttribute("ingridients", ingridientRepository.findAll());
        model.addAttribute("ingridient", new Ingridient());
        return "addIngridientToRecipe";
    }
    @PostMapping("/addIngridient")
    public String addIng(Model model,
                            @RequestParam("ingId") Long ingId,
                            @RequestParam("recipeId") Long recipeId){
        Recipe recipe = recipeService.getRecipeById(recipeId);
        recipe.addIngridient(ingridientRepository.getById(ingId));
        recipeService.saveRecipe(recipe);
        model.addAttribute("recipe", recipe);
        model.addAttribute("ingridients", ingridientRepository.findAll());
        model.addAttribute("ingridient", new Ingridient());
        return "addIngridientToRecipe";
    }
    public User getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;
        User user;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        user = userRepo.findByEmail(username);
        return user;
    }
}