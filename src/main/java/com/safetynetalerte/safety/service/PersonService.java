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
	
	
	public List<Person> getAll(){
		return personRepositoryImpl.getAll();
	}
}
