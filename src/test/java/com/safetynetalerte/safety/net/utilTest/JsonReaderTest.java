package com.safetynetalerte.safety.net.utilTest;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.model.Medicalrecord;
import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.util.JsonReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest public class JsonReaderTest {
	
	private final static Logger logger = (Logger) LoggerFactory.getLogger(JsonReader.class);
	
	@Test
	void getPersonsListFromTheDataJsonFileCalledTheJsonReaderTest() {
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		List<Person> persons = JsonReader.personsList();
		
		Assertions.assertNotNull(persons);
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("Creation of the jsonNode", logsList.get(0)
				.getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(0)
				.getLevel());
		Assertions.assertEquals("Creation of the personList", logsList.get(1)
				.getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(1)
				.getLevel());
	}
	
	@Test
	void getFirestationsListFromTheDataJsonFileCalledTheJsonReaderTest() {
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		List<Firestation> firestations = JsonReader.firestationsList();
		
		Assertions.assertNotNull(firestations);
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("Creation of the jsonNode", logsList.get(0)
				.getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(0)
				.getLevel());
		Assertions.assertEquals("Creation of the firestationList", logsList.get(1)
				.getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(1)
				.getLevel());
	}
	
	@Test
	void getMedicalrecordsListFromTheDataJsonFileCalledTheJsonReaderTest() {
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		List<Medicalrecord> medicalrecords = JsonReader.medicalrecordsList();
		
		Assertions.assertNotNull(medicalrecords);
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("Creation of the jsonNode", logsList.get(0)
				.getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(0)
				.getLevel());
		Assertions.assertEquals("Creation of the medicalrecordsList", logsList.get(1)
				.getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(1)
				.getLevel());
	}
}
