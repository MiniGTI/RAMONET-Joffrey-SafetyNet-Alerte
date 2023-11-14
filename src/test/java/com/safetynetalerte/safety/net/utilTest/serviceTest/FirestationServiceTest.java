package com.safetynetalerte.safety.net.utilTest.serviceTest;

import com.safetynetalerte.safety.dto.ListPersonStationNumberDTO;
import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.repository.FirestationRepositoryImpl;
import com.safetynetalerte.safety.service.AddressCalculatorService;
import com.safetynetalerte.safety.service.AgeCalculatorService;
import com.safetynetalerte.safety.service.FirestationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest public class FirestationServiceTest {
	
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
	void findPersonsCoveredByFirestationIdTest() throws Exception {
		
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
	}
}
