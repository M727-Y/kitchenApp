//*********************************************************************************
// Project: < Kitchen >
//        * Assignment: < assignment #2 >
//        * Author(s): < author name Mikhail Yugay, Askar Bulabayev, Arnur Azangaliyev>
//        * Student Number: < student number 101312178, 101322619, 101322803 >
//        * Date:
//        * Description: <This file contains getters and setters of User class. Model for database table >
//        *********************************************************************************//
package com.recipe.kitchen.model;


import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 45)
    private String email;

    @Column(nullable = false, length = 64)
    private String password;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @ManyToMany
    @JoinTable(
            name="favorites",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name="recipe_id")
    )
    private Set<Recipe> favoriteRecipes;

    @OneToMany(mappedBy = "author",orphanRemoval = true, cascade = CascadeType.PERSIST)
    private Set<Recipe> myRecipes;

    public Set<Event> getMyEvents() {
        return myEvents;
    }

    public void setMyEvents(Set<Event> myEvents) {
        this.myEvents = myEvents;
    }
    public boolean removeFromEvents(Event event){
        return myEvents.remove(event);
    }

    @OneToMany(mappedBy = "author",orphanRemoval = true, cascade = CascadeType.PERSIST)
    private Set<Event> myEvents;




    public Set<Ingridient> getShoppingList() {
        return shoppingList;
    }

    public void setShoppingList(Set<Ingridient> shoppingList) {
        this.shoppingList = shoppingList;
    }

    @ManyToMany
    @JoinTable(
            name="shoppingList",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name="ingridient_id")
    )
    private  Set<Ingridient> shoppingList;

    public boolean addToShoppingList(Ingridient ingridient){
        return shoppingList.add(ingridient);

    }
    public boolean removeFromShoppingList(Ingridient ingridient){
        return shoppingList.remove(ingridient);
    }


//    @OneToMany(mappedBy = "user")
//    private List<CalendarRecipe> planRecipes;

    public Set<Recipe> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public boolean addToFavorite(Recipe recipe){
        return favoriteRecipes.add(recipe);
    }

    public boolean removeFromFavorites(Recipe recipe){ return favoriteRecipes.remove(recipe);}

    public void setFavoriteRecipes(Set<Recipe> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }
//
//    public List<CalendarRecipe> getPlanRecipes() {
//        return planRecipes;
//    }
//
//    public void setPlanRecipes(List<CalendarRecipe> planRecipes) {
//        this.planRecipes = planRecipes;
//    }

    public Set<Recipe> getMyRecipes() {
        return myRecipes;
    }

    public void setMyRecipes(Set<Recipe> myRecipes) {
        this.myRecipes = myRecipes;
    }



    public boolean removeRecipe(Recipe recipe){
        return myRecipes.remove(recipe);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
