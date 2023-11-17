package com.safetynetalerte.safety.net.utilTest.serviceTest;

import com.safetynetalerte.safety.dto.*;
import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.repository.FirestationRepositoryImpl;
import com.safetynetalerte.safety.repository.PersonRepositoryImpl;
import com.safetynetalerte.safety.service.AgeCalculatorService;
import com.safetynetalerte.safety.service.MedicalrecordService;
import com.safetynetalerte.safety.service.SpecialsRequestsService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest public class SpecialsRequestsServiceTest {
	private final String ADDRESS = "address";
	private final String STATION = "9";
	private List<Person> personsFindByAddress = new ArrayList<>(
			List.of(new Person("person1", "person1", "address", "test", "12345", "123-456-7891", "test1@test.com"),
					new Person("person2", "person1", "address", "test", "12345", "123-456-7892", "test2@test.com"),
					new Person("person3", "person1", "address", "test", "12345", "123-456-7891", "test@test.com"),
					new Person("person4", "person1", "address", "test", "12345", "123-456-7891", "test@test.com")));
	private final List<Firestation> FIRESTATIONS =
			new ArrayList<>(List.of(new Firestation("599 bvd", "9"), new Firestation("1 25th St", "9")));
	
	List<String> medications = new ArrayList<>(List.of("test : 1", "test : 2"));
	List<String> allergies = new ArrayList<>(List.of("test1", "test2"));
	MedicalrecordDTO medicalrecordDTO = new MedicalrecordDTO(medications, allergies);
	@MockBean
	private PersonRepositoryImpl personRepositoryImpl;
	@MockBean
	private FirestationRepositoryImpl firestationRepositoryImpl;
	@MockBean
	private AgeCalculatorService ageCalculatorService;
	@MockBean
	private MedicalrecordService medicalrecordService;
	@Autowired
	private SpecialsRequestsService specialsRequestsService;
	
	@Test
	void findChildrenAndFamilyByAddress() throws Exception {
		List<Person> minorPersonsList = new ArrayList<>(
				List.of(new Person("person3", "person1", "address", "test", "12345", "123-456-7891", "test@test.com"),
						new Person("person4", "person1", "address", "test", "12345", "123-456-7891", "test@test.com")));
		when(personRepositoryImpl.findByAddress(ADDRESS)).thenReturn(personsFindByAddress);
		when(ageCalculatorService.adultListFilter(personsFindByAddress)).thenReturn(minorPersonsList);
		when(ageCalculatorService.ageCalculator("person3", "person1")).thenReturn(8);
		when(ageCalculatorService.ageCalculator("person4", "person1")).thenReturn(10);
		
		ListChildAlertDTO result = specialsRequestsService.findChildrenAndFamilyByAddress(ADDRESS);
		
		Assertions.assertEquals(minorPersonsList.size(), result.getChildAlertList()
				.size());
		Assertions.assertEquals(personsFindByAddress.size(), result.getFamilyList()
				.size());
	}
	
	@Test
	void findPhoneOfAllPersonsCoveredByFirestationNumberTest() {
		
		List<Person> person1List = new ArrayList<>(
				List.of(new Person("person1", "person1", "599 bvd", "test", "12345", "123-456-7891", "test1@test.com"),
						new Person("person2", "person1", "599 bvd", "test", "12345", "123-456-7892",
								"test2@test.com")));
		List<Person> persons2List = new ArrayList<>(
				List.of(new Person("person3", "person1", "1 25th St", "test", "12345", "123-456-7891", "test@test.com"),
						new Person("person4", "person1", "1 25th St", "test", "12345", "123-456-7891",
								"test@test.com")));
		
		when(firestationRepositoryImpl.findByStation(STATION)).thenReturn(FIRESTATIONS);
		when(personRepositoryImpl.findByAddress("599 bvd")).thenReturn(person1List);
		when(personRepositoryImpl.findByAddress("1 25th St")).thenReturn(persons2List);
		
		ListPhoneAlertDTO result = specialsRequestsService.findPhoneOfAllPersonsCoveredByFirestationNumber(STATION);
		
		Assertions.assertNotNull(result);
		Assertions.assertEquals(4, result.getPhoneAlertList()
				.size());
	}
	
	@Test
	void findPersonsAndTheirMedicalrecordsAndTheirFirestationByAddressTest() {
		
		when(personRepositoryImpl.findByAddress(ADDRESS)).thenReturn(personsFindByAddress);
		when(ageCalculatorService.ageCalculator("person1", "person1")).thenReturn(38);
		when(ageCalculatorService.ageCalculator("person2", "person1")).thenReturn(40);
		when(ageCalculatorService.ageCalculator("person3", "person1")).thenReturn(8);
		when(ageCalculatorService.ageCalculator("person4", "person1")).thenReturn(10);
		when(medicalrecordService.medicalrecordDTOGenerator("person1", "person1")).thenReturn(
				new MedicalrecordDTO(medications, allergies));
		when(medicalrecordService.medicalrecordDTOGenerator("person2", "person1")).thenReturn(
				new MedicalrecordDTO(medications, allergies));
		when(medicalrecordService.medicalrecordDTOGenerator("person3", "person1")).thenReturn(
				new MedicalrecordDTO(medications, allergies));
		when(medicalrecordService.medicalrecordDTOGenerator("person4", "person1")).thenReturn(
				new MedicalrecordDTO(medications, allergies));
		when(firestationRepositoryImpl.findByAddress(ADDRESS)).thenReturn(new Firestation(ADDRESS, "9"));
		
		ListFirePersonDTO result =
				specialsRequestsService.findPersonsAndTheirMedicalrecordsAndTheirFirestationByAddress(ADDRESS);
		
		Assertions.assertEquals(4, result.getPersonFireAlertList()
				.size());
		Assertions.assertEquals("9", result.getFirestationNumber());
	}
	
	@Test
	void findPersonsAndTheirMedicalrecordsByAFirestationListTest(){
		List<String> firestationId = new ArrayList<>(List.of("9","1"));
		when(firestationRepositoryImpl.findByStation(STATION)).thenReturn(FIRESTATIONS);
		when(personRepositoryImpl.findByAddress("599 bvd")).thenReturn(personsFindByAddress);
		personsFindByAddress.forEach(person -> person.setAddress("599 bvd"));
		when(ageCalculatorService.ageCalculator("person1", "person1")).thenReturn(38);
		when(ageCalculatorService.ageCalculator("person2", "person1")).thenReturn(40);
		when(ageCalculatorService.ageCalculator("person3", "person1")).thenReturn(8);
		when(ageCalculatorService.ageCalculator("person4", "person1")).thenReturn(10);
		when(medicalrecordService.medicalrecordDTOGenerator("person1", "person1")).thenReturn(
				new MedicalrecordDTO(medications, allergies));
		when(medicalrecordService.medicalrecordDTOGenerator("person2", "person1")).thenReturn(
				new MedicalrecordDTO(medications, allergies));
		when(medicalrecordService.medicalrecordDTOGenerator("person3", "person1")).thenReturn(
				new MedicalrecordDTO(medications, allergies));
		when(medicalrecordService.medicalrecordDTOGenerator("person4", "person1")).thenReturn(
				new MedicalrecordDTO(medications, allergies));
		
		ListPersonsCoveredResponseDTO result = specialsRequestsService.findPersonsAndTheirMedicalrecordsByAFirestationList(firestationId);
		
		Assertions.assertEquals(FIRESTATIONS.size(), result.getListPersonsCoveredList().size());
		Assertions.assertEquals(personsFindByAddress.size(), result.getListPersonsCoveredList().get(0).getPersonAndMedicalrecordsList().size());
	}
	
	@Test
	void findPersonAndTheirFamilyTest(){
		String firstName = "person1";
		String lastName = "person1";
		personsFindByAddress.get(3).setLastName("person2");
		when(personRepositoryImpl.getAll()).thenReturn(personsFindByAddress);
		when(ageCalculatorService.ageCalculator("person1", "person1")).thenReturn(38);
		when(ageCalculatorService.ageCalculator("person2", "person1")).thenReturn(40);
		when(ageCalculatorService.ageCalculator("person3", "person1")).thenReturn(8);
		when(medicalrecordService.medicalrecordDTOGenerator("person1", "person1")).thenReturn(
				new MedicalrecordDTO(medications, allergies));
		when(medicalrecordService.medicalrecordDTOGenerator("person2", "person1")).thenReturn(
				new MedicalrecordDTO(medications, allergies));
		when(medicalrecordService.medicalrecordDTOGenerator("person3", "person1")).thenReturn(
				new MedicalrecordDTO(medications, allergies));
		
		ListPersonsInfoDTO result = specialsRequestsService.findPersonAndTheirFamily(firstName, lastName);
		
		Assertions.assertEquals(3, result.getPersonsInfoList().size());
	}
	
	@Test
	void findAllMailOfTheCityTest(){
		String city = "test";
		personsFindByAddress.get(3).setCity("modified");
		when(personRepositoryImpl.getAll()).thenReturn(personsFindByAddress);
		
		ListEmailDTO result = specialsRequestsService.findAllMailOfTheCity(city);
		
		Assertions.assertEquals(3, result.getEmailList().size());
	}
}