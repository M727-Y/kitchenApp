//*********************************************************************************
// Project: < Kitchen >
//        * Assignment: < assignment #2 >
//        * Author(s): < author name Mikhail Yugay, Askar Bulabayev, Arnur Azangaliyev>
//        * Student Number: < student number 101312178, 101322619, 101322803 >
//        * Date:
//        * Description: <Main Class For application with the main method >
//        *********************************************************************************//
package com.recipe.kitchen;

import com.recipe.kitchen.model.User;
import com.recipe.kitchen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class KitchenApplication {

	public static void main(String[] args) {



		SpringApplication.run(KitchenApplication.class, args);
	}


}
