package com.safetynetalerte.safety.net.utilTest.serviceTest;

import com.safetynetalerte.safety.dto.MedicalrecordDTO;
import com.safetynetalerte.safety.model.Medicalrecord;
import com.safetynetalerte.safety.repository.MedicalrecordRepositoryImpl;
import com.safetynetalerte.safety.service.MedicalrecordService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MedicalrecordServiceTest {
	
	private final String FIRSTNAME = "firstName;";
	private final String LASTNAME = "lastName";
	List<String> medications = new ArrayList<>(List.of("test : 1", "test : 2"));
	List<String> allergies = new ArrayList<>(List.of("test1", "test2"));
	List<Medicalrecord> medicalrecords = new ArrayList<>(List.of(new Medicalrecord("firstName1", "lastName1", "05/05/2000", medications, allergies), new Medicalrecord("firstName2", "lastName2", "09/01/2020", medications, allergies)));
	Medicalrecord medicalrecord = new Medicalrecord("firstName", "lastName", "10/10/1990", medications, allergies);
	@MockBean
	private MedicalrecordRepositoryImpl medicalrecordRepositoryImpl;
	@Autowired
	private MedicalrecordService medicalrecordService;
	
	@Test
	void getAllTest() {
		when(medicalrecordRepositoryImpl.getAll()).thenReturn(medicalrecords);
		
		List<Medicalrecord> result = medicalrecordService.getAll();
		
		Assertions.assertEquals(medicalrecords.size(), result.size());
	}
	
	@Test
	void findByTest() {
		
		when(medicalrecordRepositoryImpl.findById(FIRSTNAME, LASTNAME)).thenReturn(medicalrecord);
		
		Medicalrecord result = medicalrecordService.findBy(FIRSTNAME, LASTNAME);
		
		Assertions.assertEquals(medicalrecord.getBirthdate(), result.getBirthdate());
	}
	
	@Test
	void saveTest() {
		when(medicalrecordRepositoryImpl.save(medicalrecord)).thenReturn(medicalrecord);
		
		Medicalrecord result = medicalrecordService.save(medicalrecord);
		
		Assertions.assertEquals(medicalrecord.getBirthdate(), result.getBirthdate());
	}
	
	@Test
	void deleteTest() {
		
		doNothing().when(medicalrecordRepositoryImpl)
				.deleteBy(FIRSTNAME, LASTNAME);
		medicalrecordService.deleteBy(FIRSTNAME, LASTNAME);
		
		Assertions.assertDoesNotThrow(() -> medicalrecordService.deleteBy(FIRSTNAME, LASTNAME));
	}
	
	@Test
	void update() {
		
		Medicalrecord partialMedicalrecord = new Medicalrecord(null, null, "modified birthdate", null, null);
		Medicalrecord updatedMedicalrecord = new Medicalrecord("firstName", "lastName", "modified birthdate", medications, allergies);
		
		when(medicalrecordRepositoryImpl.updateBy(partialMedicalrecord, FIRSTNAME, LASTNAME)).thenReturn(updatedMedicalrecord);
		
		Medicalrecord result = medicalrecordService.update(partialMedicalrecord, FIRSTNAME, LASTNAME);
		
		Assertions.assertEquals(updatedMedicalrecord.getBirthdate(), result.getBirthdate());
	}
	
	@Test
	void medicalrecordDTOGenerator() {
		
		when(medicalrecordService.findBy(FIRSTNAME, LASTNAME)).thenReturn(medicalrecord);
		
		MedicalrecordDTO result = medicalrecordService.medicalrecordDTOGenerator(FIRSTNAME, LASTNAME);
		
		Assertions.assertEquals(medicalrecord.getMedications(), result.getMedications());
	}
}
