//*********************************************************************************
// Project: < Kitchen >
//        * Assignment: < assignment #2 >
//        * Author(s): < author name Mikhail Yugay, Askar Bulabayev, Arnur Azangaliyev>
//        * Student Number: < student number 101312178, 101322619, 101322803 >
//        * Date:
//        * Description: <Interface to maintain all actions related to Recipe >
//        *********************************************************************************//
package com.recipe.kitchen.service;

import com.recipe.kitchen.model.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getAllRecipes();

    Recipe saveRecipe(Recipe recipe);

    Recipe getRecipeById(Long id);

    Recipe updateRecipe(Recipe recipe);

    void deleteRecipeById(Long id);
}
