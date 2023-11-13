package com.safetynetalerte.safety.net.utilTest;

import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.model.Medicalrecord;
import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.util.JsonReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class JsonReaderTest {

	
	@Test
	public void getPersonsListFromTheDataJsonFileCalledTheJsonReaderTest(){
	List<Person> persons = JsonReader.personsList();

		Assertions.assertNotNull(persons);
	}
	
	@Test
	public void getFirestationsListFromTheDataJsonFileCalledTheJsonReaderTest(){
		List<Firestation> firestations = JsonReader.firestationsList();
		
		Assertions.assertNotNull(firestations);
	}
	@Test
	public void getMedicalrecordsListFromTheDataJsonFileCalledTheJsonReaderTest(){
		List<Medicalrecord> medicalrecords = JsonReader.medicalrecordsList();
		
		Assertions.assertNotNull(medicalrecords);
	}
}
