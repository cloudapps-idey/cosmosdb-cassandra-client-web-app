package com.example.controller;

import java.io.IOException;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import com.example.services.UserService;

@Controller
@RequestMapping("userservice")
public class UserController {

	@Autowired
	private UserService service;

	@GetMapping("users")
	public ResponseEntity<String> getCategories() {

		String users = "Please see the log";
		try {
			service.getAllUsers();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ResponseEntity<String>(users, HttpStatus.OK);
	}
}