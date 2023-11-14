package com.safetynetalerte.safety.net.utilTest.integrationsTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerte.safety.model.Person;
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


@SpringBootTest @AutoConfigureMockMvc public class PersonTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private final String FIRSTNAME = "Sasha";
	private final String LASTNAME = "Ketchum";
	
	@Test
	void getAllTest() throws Exception {
		mockMvc.perform(get("/api/v1/person"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[0].firstName").value("John"))
				.andExpect(jsonPath("$.[14].email").value("reg@email.com"))
				.andDo(print());
	}
	
	@Test
	void findByTest() throws Exception {
		mockMvc.perform(get("/api/v1/person/{firstName}/{lastName}", "Lily", "Cooper"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.address").value("489 Manchester St"))
				.andExpect(jsonPath("$.phone").value("841-874-9845"))
				.andExpect(jsonPath("$.email").value("lily@email.com"))
				.andDo(print());
	}
	
	@Test
	void saveTest() throws Exception {
		Person person = new Person("Sasha", "Ketchum", "la petite maison", "Bourg-Palette", "12345", "286-555-9825",
				"master@pokemon.com");
		
		mockMvc.perform(post("/api/v1/person/add").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(person)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lastName").value("Ketchum"))
				.andExpect(jsonPath("$.address").value("la petite maison"))
				.andExpect(jsonPath("$.email").value("master@pokemon.com"))
				.andDo(print());
	}
	
	@Test
	void updateTest() throws Exception {

		Person partialUpdate = new Person(null, null, "updated address", "updated city", "updated zip", null, null);
		
		mockMvc.perform(patch("/api/v1/person/update/{firstName}/{lastName}", FIRSTNAME, LASTNAME).contentType(
								MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(partialUpdate)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lastName").value("Ketchum"))
				.andExpect(jsonPath("$.address").value("updated address"))
				.andExpect(jsonPath("$.zip").value("updated zip"))
				.andDo(print());
	}
	
	@Test
	void deleteBy() throws Exception{
		mockMvc.perform(delete("/api/v1/person/delete/{firstName}/{lastName}", FIRSTNAME, LASTNAME))
				.andExpect(status().isOk())
				.andDo(print());
		
		
					}
	
}
