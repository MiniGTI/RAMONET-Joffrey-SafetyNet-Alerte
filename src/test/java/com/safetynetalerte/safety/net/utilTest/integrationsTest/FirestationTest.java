package com.safetynetalerte.safety.net.utilTest.integrationsTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerte.safety.model.Firestation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FirestationTest {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	
	private final String STATION = "9";
	private final String ADDRESS = "la petite maison";
	
	@Test
	void getAllTest() throws Exception{
		mockMvc.perform(get("/api/v1/firestation"))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	@Test
	void findbyTest() throws Exception{
		mockMvc.perform(get("/api/v1/firestation/{station}", "3"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].address").value("1509 Culver St"))
				.andExpect(jsonPath("$.[4].address").value("748 Townings Dr"))
				.andDo(print());
	}
	
	@Test
	void saveTest() throws Exception{
		Firestation firestation = new Firestation(ADDRESS, "6");
		mockMvc.perform(post("/api/v1/firestation/add").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(firestation)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.address").value("la petite maison"))
				.andExpect(jsonPath("$.station").value("6"))
				.andDo(print());
	}
	

	@Test
	void updateTest() throws Exception{
		Firestation partialFirestation = new Firestation(null, "9");
		mockMvc.perform(patch("/api/v1/firestation/update/{address}", ADDRESS).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(partialFirestation)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.station").value("9"))
				.andDo(print());
	}
	
	@Test
	void deleteByTest() throws Exception{
		mockMvc.perform(delete("/api/v1/firestation/delete/{address}/{station}", ADDRESS, STATION ))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	@Test
	void findPersonsCoveredByFirestationIdTest() throws Exception{
		mockMvc.perform(get("/api/v1/firestation/firestation/{station}", "3"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.adult").value("10"))
				.andExpect(jsonPath("$.child").value("3"))
				.andExpect(jsonPath("$.Persons.[4].firstName").value("Felicia"))
				.andDo(print());
	
	}
	
}
