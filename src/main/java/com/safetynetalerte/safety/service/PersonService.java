package com.safetynetalerte.safety.service;

import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.repository.PersonRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
	private final PersonRepositoryImpl personRepositoryImpl;
	
	public PersonService(PersonRepositoryImpl personRepositoryImpl) {
		this.personRepositoryImpl = personRepositoryImpl;
	}
	
	
	public List<Person> getAll() {
		return personRepositoryImpl.getAll();
	}
	
	public Person findBy(final String firstName, final String lastName) {
		return personRepositoryImpl.findById(firstName, lastName);
	}
	
	public List<Person> findPersonsByAddress(final String address){
		return personRepositoryImpl.findPersonsByAddress(address);
	}
	
	public Person save(Person person) {
		return personRepositoryImpl.save(person);
	}

	public Person update(Person partialUpdate, final String firstName, final String lastName) {
		return personRepositoryImpl.updateBy(partialUpdate, firstName, lastName);
	}
	
	public void deleteBy(final String firstName, final String lastName) {
		personRepositoryImpl.deleteBy(firstName, lastName);
	}
	

}
