//*********************************************************************************
// Project: < Kitchen >
//        * Assignment: < assignment #2 >
//        * Author(s): < author name Mikhail Yugay, Askar Bulabayev, Arnur Azangaliyev>
//        * Student Number: < student number 101312178, 101322619, 101322803 >
//        * Date:
//        * Description: <Class that makes functionalities to the Recipe. >
//        *********************************************************************************//
package com.recipe.kitchen.service.impl;

import com.recipe.kitchen.model.Recipe;
import com.recipe.kitchen.repository.RecipeRepository;
import com.recipe.kitchen.service.RecipeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        super();
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id).get();
    }

    @Override
    public Recipe updateRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    @Override
    public void deleteRecipeById(Long id) {
        recipeRepository.deleteById(id);
    }
}
