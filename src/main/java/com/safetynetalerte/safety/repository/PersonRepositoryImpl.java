package com.safetynetalerte.safety.repository;

import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.util.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class PersonRepositoryImpl implements PersonRepository {
	private final static Logger logger = LoggerFactory.getLogger(PersonRepositoryImpl.class);
	private final List<Person> persons = JsonReader.personsList();
	
	@Override
	public List<Person> getAll() {
		return persons;
	}
	
	@Override
	public Person save(Person person) {
		persons.add(person);
		return person;
	}
	
	@Override
	public Person findById(final String firstName, final String lastName) {
			Person findedPerson = null;
		
		for(Person person : persons) {
			if(person
					.getFirstName()
					.equals(firstName) && person
					.getLastName()
					.equals(lastName)) {
				findedPerson = person;
			}
		}
		
		return findedPerson;
	}
	
	public List<Person> findByAddress(final String address) {
		List<Person> personsList = new ArrayList<>();
		for(Person person : persons) {
			if(person.getAddress()
					.equals(address)) {
				personsList.add(person);
			}
		}
		return personsList;
	}
	
	public List<Person> findPersonsByAddress(final String address) {
		List<Person> personList = new ArrayList<>();
		for(Person person : persons) {
			if(person.getAddress()
					.equals(address)) {
				personList.add(person);
			}
		}
		return personList;
	}
	
	@Override
	public void deleteBy(final String firstName, final String lastName) {
		persons.removeIf(person -> person.getFirstName()
				.equals(firstName) && person.getLastName()
				.equals(lastName));
	}
	
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
		return personToUpdate;
	}
}

