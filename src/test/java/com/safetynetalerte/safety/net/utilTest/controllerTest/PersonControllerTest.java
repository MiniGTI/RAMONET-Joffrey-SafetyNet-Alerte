package com.safetynetalerte.safety.net.utilTest.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerte.safety.controller.PersonController;
import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.service.PersonService;
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


@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {
	
	@Autowired
	ObjectMapper objectMapper;
	List<Person> persons = new ArrayList<>(
			List.of(new Person("person1", "person1", "1 test", "test", "12345", "123-456-7891", "test@test.com"),
					new Person("person2", "person2", "2 test", "test", "12346", "123-456-7892", "test2@test.com")));
	Person person = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private PersonService personService;
	
	@Test
	void getAllTest() throws Exception {
		when(personService.getAll()).thenReturn(persons);
		List<Person> serviceResult = personService.getAll();
		
		mockMvc.perform(get("/api/v1/person"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(serviceResult.size()))
				.andDo(print());
	}
	
	@Test
	void findByTest() throws Exception {
		String firstName = "firstName";
		String lastName = "lastName";
		when(personService.findBy(firstName, lastName)).thenReturn(person);
		Person serviceResult = personService.findBy(firstName, lastName);
		
		mockMvc.perform(get("/api/v1/person/{firstName}/{lastName}", firstName, lastName))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(serviceResult.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(serviceResult.getLastName()))
				.andExpect(jsonPath("$.address").value(serviceResult.getAddress()))
				.andExpect(jsonPath("$.city").value(serviceResult.getCity()))
				.andExpect(jsonPath("$.zip").value(serviceResult.getZip()))
				.andExpect(jsonPath("$.phone").value(serviceResult.getPhone()))
				.andExpect(jsonPath("$.email").value(serviceResult.getEmail()))
				.andDo(print());
	}
	
	@Test
	void saveTest() throws Exception {
		Person person = new Person("firstName", "lastName", "address", "city", "zip", "phone", "email");
		
		when(personService.save(person)).thenReturn(person);
		Person serviceResult = personService.save(person);
		
		mockMvc.perform(post("/api/v1/person/add").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(serviceResult)))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	@Test
	void updateTest() throws Exception {
		String firstName = "firstName";
		String lastName = "lastName";
		Person partialPerson = new Person(null, null, "modified address", null, null, "modified phone", null);
		Person updatedPerson =
				new Person("firstName", "lastName", "modified address", "city", "zip", "modified phone", "email");
		when(personService.update(partialPerson, firstName, lastName)).thenReturn(updatedPerson);
		Person serviceResult = personService.update(partialPerson, firstName, lastName);
		
		mockMvc.perform(patch("/api/v1/person/update/{firstName}/{lastName}", firstName, lastName).contentType(
								MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(partialPerson)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value(serviceResult.getFirstName()))
				.andExpect(jsonPath("$.lastName").value(serviceResult.getLastName()))
				.andExpect(jsonPath("$.address").value(serviceResult.getAddress()))
				.andExpect(jsonPath("$.phone").value(serviceResult.getPhone()))
				.andDo(print());
	}
	
	@Test
	void deleteTest() throws Exception {
		String firstName = "firstName";
		String lastName = "lastName";
		doNothing().when(personService)
				.deleteBy(firstName, lastName);
		
		personService.deleteBy(firstName, lastName);
		
		mockMvc.perform(delete("/api/v1/person/delete/{firstName}/{lastName}", firstName, lastName))
				.andExpect(status().isOk())
				.andDo(print());
	}
}
