package com.safetynetalerte.safety.controller;

import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.service.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class of Person objects.
 */
@RestController
@RequestMapping("/api/v1/person")
public class PersonController {
    /**
     * Logger.
     */
    private final static Logger logger = LoggerFactory.getLogger(PersonController.class);
    /**
     * Class of the Person service class.
     */
    private final PersonService personService;

    /**
     * Class constructor.
     *
     * @param personService Person service.
     */
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Call the person service getAll method.
     *
     * @return a list of Person Object.
     */
    @GetMapping
    public List<Person> getAll() {
        logger.info("Get all persons from the personsList");
        return personService.getAll();
    }

    /**
     * Call the person service findBy method.
     *
     * @param firstName id of the Person objects.
     * @param lastName  id of the Person objects.
     * @return a Person Object.
     */
    @GetMapping("/{firstName}/{lastName}")
    public Person findBy(
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        logger.info(firstName + " " + lastName + " are found successfully");

        return personService.findBy(firstName, lastName);
    }

    /**
     * Call the person service save method.
     *
     * @param person Person object parsed in the body of @mapping.
     * @return the Person object saved.
     */
    @PostMapping("/add")
    public Person save(
            @RequestBody Person person) {
        logger.info(
                person.getFirstName() + " " + person.getLastName() + " are added successfully in the list of persons");
        return personService.save(person);
    }

    /**
     * Call the person service update method.
     *
     * @param partialUpdate Person object with attributes have to be modified.
     * @param firstName     id of the Person objects.
     * @param lastName      id of the Person objects.
     * @return the Person object updated.
     */
    @PatchMapping("/update/{firstName}/{lastName}")
    public Person update(
            @RequestBody Person partialUpdate,
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        logger.info(firstName + " " + lastName + " are updated successfully in the list of persons");
        return personService.update(partialUpdate, firstName, lastName);
    }

    /**
     * Call the person service delete method.
     *
     * @param firstName id of the Person objects.
     * @param lastName  id of the Person objects.
     */
    @DeleteMapping("/delete/{firstName}/{lastName}")
    public void deleteBy(
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        logger.info(firstName + " " + lastName + " are removed successfully of the list of persons");
        personService.deleteBy(firstName, lastName);
    }
}

