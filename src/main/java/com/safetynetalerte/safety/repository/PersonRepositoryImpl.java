package com.safetynetalerte.safety.repository;

import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.util.JsonReader;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class PersonRepositoryImpl implements PersonRepository {
	
	private final List<Person> persons = JsonReader.personList();
	private Person person;
	
	@Override
	public List<Person> getAll() {
		return persons;
	}
	
	@Override
	public Person save(Person person) {
		return person;
	}
	
	@Override
	public Person findBy(String key) {
		return person;
	}
	
	@Override
	public void deleteBy(Person person) {
	
	}
}
