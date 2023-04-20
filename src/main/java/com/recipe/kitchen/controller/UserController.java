//*********************************************************************************
// Project: < Kitchen >
//        * Assignment: < assignment #2 >
//        * Author(s): < author name Mikhail Yugay, Askar Bulabayev, Arnur Azangaliyev>
//        * Student Number: < student number 101312178, 101322619, 101322803 >
//        * Date:
//        * Description: <This file contains all the routes related to the application. Controller to recieve input, process and show view and model >
//        *********************************************************************************//
package com.recipe.kitchen.controller;

import com.recipe.kitchen.model.Event;
import com.recipe.kitchen.model.Ingridient;
import com.recipe.kitchen.model.Recipe;
import com.recipe.kitchen.model.User;
import com.recipe.kitchen.repository.EventRepository;
import com.recipe.kitchen.repository.IngridientRepository;
import com.recipe.kitchen.repository.UserRepository;
import com.recipe.kitchen.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.Console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private IngridientRepository ingridientRepository;
    @Autowired
    private EventRepository eventRepocitory;

    @GetMapping("/profile")
    public String viewProfile(Model model) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();


        model.addAttribute("user", userRepo.findByEmail(((UserDetails)principal).getUsername()));
        model.addAttribute("edit", false);

        return "profile";
    }

    @GetMapping("/main")
    public String mainPage(Model model) {
        User author = getCurrentUser();
        Set<Recipe> recipes = author.getMyRecipes();
        Set<Recipe> favorites = author.getFavoriteRecipes();
        Set<Event> events = author.getMyEvents();
        model.addAttribute("events", events);
        model.addAttribute("recipes", recipes);
        model.addAttribute("favorites", favorites);

        return "main_menu";
    }
    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = userRepo.findAll();
        model.addAttribute("listUsers", listUsers);

        return "users";
    }

    @GetMapping("/user/addToFavorite/{id}")
    public String addToFavorite(@PathVariable Long id,Model model){
        Recipe recipe = recipeService.getRecipeById(id);
        User user = getCurrentUser();
        user.addToFavorite(recipe);
        userRepo.save(user);

        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "recipes";
    }
    @GetMapping("/editProfile")
    public String editProfile(Model model){
        User user = getCurrentUser();

        model.addAttribute("user", user);
        model.addAttribute("edit", true);
        return "profile";
    }
    @PostMapping("/editProfile")
    public String updateUser(
            @RequestParam(value = "firstName") String firtsName,
            @RequestParam(value = "lastName") String lastName,
            @RequestParam(value = "email") String email
    ){
        User user = getCurrentUser();
        user.setFirstName(firtsName);
        user.setLastName(lastName);
        user.setEmail(email);
        userRepo.save(user);



        return "redirect:/profile";
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
    @GetMapping("/changePassword")
    public String changePassword(){
        return "passwordChangeForm";
    }
    @PostMapping("/changePassword")
    public String changePasswordProcess(
            @RequestParam(value = "oldPassword") String oldPassword,
            @RequestParam(value = "password") String password,
            Model model
    ){
        User user = getCurrentUser();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if(passwordEncoder.matches(oldPassword, user.getPassword())){

            String encodedPassword = passwordEncoder.encode(password);
            user.setPassword(encodedPassword);
            userRepo.save(user);
            return "redirect:/profile";
        } else {
            model.addAttribute("error",true);
            return "passwordChangeForm";
        }




    }
    @PostMapping("/search_recipe_myRecipes")
    public String searchRecipe(@RequestParam("recipeId") Long id,Model model){

        User user = getCurrentUser();
        Set<Recipe> recipes = user.getMyRecipes();
        Set<Recipe> favorites = user.getFavoriteRecipes();
        Set<Event> events = user.getMyEvents();
        model.addAttribute("events", events);
        model.addAttribute("favorites", favorites);
        if(recipes.contains(recipeService.getRecipeById(id))){
            model.addAttribute("recipes", recipeService.getRecipeById(id));

        } else {
            model.addAttribute("recipes", recipes);
        }
        return "main_menu";

    }
    @PostMapping("/search_recipe_myFavoriteRecipes")
    public String searchRecipeFavorites(@RequestParam("recipeId") Long id,Model model){
        User user = getCurrentUser();
        Set<Recipe> recipes = user.getMyRecipes();
        Set<Recipe> favorites = user.getFavoriteRecipes();
        Set<Event> events = user.getMyEvents();
        model.addAttribute("events", events);
        model.addAttribute("recipes", recipes);
        if(favorites.contains(recipeService.getRecipeById(id))){
            model.addAttribute("favorites", recipeService.getRecipeById(id));

        } else {
            model.addAttribute("favorites", favorites);
        }
        return "main_menu";

    }
    @PostMapping("/removeFromFavorites")
    public String removeFromFavorites(Model model,
                                      @RequestParam("id") Long id){
        User author = getCurrentUser();
        Recipe recipe = recipeService.getRecipeById(id);
        author.removeFromFavorites(recipe);
        userRepo.save(author);
        Set<Recipe> recipes = author.getMyRecipes();
        Set<Recipe> favorites = author.getFavoriteRecipes();
        model.addAttribute("recipes", recipes);
        model.addAttribute("favorites", favorites);
        return "main_menu";
    }

    @GetMapping("/shoppingList")
    public String shoppingList(Model model){
        model.addAttribute("ingridients", ingridientRepository.findAll());
        model.addAttribute("ingridient", new Ingridient());
        model.addAttribute("user", getCurrentUser());
        return "shoppingList";
    }

    @PostMapping("/addIngridientToShoppingList")
    public String addIngridientToShoppingList(Model model, @RequestParam("ingId")Long id){
        User user = getCurrentUser();
        user.addToShoppingList(ingridientRepository.findById(id).get());
        userRepo.save(user);
        model.addAttribute("ingridients", ingridientRepository.findAll());
        model.addAttribute("ingridient", new Ingridient());
        model.addAttribute("user", user);
        return "shoppingList";
    }
    @PostMapping("/deleteIngridientFromShoppingList")
    public String deleteIngridientFromShoppingList(Model model, @RequestParam("ingId") Long id){
        User user = getCurrentUser();
        user.removeFromShoppingList(ingridientRepository.findById(id).get());
        userRepo.save(user);
        model.addAttribute("ingridients", ingridientRepository.findAll());
        model.addAttribute("ingridient", new Ingridient());
        model.addAttribute("user", user);
        return "shoppingList";
    }
    @GetMapping("/calendar")
    public String addEvent(Model model) {
        model.addAttribute("event", new Event());
        model.addAttribute("recipes", recipeService.getAllRecipes());
        return "calendar";
    }
    @PostMapping("/user/addEvent")
    public String addEvent(Event event,Model model,
                           @RequestParam("recipeId") Long recipeid){
        User user = getCurrentUser();
        Recipe recipe = recipeService.getRecipeById(recipeid);

//        event.setDate(date);
        event.setRecipe(recipe);
        event.setAuthor(user);
        eventRepocitory.save(event);



        Set<Event> events = user.getMyEvents();
        Set<Recipe> recipes = user.getMyRecipes();
        Set<Recipe> favorites = user.getFavoriteRecipes();
        model.addAttribute("recipes", recipes);
        model.addAttribute("favorites", favorites);
        model.addAttribute("events", events);
        return "main_menu";
    }
    @PostMapping("/updateEvent")
    public String updateEvent(Model model,
                              @RequestParam("id") Long id){
        Event event = eventRepocitory.findById(id).get();
        List<Recipe> recipes = recipeService.getAllRecipes();

        model.addAttribute("recipes", recipes);

        model.addAttribute("event", event);
        return "updateEvent";
    }
    @PostMapping("/user/updateEvent")
    public String processUpdateEvent(@RequestParam("name") String name,
                                     @RequestParam("date") String date,
                                     @RequestParam("recipeId") Long recipeid,
                                     @RequestParam("id") Long id){
        Recipe recipe = recipeService.getRecipeById(recipeid);
        Event event = eventRepocitory.findById(id).get();
        System.out.println(date);
        Date date1= null;
        try {
            date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        event.setDate(date1);
        event.setRecipe(recipe);
        event.setName(name);
        eventRepocitory.save(event);
        return "redirect:/main";

    }
    @PostMapping("/removeEvent")
    public String removeEvent(@RequestParam("id")Long id){
        Event event = eventRepocitory.findById(id).get();
        User user = event.getAuthor();
        user.removeFromEvents(event);
        userRepo.save(user);
        Recipe recipe = event.getRecipe();
        recipe.removeFromEvents(event);
        recipeService.saveRecipe(recipe);
//        eventRepocitory.deleteById(id);

        return "redirect:/main";
    }
}
