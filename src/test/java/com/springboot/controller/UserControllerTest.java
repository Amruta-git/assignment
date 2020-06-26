package com.springboot.controller;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.model.User;

@WebMvcTest(UserControllerTest.class)
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;
	@MockBean
	private UserController userController;

	@Test
	public void getUsers() throws Exception {
		User user = new User(1, "Jenny", "abc", "20", "Female");
		User user1 = new User(2, "John", "xyz", "30", "Male");

		List<User> userList = new ArrayList<>();
		userList.add(user);
		userList.add(user1);
		Mockito.when(userController.getAllUsers()).thenReturn(userList);

		mvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$").exists());

		List<User> responseUser = userController.getAllUsers();
		Assert.assertNotNull(user);
		Assert.assertEquals(responseUser.get(0), user);
		Assert.assertEquals(responseUser.get(1), user1);
		Assert.assertEquals(responseUser.size(), 2);
	}

	@Test
	public void getUserById() throws Exception {
		User user = new User(1, "Jenny", "abc", "20", "Female");

		Mockito.when(userController.getAllUserById(user.getId())).thenReturn(Optional.of(user));
		mvc.perform(get("/user/{id}", user.getId())).andExpect(status().isOk());
		Optional<User> responseUser = userController.getAllUserById(user.getId());
		Assert.assertNotNull(responseUser);
		Assert.assertSame(responseUser.get(), user);

	}

	@Test
	public void createUser() throws Exception {
		User user = new User();
		user.setId(1);
		user.setFirstName("Anny");
		user.setLastName("xyz");
		user.setAge("15");
		user.setGender("Female");

		Mockito.when(userController.createUser(Mockito.any(User.class))).thenReturn(user);
		mvc.perform( post("/users").content(new ObjectMapper().writeValueAsString(user)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	
	}

}
