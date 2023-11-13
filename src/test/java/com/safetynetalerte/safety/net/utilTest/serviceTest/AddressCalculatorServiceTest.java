package com.safetynetalerte.safety.net.utilTest.serviceTest;

import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.repository.FirestationRepositoryImpl;
import com.safetynetalerte.safety.repository.PersonRepositoryImpl;
import com.safetynetalerte.safety.service.AddressCalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class AddressCalculatorServiceTest {
	
	private static AddressCalculatorService addressCalculatorService;
	
	@Mock
	private static FirestationRepositoryImpl firestationRepositoryImpl;
	@Mock
	private static PersonRepositoryImpl personRepositoryImpl;
	
	List<Person> persons = new ArrayList<>();

	
	@BeforeEach
	void setUpPerTest(){
		addressCalculatorService = new AddressCalculatorService(firestationRepositoryImpl, personRepositoryImpl);
		persons.add(new Person("person1", "person1", "1 test", "test", "12345", "123-456-7891", "test@test.com"));
		persons.add(new Person("person2", "person2", "2 test", "test", "12345", "123-456-7892", "test2@test.com"));
		persons.add(new Person("person3", "person3", "3 test", "test", "12346", "123-456-7893", "test3@test.com"));

		
	}
	
	@Test
	void personsCoveredByAFirestationTest(){
		List<Firestation> firestationList = new ArrayList<>();
		firestationList.add(new Firestation("1 test", "1"));
		when(firestationRepositoryImpl.findByStation("1")).thenReturn(firestationList);
		when(personRepositoryImpl.getAll()).thenReturn(persons);
		
		List<Person> result = addressCalculatorService.personsCoveredByAFirestation("1");
		
		
		
		Assertions.assertEquals(1, result.size());
		Assertions.assertTrue(result.stream().anyMatch(person -> person.getFirstName().equals("person1")));
	}
	
	
	/**
	public List<Person> personsCoveredByAFirestation(String station) {
		List<Firestation> firestations = firestationRepositoryImpl.findByStation(station);
		List<Person> persons = personRepositoryImpl.getAll();
		List<Person> personsCoveredList = new ArrayList<>();
		for(Firestation firestation : firestations)
			for(Person person : persons) {
				if(person.getAddress()
						.equals(firestation.getAddress())) {
					personsCoveredList.add(person);
				}
			}
		return personsCoveredList;
	}
	 */
}
