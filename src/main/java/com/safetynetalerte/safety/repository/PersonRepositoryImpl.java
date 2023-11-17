package com.safetynetalerte.safety.repository;

import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.util.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the Person object repository.
 */
@Repository
public class PersonRepositoryImpl implements PersonRepository {
	/**
	 * Logger
	 */
	private final static Logger logger = LoggerFactory.getLogger(PersonRepositoryImpl.class);
	/**
	 * Creation of the list of Person object from the data.json.
	 */
	private final List<Person> persons = JsonReader.personsList();
	
	/**
	 * CRUD read all
	 *
	 * @return a list of all Person object
	 */
	@Override
	public List<Person> getAll() {
		logger.debug("persons: " + persons.size());
		return persons;
	}
	
	/**
	 * CRUD create.
	 *
	 * @param person Person object parsed in the body of the /add request.
	 * @return the Person object parsed.
	 */
	@Override
	public Person save(Person person) {
		persons.add(person);
		logger.debug("person: " + person.getFirstName() + " " + person.getLastName() + " " + person.getAddress() + " " +
				person.getCity() + " " + person.getZip() + " " + person.getPhone() + " " + person.getEmail());
		return person;
	}
	
	/**
	 * CRUD read by id.
	 *
	 * @param firstName id parsed int the @mapping.
	 * @param lastName  id parsed int the @mapping.
	 * @return a Person object with firstName and lastName values in their firstName and lastName attributes.
	 */
	@Override
	public Person findById(final String firstName, final String lastName) {
		Person personFound = null;
		
		for(Person person : persons) {
			if(person.getFirstName()
					.equals(firstName) && person.getLastName()
					.equals(lastName)) {
				personFound = person;
			}
		}
		if(personFound != null) {
			logger.debug("personFound: " + personFound.getFirstName() + " " + personFound.getLastName() + " " +
					personFound.getAddress() + " " + personFound.getCity() + " " + personFound.getZip() + " " +
					personFound.getPhone() + " " + personFound.getEmail());
		} else {
			logger.error("personFound is null");
		}
		return personFound;
	}
	
	/**
	 * CRUD read by id.
	 *
	 * @param address id parsed int the @mapping.
	 * @return a list of Person object with address value in their address attribute.
	 */
	public List<Person> findByAddress(final String address) {
		List<Person> personsList = new ArrayList<>();
		for(Person person : persons) {
			if(person.getAddress()
					.equals(address)) {
				personsList.add(person);
			}
		}
		logger.debug("personsList: " + personsList.size());
		return personsList;
	}
	
	/**
	 * CRUD delete by id.
	 *
	 * @param firstName id parsed int the @mapping.
	 * @param lastName  id parsed int the @mapping.
	 */
	@Override
	public void deleteBy(final String firstName, final String lastName) {
		persons.removeIf(person -> person.getFirstName()
				.equals(firstName) && person.getLastName()
				.equals(lastName));
	}
	
	/**
	 * CRUD update by id.
	 *
	 * @param partialUpdate Person object with attributes have to be modified.
	 * @param firstName     id parsed int the @mapping.
	 * @param lastName      id parsed int the @mapping.
	 * @return the Person object modified.
	 */
	@Override
	public Person updateBy(Person partialUpdate, String firstName, String lastName) {
		Person personToUpdate = findById(firstName, lastName);
		
		personToUpdate.setFirstName(firstName);
		personToUpdate.setLastName(lastName);
		if(Objects.nonNull(partialUpdate.getAddress())) {
			personToUpdate.setAddress(partialUpdate.getAddress());
		}
		if(Objects.nonNull(partialUpdate.getCity())) {
			personToUpdate.setCity(partialUpdate.getCity());
		}
		if(Objects.nonNull(partialUpdate.getZip())) {
			personToUpdate.setZip(partialUpdate.getZip());
		}
		if(Objects.nonNull(partialUpdate.getPhone())) {
			personToUpdate.setPhone(partialUpdate.getPhone());
		}
		if(Objects.nonNull(partialUpdate.getEmail())) {
			personToUpdate.setEmail(partialUpdate.getEmail());
		}
		for(int i = 0; i < persons.size(); i++) {
			if(persons.get(i)
					.getFirstName()
					.equals(firstName) && persons.get(i)
					.getLastName()
					.equals(lastName)) {
				persons.set(i, personToUpdate);
			}
		}
		logger.debug("person updated: " + personToUpdate.getFirstName() + " " + personToUpdate.getLastName() + " " +
				personToUpdate.getAddress() + " " + personToUpdate.getCity() + " " + personToUpdate.getZip() + " " +
				personToUpdate.getPhone() + " " + personToUpdate.getEmail());
		return personToUpdate;
	}
}

