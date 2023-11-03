package com.safetynetalerte.safety.controller;

import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/person")
public class PersonController {
	private static final Logger logger = LoggerFactory.getLogger(PersonController.class);
	private final PersonService personService;
	
	public PersonController(PersonService personService) {
		this.personService = personService;
	}
	
	@GetMapping
	public List<Person> getAll(){
		logger.info("Get all persons from the personsList");
		return personService.getAll();
	}
}
