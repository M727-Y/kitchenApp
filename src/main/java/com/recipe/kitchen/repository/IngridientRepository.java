//*********************************************************************************
// Project: < Kitchen >
//        * Assignment: < assignment #2 >
//        * Author(s): < author name Mikhail Yugay, Askar Bulabayev, Arnur Azangaliyev>
//        * Student Number: < student number 101312178, 101322619, 101322803 >
//        * Date:
//        * Description: <JPA repository to send and fetch data from db>
//        *********************************************************************************//
package com.recipe.kitchen.repository;

import com.recipe.kitchen.model.Ingridient;
import com.recipe.kitchen.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngridientRepository  extends JpaRepository<Ingridient, Long> {
}
