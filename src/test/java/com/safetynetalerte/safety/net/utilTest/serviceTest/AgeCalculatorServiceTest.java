package com.safetynetalerte.safety.net.utilTest.serviceTest;

import com.safetynetalerte.safety.model.Medicalrecord;
import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.repository.MedicalrecordRepositoryImpl;
import com.safetynetalerte.safety.service.AgeCalculatorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
public class AgeCalculatorServiceTest {
	
	private static AgeCalculatorService ageCalculatorService;
	List<Person> persons = new ArrayList<>(List.of(new Person("person1", "person1", "1 test", "test", "12345", "123-456-7891", "test@test.com"), new Person("person2", "person2", "2 test", "test", "12346", "123-456-7892", "test2@test.com")));
	List<String> medication1 = new ArrayList<>(List.of("test : 2", "test2 : 5"));
	List<String> medication2 = new ArrayList<>(List.of("test3 : 1"));
	List<String> allergies1 = new ArrayList<>(List.of("test1"));
	List<String> allergies2 = new ArrayList<>(List.of("test2", "test2.1"));
	List<Medicalrecord> medicalrecords = new ArrayList<>(List.of(new Medicalrecord("person1", "person1", "01/02/2020", medication1, allergies1), new Medicalrecord("person2", "person2", "09/08/1993", medication2, allergies2)));
	@Mock
	private MedicalrecordRepositoryImpl medicalrecordRepositoryImpl;
	
	@BeforeEach
	void setUpPerTest() {
		ageCalculatorService = new AgeCalculatorService(medicalrecordRepositoryImpl);
	}
	
	
	@Test
	void counterOfAdultListFilterTest() {
		when(medicalrecordRepositoryImpl.getAll()).thenReturn(medicalrecords);
		
		int result = ageCalculatorService.counterOfAdultListFilter(persons);
		
		Assertions.assertEquals(1, result);
	}
	
	@Test
	void adultListFiltrerTest() throws Exception {
		when(medicalrecordRepositoryImpl.getAll()).thenReturn(medicalrecords);
		
		List<Person> result = ageCalculatorService.adultListFilter(persons);
		
		Assertions.assertEquals(1, result.size());
	}
	
	@Test
	void ageCalculatorTest() {
		when(medicalrecordRepositoryImpl.getAll()).thenReturn(medicalrecords);
		
		int result = ageCalculatorService.ageCalculator("person1", "person1");
		
		Assertions.assertEquals(3, result);
	}
	
	@Test
	void birthdateFormaterException() {
		List<Medicalrecord> medicalrecordList = new ArrayList<>();
		List<String> medication1 = new ArrayList<>(List.of("test : 2", "test2 : 5"));
		List<String> allergies1 = new ArrayList<>(List.of("test1"));
		medicalrecordList.add(new Medicalrecord("person1", "person1", "02/05/3000", medication1, allergies1));
		
		when(medicalrecordRepositoryImpl.getAll()).thenReturn(medicalrecordList);
		
		int result = ageCalculatorService.counterOfAdultListFilter(persons);
	}
}
