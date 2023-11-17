package com.safetynetalerte.safety.net.utilTest.repositoryTest;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.repository.FirestationRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest

public class FirestationRepositoryImplTest {
	private final static Logger logger = (Logger) LoggerFactory.getLogger(FirestationRepositoryImpl.class);
	private final Firestation FIRESTATION = new Firestation("9599 bvd", "5");
	private final String ADDRESS = "9599 bvd";
	@Autowired
	private FirestationRepositoryImpl firestationRepositoryImpl;
	
	@Test
	void getAllAndLoggerTest() {
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		List<Firestation> result = firestationRepositoryImpl.getAll();
		
		Assertions.assertEquals(14, result.size());
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("firestations: " + result.size(), logsList.get(0)
				.getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(0)
				.getLevel());
	}
	
	@Test
	void saveAndLoggerTest() {
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		Firestation firestationSaved = firestationRepositoryImpl.save(FIRESTATION);
		
		Assertions.assertEquals(FIRESTATION, firestationSaved);
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("firestation: " + firestationSaved.getAddress() + " " + firestationSaved.getStation(),
				logsList.get(0)
						.getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(0)
				.getLevel());
	}
	
	@Test
	void findByStationAndDebugLoggerTest() {
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		List<Firestation> firestationsFound = firestationRepositoryImpl.findByStation("3");
		
		Assertions.assertEquals("1509 Culver St", firestationsFound.get(0)
				.getAddress());
		Assertions.assertEquals(5, firestationsFound.size());
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("firestationList: " + firestationsFound.size(), logsList.get(0)
				.getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(0)
				.getLevel());
	}
	
	@Test
	void findByAddressAndDebugLoggerTest() {
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		Firestation firestationFound = firestationRepositoryImpl.findByAddress("1509 Culver St");
		
		Assertions.assertEquals("3", firestationFound.getStation());
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals(
				"firestationFound: " + firestationFound.getAddress() + " " + firestationFound.getStation(),
				logsList.get(0)
						.getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(0)
				.getLevel());
	}
	
	@Test
	void updateAndLoggerTest() {
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		Firestation partialFirestation = new Firestation(null, "9");
		
		Firestation firestationUpdated = firestationRepositoryImpl.update(partialFirestation, ADDRESS);
		
		Assertions.assertEquals("9", firestationUpdated.getStation());
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals(
				"firestation to update: " + firestationUpdated.getAddress() + " " + firestationUpdated.getStation(),
				logsList.get(0)
						.getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(0)
				.getLevel());
	}
	
	@Test
	void deleteByTest() {
		firestationRepositoryImpl.deleteBy(ADDRESS, "9");
		List<Firestation> firestationsList = firestationRepositoryImpl.getAll();
		
		Assertions.assertFalse(firestationsList.contains(new Firestation(ADDRESS, "9")));
	}
	
	@Test
	void findByAddressWithNoFirestationFoundAndErrorLoggerTest() {
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		firestationRepositoryImpl.findByAddress("");
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("firestationFound is null", logsList.get(0)
				.getMessage());
		Assertions.assertEquals(Level.ERROR, logsList.get(0)
				.getLevel());
	}
}
