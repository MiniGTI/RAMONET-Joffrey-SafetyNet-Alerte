package com.safetynetalerte.safety.net.utilTest.repositoryTest;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.repository.PersonRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PersonRepositoryImplTest {
	private final static Logger logger = (Logger) LoggerFactory.getLogger(PersonRepositoryImpl.class);
	private final Person PERSON = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");
	
	@Autowired
	private PersonRepositoryImpl personRepositoryImpl;
	
	@Test
	void getAllAndLoggerTest() {
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		List<Person> persons = personRepositoryImpl.getAll();
		
		Assertions.assertEquals(24, persons.size());
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("persons: " + persons.size(), logsList.get(0)
				.getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(0)
				.getLevel());
	}
	
	@Test
	void saveAndLoggerTest() {
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		Person personSaved = personRepositoryImpl.save(PERSON);
		
		Assertions.assertEquals(PERSON, personSaved);
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("person: " + personSaved.getFirstName() + " " + personSaved.getLastName() + " " + personSaved.getAddress() + " " +
				personSaved.getCity() + " " + personSaved.getZip() + " " + personSaved.getPhone() + " " + personSaved.getEmail(), logsList.get(0).getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(0).getLevel());
	}
	
	@Test
	void findByIdAndDebugLoggerTest(){
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		Person personFound = personRepositoryImpl.findById("John", "Boyd");
		
		Assertions.assertEquals("John", personFound.getFirstName());
		Assertions.assertEquals("1509 Culver St", personFound.getAddress());
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("personFound: " + personFound.getFirstName() + " " + personFound.getLastName() + " " +
				personFound.getAddress() + " " + personFound.getCity() + " " + personFound.getZip() + " " +
				personFound.getPhone() + " " + personFound.getEmail(), logsList.get(0).getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(0).getLevel());
	}
	
	@Test
	void findByAddressAndLoggerTest(){
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		List<Person> personsList = personRepositoryImpl.findByAddress("1509 Culver St");
		
		Assertions.assertEquals(5, personsList.size());
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("personsList: " + personsList.size(), logsList.get(0).getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(0).getLevel());
	}
	
	@Test
	void updateAndLoggerTest(){
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		Person partialUpdate = new Person(null, null , "address updated", null, null, null, null);
		
		Person updatedPerson = personRepositoryImpl.updateBy(partialUpdate, "John", "Boyd");
		
		Assertions.assertEquals(partialUpdate.getAddress(), updatedPerson.getAddress());
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("person updated: " + updatedPerson.getFirstName() + " " + updatedPerson.getLastName() + " " +
				updatedPerson.getAddress() + " " + updatedPerson.getCity() + " " +
				updatedPerson.getZip() + " " + updatedPerson.getPhone() + " " + updatedPerson.getEmail(), logsList.get(1).getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(1).getLevel());
	}
	
	
	@Test
	void deleteTest(){
		personRepositoryImpl.deleteBy("firstName", "lastName");
		List<Person> personList = personRepositoryImpl.getAll();
		
		Assertions.assertFalse(personList.contains(PERSON));
	}
	
	@Test
	void findByIdWithNoPersonFoundAndErrorLoggerTest(){
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		Person personFound = personRepositoryImpl.findById("", "");
	
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("personFound is null", logsList.get(0).getMessage());
		Assertions.assertEquals(Level.ERROR, logsList.get(0).getLevel());
	}
}
