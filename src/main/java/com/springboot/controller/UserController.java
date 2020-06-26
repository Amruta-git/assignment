package com.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.model.User;
import com.springboot.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
	
	@Autowired
	UserRepository userRepo;
	
	 @GetMapping("/users")
	    public List <User> getAllUsers() {
	        
		 return userRepo.findAll();          //find all users from the user table
	    }
	 
	 @GetMapping("/user/{id}")
	    public Optional < User > getAllUserById(@PathVariable long id) {
	       
		 return userRepo.findById(id);        //find user by Id from the user table
	        
	    }
	 
	 @PostMapping("/users")
	    public User createUser(@RequestBody User user) {
	        
		 return userRepo.save(user);          //save user data in user table
	   
	 }

}
