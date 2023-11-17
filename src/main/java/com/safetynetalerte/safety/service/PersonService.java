package com.safetynetalerte.safety.service;

import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.repository.PersonRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class of Person object.
 */
@Service
public class PersonService {
	/**
	 * Call of the implementation of the Person repository.
	 */
	private final PersonRepositoryImpl personRepositoryImpl;
	
	/**
	 * Class constructor.
	 *
	 * @param personRepositoryImpl Person repository.
	 */
	public PersonService(PersonRepositoryImpl personRepositoryImpl) {
		this.personRepositoryImpl = personRepositoryImpl;
	}
	
	/**
	 * Call the person repository Impl getAll method.
	 *
	 * @return a list of Person Object.
	 */
	public List<Person> getAll() {
		return personRepositoryImpl.getAll();
	}
	
	/**
	 * Call the person repository Impl findBy method.
	 *
	 * @param firstName id of the Person objects.
	 * @param lastName  id of the Person objects.
	 * @return a Person Object.
	 */
	public Person findBy(final String firstName, final String lastName) {
		return personRepositoryImpl.findById(firstName, lastName);
	}
	
	/**
	 * Call the person repository Impl findByAddress method.
	 *
	 * @param address id of the Person objects.
	 * @return a Person Object.
	 */
	public List<Person> findByAddress(final String address) {
		return personRepositoryImpl.findByAddress(address);
	}
	
	/**
	 * Call the person repository Impl save method.
	 *
	 * @param person Person object parsed in the body of @mapping.
	 * @return the Person object saved.
	 */
	public Person save(Person person) {
		return personRepositoryImpl.save(person);
	}
	
	/**
	 * Call the person repository Impl update method.
	 *
	 * @param partialUpdate Person object with attributes have to be modified.
	 * @param firstName     id of the Person objects.
	 * @param lastName      id of the Person objects.
	 * @return the Person object updated.
	 */
	public Person update(Person partialUpdate, final String firstName, final String lastName) {
		return personRepositoryImpl.updateBy(partialUpdate, firstName, lastName);
	}
	
	/**
	 * Call the person repository Impl delete method.
	 *
	 * @param firstName id of the Person objects.
	 * @param lastName  id of the Person objects.
	 */
	public void deleteBy(final String firstName, final String lastName) {
		personRepositoryImpl.deleteBy(firstName, lastName);
	}
}
