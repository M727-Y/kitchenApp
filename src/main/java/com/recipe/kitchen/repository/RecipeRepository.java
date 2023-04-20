//*********************************************************************************
// Project: < Kitchen >
//        * Assignment: < assignment #2 >
//        * Author(s): < author name Mikhail Yugay, Askar Bulabayev, Arnur Azangaliyev>
//        * Student Number: < student number 101312178, 101322619, 101322803 >
//        * Date:
//        * Description: <RecipeRepository - Interface that maintains recipe information >
//        *********************************************************************************//
package com.recipe.kitchen.repository;

import com.recipe.kitchen.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

}