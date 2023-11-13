package com.safetynetalerte.safety.controller;

import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/person")
public class PersonController {
	private final static Logger logger = LoggerFactory.getLogger(PersonController.class);
	private final PersonService personService;
	
	public PersonController(PersonService personService) {
		this.personService = personService;
	}
	
	@GetMapping
	public List<Person> getAll() {
		logger.info("Get all persons from the personsList");
		return personService.getAll();
	}
	
	@GetMapping("/{firstName}/{lastName}")
	public Person findBy(
			@PathVariable
			final String firstName,
			@PathVariable
			final String lastName) {
		logger.info(firstName + " " + lastName + " are found successfully");
		
		return personService.findBy(firstName, lastName);
	}
	
	@PostMapping("/add")
	public Person save(
			@RequestBody Person person) {
		logger.info(person.getFirstName() + " " + person.getLastName() + " are added successfully in the list of persons");
		return personService.save(person);
	}
	
	@PatchMapping("/update/{firstName}/{lastName}")
	public Person update(
			@RequestBody Person partialUpdate,
			@PathVariable
			final String firstName,
			@PathVariable
			final String lastName) {
		logger.info(firstName + " " + lastName + " are updated successfully in the list of persons");
		return personService.update(partialUpdate, firstName, lastName);
	}
	
	@DeleteMapping("/delete/{firstName}/{lastName}")
	public void deleteBy(
			@PathVariable
			final String firstName,
			@PathVariable
			final String lastName) {
		logger.info(firstName + " " + lastName + " are removed successfully of the list of persons");
		personService.deleteBy(firstName, lastName);
	}
}

