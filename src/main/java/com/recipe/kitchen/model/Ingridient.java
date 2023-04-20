//*********************************************************************************
// Project: < Kitchen >
//        * Assignment: < assignment #2 >
//        * Author(s): < author name Mikhail Yugay, Askar Bulabayev, Arnur Azangaliyev>
//        * Student Number: < student number 101312178, 101322619, 101322803 >
//        * Date:
//        * Description: <Ingridient Entity contans columns and relations to other entities >
//        *********************************************************************************//
package com.recipe.kitchen.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ingridients")
public class Ingridient {
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }

    @ManyToMany(mappedBy = "ingridients")
    private Set<Recipe> recipes;

    public Set<User> getInShoppingList() {
        return inShoppingList;
    }

    public void setInShoppingList(Set<User> inShoppingList) {
        this.inShoppingList = inShoppingList;
    }

    @ManyToMany(mappedBy = "shoppingList")
    private Set<User> inShoppingList;

}
