package com.safetynetalerte.safety.net.utilTest.serviceTest;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.repository.FirestationRepositoryImpl;
import com.safetynetalerte.safety.repository.PersonRepositoryImpl;
import com.safetynetalerte.safety.service.AddressCalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;
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
	
	List<Person> persons = new ArrayList<>(List.of(new Person("person1", "person1", "1 test", "test", "12345", "123-456-7891", "test@test.com"), new Person("person2", "person2", "2 test", "test", "12345", "123-456-7892", "test2@test.com"), new Person("person3", "person3", "3 test", "test", "12346", "123-456-7893", "test3@test.com")));
	
	
	@BeforeEach
	void setUpPerTest() {
		addressCalculatorService = new AddressCalculatorService(firestationRepositoryImpl, personRepositoryImpl);
	}
	
	@Test
	void personsCoveredByAFirestationTest() {
		List<Firestation> firestationList = new ArrayList<>();
		firestationList.add(new Firestation("1 test", "1"));
		when(firestationRepositoryImpl.findByStation("1")).thenReturn(firestationList);
		when(personRepositoryImpl.getAll()).thenReturn(persons);
		
		List<Person> result = addressCalculatorService.personsCoveredByAFirestation("1");
		
		Assertions.assertEquals(1, result.size());
		Assertions.assertTrue(result.stream()
				.anyMatch(person -> person.getFirstName()
						.equals("person1")));
	}
	
	@Test
	void personsCoveredByAFirestationWhenFirestationListIsEmptyLoggerTest() {
		Logger logger = (Logger) LoggerFactory.getLogger(AddressCalculatorService.class);
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		List<Firestation> firestationList = new ArrayList<>();
		when(firestationRepositoryImpl.findByStation("1")).thenReturn(firestationList);
		when(personRepositoryImpl.getAll()).thenReturn(persons);
		
		addressCalculatorService.personsCoveredByAFirestation("1");
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("firestations size: 0", logsList.get(0)
				.getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(0)
				.getLevel());
		Assertions.assertEquals("personsCoveredList is empty", logsList.get(1)
				.getMessage());
		Assertions.assertEquals(Level.ERROR, logsList.get(1)
				.getLevel());
	}
}
