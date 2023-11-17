package com.safetynetalerte.safety.net.utilTest.serviceTest;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.safetynetalerte.safety.dto.ListPersonStationNumberDTO;
import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.repository.FirestationRepositoryImpl;
import com.safetynetalerte.safety.service.AddressCalculatorService;
import com.safetynetalerte.safety.service.AgeCalculatorService;
import com.safetynetalerte.safety.service.FirestationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FirestationServiceTest {
	
	private final String STATION = "station";
	private final String ADDRESS = "address";
	private final List<Firestation> FIRESTATIONS = new ArrayList<>(
			List.of(new Firestation("599 bvd", "9"), new Firestation("1 25th St", "7"),
					new Firestation("599 1th bvd", "9"), new Firestation("1 20th St", "7")));
	private final Firestation FIRESTATION = new Firestation("9599 bvd", "5");
	@MockBean
	private FirestationRepositoryImpl firestationRepositoryImpl;
	@MockBean
	private AddressCalculatorService addressCalculatorService;
	@MockBean
	private AgeCalculatorService ageCalculatorService;
	@Autowired
	private FirestationService firestationService;
	
	@Test
	void getAllTest() {
		
		when(firestationRepositoryImpl.getAll()).thenReturn(FIRESTATIONS);
		
		List<Firestation> result = firestationService.getAll();
		
		Assertions.assertEquals(FIRESTATIONS.size(), result.size());
	}
	
	@Test
	void findByTest() {
		when(firestationRepositoryImpl.findByStation(STATION)).thenReturn(FIRESTATIONS);
		
		List<Firestation> result = firestationService.findBy(STATION);
		
		Assertions.assertEquals(FIRESTATIONS.size(), result.size());
	}
	
	@Test
	void saveTest() {
		when(firestationRepositoryImpl.save(FIRESTATION)).thenReturn(FIRESTATION);
		
		Firestation result = firestationService.save(FIRESTATION);
		
		Assertions.assertEquals(FIRESTATION.getAddress(), result.getAddress());
	}
	
	@Test
	void deleteByTest() {
		doNothing().when(firestationRepositoryImpl)
				.deleteBy(ADDRESS, STATION);
		firestationService.deleteBy(ADDRESS, STATION);
		
		Assertions.assertDoesNotThrow(() -> firestationService.deleteBy(ADDRESS, STATION));
	}
	
	@Test
	void updateTest() {
		Firestation partialFirestation = new Firestation(null, "5");
		Firestation updatedFirestation = new Firestation("599 bvd", "5");
		
		when(firestationRepositoryImpl.update(partialFirestation, ADDRESS)).thenReturn(updatedFirestation);
		
		Firestation result = firestationService.update(partialFirestation, ADDRESS);
		
		Assertions.assertEquals(updatedFirestation.getAddress(), result.getAddress());
	}
	
	@Test
	void findPersonsCoveredByFirestationIdAndLoggerTest() throws Exception {
		Logger logger = (Logger) LoggerFactory.getLogger(FirestationService.class);
		ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
		listAppender.start();
		logger.addAppender(listAppender);
		
		List<Person> persons = new ArrayList<>(
				List.of(new Person("person1", "person1", "1 test", "test", "12345", "123-456-7891", "test@test.com"),
						new Person("person2", "person2", "2 test", "test", "12346", "123-456-7892", "test2@test.com")));
		
		when(addressCalculatorService.personsCoveredByAFirestation(STATION)).thenReturn(persons);
		when(ageCalculatorService.counterOfAdultListFilter(persons)).thenReturn(1);
		
		ListPersonStationNumberDTO result = firestationService.findPersonsCoveredByFirestationId(STATION);
		
		Assertions.assertEquals(1, result.getAdult());
		Assertions.assertEquals(1, result.getChild());
		Assertions.assertEquals(persons.size(), result.getPersonStationNumberList()
				.size());
		
		List<ILoggingEvent> logsList = listAppender.list;
		Assertions.assertEquals("personsCoveredByAFirestation.size: " + persons.size(), logsList.get(0).getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(0).getLevel());
		Assertions.assertEquals("personsInfosFiltered size: " + result.getPersonStationNumberList().size(), logsList.get(1).getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(1).getLevel());
		Assertions.assertEquals("minor: " + result.getChild() + " adult: " + result.getAdult(), logsList.get(2).getMessage());
		Assertions.assertEquals(Level.DEBUG, logsList.get(2).getLevel());
	}
	
	
}
