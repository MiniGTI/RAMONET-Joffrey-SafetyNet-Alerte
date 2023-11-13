package com.safetynetalerte.safety.net.utilTest.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerte.safety.controller.MedicalrecordController;
import com.safetynetalerte.safety.model.Medicalrecord;
import com.safetynetalerte.safety.service.MedicalrecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalrecordController.class)
public class MedicalrecordControllerTest {
	
	@Autowired
	ObjectMapper objectMapper;
	List<String> medications = new ArrayList<>(List.of("test : 1", "test : 2"));
	List<String> allergies = new ArrayList<>(List.of("test1", "test2"));
	List<Medicalrecord> medicalrecords = new ArrayList<>(List.of(new Medicalrecord("firstName1", "lastName1", "05/05/2000", medications, allergies), new Medicalrecord("firstName2", "lastName2", "09/01/2020", medications, allergies)));
	Medicalrecord medicalrecord = new Medicalrecord("firstName", "lastName", "10/10/1990", medications, allergies);
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private MedicalrecordService medicalrecordService;
	
	@Test
	void getAllTest() throws Exception {
		when(medicalrecordService.getAll()).thenReturn(medicalrecords);
		
		mockMvc.perform(get("/api/v1/medicalrecord"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(medicalrecords.size()))
				.andDo(print());
	}
	
	@Test
	void findByTest() throws Exception {
		String firstName = "firstName";
		String lastName = "lastName";
		when(medicalrecordService.findBy(firstName, lastName)).thenReturn(medicalrecord);
		
		mockMvc.perform((get("/api/v1/medicalrecord/{firstName}/{lastName}", firstName, lastName)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(firstName))
				.andExpect(jsonPath("$.lastName").value(lastName))
				.andExpect(jsonPath("$.birthdate").value(medicalrecord.getBirthdate()))
				.andExpect(jsonPath("$.medications").value(medicalrecord.getMedications()))
				.andExpect(jsonPath("$.allergies").value(medicalrecord.getAllergies()))
				.andDo(print());
	}
	
	@Test
	void saveTest() throws Exception{
		when(medicalrecordService.save(medicalrecord)).thenReturn(medicalrecord);
		
		mockMvc.perform(post("/api/v1/medicalrecord/add").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(medicalrecord)))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	@Test
	void updateTest() throws Exception{
		String firstName = "firstName";
		String lastName = "lastName";
		Medicalrecord partialMedicalrecord = new Medicalrecord(null, null, "modified birthdate", null, null);
		Medicalrecord updatedMedicalrecord = new Medicalrecord("firstName", "lastName", "modified birthdate", medications, allergies);
		
		when(medicalrecordService.update(partialMedicalrecord, firstName, lastName)).thenReturn(updatedMedicalrecord);
	
		mockMvc.perform(patch("/api/v1/medicalrecord/update/{firstName}/{lastName}", firstName, lastName).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(partialMedicalrecord)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(firstName))
				.andExpect(jsonPath("$.lastName").value(lastName))
				.andExpect(jsonPath("$.birthdate").value(updatedMedicalrecord.getBirthdate()))
				.andExpect(jsonPath("$.medications").value(updatedMedicalrecord.getMedications()))
				.andExpect(jsonPath("$.allergies").value(updatedMedicalrecord.getAllergies()))
				.andDo(print());
	
	}
	
	@Test
	void deleteTest() throws Exception{
		String firstName = "firstName";
		String lastName = "lastName";
		doNothing().when(medicalrecordService).deleteBy(firstName, lastName);
		
		mockMvc.perform(delete("/api/v1/medicalrecord/delete/{firstName}/{lastName}", firstName, lastName))
				.andExpect(status().isOk())
				.andDo(print());
	}

}
