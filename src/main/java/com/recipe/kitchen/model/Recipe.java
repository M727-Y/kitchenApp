//*********************************************************************************
// Project: < Kitchen >
//        * Assignment: < assignment #2 >
//        * Author(s): < author name Mikhail Yugay, Askar Bulabayev, Arnur Azangaliyev>
//        * Student Number: < student number 101312178, 101322619, 101322803 >
//        * Date:
//        * Description: <This file contains getters and setters of Recipe class. Model for database table >
//        *********************************************************************************//
package com.recipe.kitchen.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "recipe_name", nullable = false)
    private String name;

    @Column(name = "recipe_description")
    private String description;

    public void setRecipeText(String recipeText) {
        this.recipeText = recipeText;
    }

    @Column(name = "recipe_Text")
    private String recipeText;

    public Set<User> getFavoriteUsers() {
        return favoriteUsers;
    }

    public void setFavoriteUsers(Set<User> favoriteUsers) {
        this.favoriteUsers = favoriteUsers;
    }

    @ManyToMany(mappedBy = "favoriteRecipes", cascade = CascadeType.PERSIST)
    private Set<User> favoriteUsers;

//    @OneToMany(mappedBy="recipe")
//    private Set<CalendarRecipe> planMeals;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable=false)
    private User author;

    public Set<Ingridient> getIngridients() {
        return ingridients;
    }

    public boolean addIngridient(Ingridient ingridient){return ingridients.add(ingridient);}

    public void setIngridients(Set<Ingridient> ingridients) {
        this.ingridients = ingridients;
    }

    @ManyToMany
    @JoinTable(
            name="recipe_ingridients",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name="ingridient_id")
    )
    private Set<Ingridient> ingridients;

    public boolean removeFromEvents(Event event){
        return events.remove(event);
    }
    public Set<Event> getEvents() {
        return events;
    }

    public void setEvents(Set<Event> events) {
        this.events = events;
    }

    @OneToMany(mappedBy = "recipe",orphanRemoval = true, cascade = CascadeType.PERSIST)
    private Set<Event> events;

    public String getRecipeText() {
        return recipeText;
    }

    public void setRecipe(String recipeText) {
        this.recipeText = recipeText;
    }
    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public boolean deleteIngridient(Ingridient ingridient){
        return ingridients.remove(ingridient);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}
