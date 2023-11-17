package com.safetynetalerte.safety.net.utilTest.repositoryTest;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.safetynetalerte.safety.model.Medicalrecord;
import com.safetynetalerte.safety.repository.MedicalrecordRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MedicalrecordRepositoryImplTest {
	private final static Logger logger = (Logger) LoggerFactory.getLogger(MedicalrecordRepositoryImpl.class);
	private final String FIRSTNAME = "firstName";
	private final String LASTNAME = "lastName";
	private final List<String> medications = new ArrayList<>(List.of("test : 1", "test : 2"));
	private final List<String> allergies = new ArrayList<>(List.of("test1", "test2"));
	private final Medicalrecord MEDICALRECORD =
			new Medicalrecord(FIRSTNAME, LASTNAME, "10/10/1990", medications, allergies);
	@Autowired
	private MedicalrecordRepositoryImpl medicalrecordRepositoryImpl;
	
	@Test
	void getAllAndLoggerTest() {
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		List<Medicalrecord> medicalrecords = medicalrecordRepositoryImpl.getAll();
		
		Assertions.assertEquals(24, medicalrecords.size());
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("medicalrecords: " + medicalrecords.size(), logsList.get(0)
				.getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(0)
				.getLevel());
	}
	
	@Test
	void saveAndLoggerTest() {
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		Medicalrecord medicalrecordSaved = medicalrecordRepositoryImpl.save(MEDICALRECORD);
		
		Assertions.assertEquals(MEDICALRECORD, medicalrecordSaved);
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals(
				"medicalrecord: " + medicalrecordSaved.getFirstName() + " " + medicalrecordSaved.getLastName() + " " +
						medicalrecordSaved.getBirthdate() + " " + medicalrecordSaved.getMedications() + " " +
						medicalrecordSaved.getAllergies(), logsList.get(0)
						.getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(0)
				.getLevel());
	}
	
	@Test
	void findByIdAndLoggerTest() {
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		Medicalrecord medicalrecordFound = medicalrecordRepositoryImpl.findById("John", "Boyd");
		
		Assertions.assertEquals("John", medicalrecordFound.getFirstName());
		Assertions.assertEquals("Boyd", medicalrecordFound.getLastName());
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals(
				"medicalrecord found: " + medicalrecordFound.getFirstName() + " " + medicalrecordFound.getLastName() +
						" " + medicalrecordFound.getBirthdate() + " " + medicalrecordFound.getMedications() + " " +
						medicalrecordFound.getAllergies(), logsList.get(0)
						.getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(0)
				.getLevel());
	}
	
	@Test
	void updateAndLoggerTest() {
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		Medicalrecord partialMedicalrecord = new Medicalrecord(null, null, "10/10/1986", null, null);
		
		Medicalrecord medicalrecordUpdated = medicalrecordRepositoryImpl.updateBy(partialMedicalrecord, "John", "Boyd");
		
		Assertions.assertEquals("10/10/1986", medicalrecordUpdated.getBirthdate());
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("medicalrecord updated: " + medicalrecordUpdated.getFirstName() + " " +
				medicalrecordUpdated.getLastName() + " " + medicalrecordUpdated.getBirthdate() + " " +
				medicalrecordUpdated.getMedications() + " " + medicalrecordUpdated.getAllergies(), logsList.get(1)
				.getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(1)
				.getLevel());
	}
	
	@Test
	void deleteByTest() {
		medicalrecordRepositoryImpl.deleteBy(FIRSTNAME, LASTNAME);
		List<Medicalrecord> medicalrecordList = medicalrecordRepositoryImpl.getAll();
		
		Assertions.assertFalse(medicalrecordList.contains(MEDICALRECORD));
	}
	
	@Test
	void findByIdWithNoMedicalrecordFoundAndErrorTest() {
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		medicalrecordRepositoryImpl.findById("", "");
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("medicalrecordFound is null", logsList.get(0)
				.getMessage());
		Assertions.assertEquals(Level.ERROR, logsList.get(0)
				.getLevel());
	}
}
