package com.sila.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sila.service.DefaultPersonService;

@RestController
public class PersonController {

	@Autowired
	private DefaultPersonService person;

	@RequestMapping("/")
	public String index() {
		return "Hello";
	}

}
