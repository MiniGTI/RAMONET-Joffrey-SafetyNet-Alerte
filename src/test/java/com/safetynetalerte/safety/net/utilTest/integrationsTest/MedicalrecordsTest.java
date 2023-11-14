package com.safetynetalerte.safety.net.utilTest.integrationsTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerte.safety.model.Medicalrecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest @AutoConfigureMockMvc public class MedicalrecordsTest {
	
	private final String FIRSTNAME = "Sasha";
	private final String LASTNAME = "Ketchum";
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Test
	void getAllTest() throws Exception {
		mockMvc.perform(get("/api/v1/medicalrecord"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].firstName").value("John"))
				.andExpect(jsonPath("$.[14].firstName").value("Reginold"))
				.andDo(print());
	}
	
	@Test
	void findByTest() throws Exception {
		mockMvc.perform(get("/api/v1/medicalrecord/{firstName}/{lastName}", "Lily", "Cooper"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.birthdate").value("03/06/1994"))
				.andDo(print());
	}
	
	@Test
	void saveTest() throws Exception {
		List<String> medications = new ArrayList<>(List.of("hyperPokeBall : 5", "superSoin : 4"));
		List<String> allergies = new ArrayList<>(List.of("teamRockett"));
		Medicalrecord medicalrecord = new Medicalrecord("Sasha", "Ketchum", "01/04/1997", medications, allergies);
		mockMvc.perform(post("/api/v1/medicalrecord/add").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(medicalrecord)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lastName").value("Ketchum"))
				.andExpect(jsonPath("$.birthdate").value("01/04/1997"))
				.andDo(print());
	}

	@Test
	void updateTest() throws Exception{
		Medicalrecord partialUpdate = new Medicalrecord(null, null, "updated birthdate", null, null);
	mockMvc.perform(patch("/api/v1/medicalrecord/update/{firstName}/{lastName}", FIRSTNAME, LASTNAME).contentType(MediaType.APPLICATION_JSON)
			.content(objectMapper.writeValueAsString(partialUpdate)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.lastName").value(LASTNAME))
			.andExpect(jsonPath("$.birthdate").value("updated birthdate"))
			.andDo(print());
	}
	
	@Test
	void deleteBy() throws Exception{
		mockMvc.perform(delete("/api/v1/medicalrecord/delete/{firstName}/{lastName}", FIRSTNAME, LASTNAME))
				.andExpect(status().isOk())
				.andDo(print());
	}

}
