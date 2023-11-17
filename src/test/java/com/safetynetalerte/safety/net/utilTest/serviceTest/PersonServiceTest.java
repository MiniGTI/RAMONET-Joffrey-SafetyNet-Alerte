package com.safetynetalerte.safety.net.utilTest.serviceTest;

import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.repository.PersonRepositoryImpl;
import com.safetynetalerte.safety.service.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PersonServiceTest {
	
	private final String FIRSTNAME = "firstName";
	private final String LASTNAME = "lastName";
	List<Person> persons = new ArrayList<>(List.of(new Person("person1", "person1", "1 test", "test", "12345", "123-456-7891", "test@test.com"), new Person("person2", "person2", "2 test", "test", "12346", "123-456-7892", "test2@test.com")));
	Person person = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");
	@MockBean
	private PersonRepositoryImpl personRepositoryImpl;
	@Autowired
	private PersonService personService;
	
	@Test
	void getAllTest() {
		
		when(personRepositoryImpl.getAll()).thenReturn(persons);
		
		List<Person> result = personService.getAll();
		
		Assertions.assertEquals(persons.size(), result.size());
	}
	
	@Test
	void findByTest() {
		
		when(personRepositoryImpl.findById(FIRSTNAME, LASTNAME)).thenReturn(person);
		
		Person result = personService.findBy(FIRSTNAME, LASTNAME);
		
		Assertions.assertEquals(result.getPhone(), person.getPhone());
	}
	
	@Test
	void findByAddressTest() {
		String address = "address";
		List<Person> personsList = new ArrayList<>();
		personsList.add(person);
		
		when(personRepositoryImpl.findByAddress(address)).thenReturn(personsList);
		
		List<Person> result = personService.findByAddress(address);
		
		Assertions.assertEquals(personsList.size(), result.size());
	}
	
	@Test
	void saveTest() {
		when(personRepositoryImpl.save(person)).thenReturn(person);
		
		Person result = personService.save(person);
		
		Assertions.assertEquals(person, result);
	}
	
	@Test
	void updateTest() {
		Person partialUpdate = new Person(null, null, "modified address", null, null, "modified phone", null);
		Person updatedPerson = new Person("firstName", "lastName", "modified address", "city", "zip", "modified phone", "email");
		when(personRepositoryImpl.updateBy(partialUpdate, FIRSTNAME, LASTNAME)).thenReturn(updatedPerson);
		
		Person result = personService.update(partialUpdate, FIRSTNAME, LASTNAME);
		
		Assertions.assertEquals(updatedPerson.getAddress(), result.getAddress());
	}
	
	@Test
	void deleteTest() {
		
		doNothing().when(personRepositoryImpl)
				.deleteBy(FIRSTNAME, LASTNAME);
		personService.deleteBy(FIRSTNAME, LASTNAME);
		
		Assertions.assertDoesNotThrow(() -> personService.deleteBy(FIRSTNAME, LASTNAME));
	}
}
