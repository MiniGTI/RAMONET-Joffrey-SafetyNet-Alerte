package com.safetynetalerte.safety.net.utilTest.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerte.safety.controller.FirestationController;
import com.safetynetalerte.safety.dto.ListPersonStationNumberDTO;
import com.safetynetalerte.safety.dto.PersonStationNumberDTO;
import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.service.FirestationService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FirestationController.class)
public class FirestationControllerTest {
	
	private final List<Firestation> FIRESTATIONS = new ArrayList<>(
			List.of(new Firestation("599 bvd", "9"), new Firestation("1 25th St", "7"),
					new Firestation("599 1th bvd", "9"), new Firestation("1 20th St", "7")));
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private FirestationService firestationService;
	
	@Test
	void getAllTest() throws Exception {
		when(firestationService.getAll()).thenReturn(FIRESTATIONS);
		List<Firestation> serviceResult = firestationService.getAll();
		
		mockMvc.perform(get("/api/v1/firestation"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(serviceResult.size()))
				.andDo(print());
	}
	
	@Test
	void findByTest() throws Exception {
		String station = "9";
		List<Firestation> firestationFound = new ArrayList<>();
		firestationFound.add(new Firestation("599 bvd", "9"));
		firestationFound.add(new Firestation("599 1th bvd", "9"));
		
		when(firestationService.findBy(station)).thenReturn(firestationFound);
		List<Firestation> serviceResult = firestationService.findBy(station);
		
		mockMvc.perform(get("/api/v1/firestation/9"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()").value(serviceResult.size()))
				.andDo(print());
	}
	
	@Test
	void saveTest() throws Exception {
		Firestation firestation = new Firestation("17 Mont Vert", "6");
		
		when(firestationService.save(firestation)).thenReturn(firestation);
		Firestation serviceResult = firestationService.save(firestation);
		
		mockMvc.perform(post("/api/v1/firestation/add").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(serviceResult)))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	@Test
	void updateTest() throws Exception {
		String address = "599 bvd";
		Firestation partialFirestation = new Firestation(null, "5");
		Firestation updatedFirestation = new Firestation("599 bvd", "5");
		when(firestationService.update(partialFirestation, address)).thenReturn(updatedFirestation);
		Firestation serviceResult = firestationService.update(partialFirestation, address);
		
		mockMvc.perform(patch("/api/v1/firestation/update/{address}", address).contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(partialFirestation)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.address").value(serviceResult.getAddress()))
				.andExpect(jsonPath("$.station").value(serviceResult.getStation()))
				.andDo(print());
	}
	
	@Test
	void deleteTest() throws Exception {
		String address = "address";
		String station = "station";
		doNothing().when(firestationService)
				.deleteBy(address, station);
		
		mockMvc.perform(delete("/api/v1/firestation/delete/{address}/{station}", address, station))
				.andExpect(status().isOk())
				.andDo(print());
	}
	
	
	@Test
	void findPersonsCoveredByStationIdTest() throws Exception {
		ListPersonStationNumberDTO personsList = new ListPersonStationNumberDTO();
		personsList.getPersonStationNumberList()
				.add(new PersonStationNumberDTO("firstName1", "lastName1", "599 bvd", "555-555-5551"));
		personsList.getPersonStationNumberList()
				.add(new PersonStationNumberDTO("firstName2", "lastName2", "599 bvd", "555-555-5552"));
		personsList.setAdult(1);
		personsList.setChild(1);
		String station = "station";
		
		when(firestationService.findPersonsCoveredByFirestationId(station)).thenReturn(personsList);
		ListPersonStationNumberDTO serviceResult = firestationService.findPersonsCoveredByFirestationId(station);
		
		mockMvc.perform(get("/api/v1/firestation/firestation/{station}", station))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.Persons.size()").value(serviceResult.getPersonStationNumberList()
						.size()))
				.andExpect(jsonPath("$.adult").value(serviceResult.getAdult()))
				.andExpect(jsonPath("$.child").value(serviceResult.getChild()))
				
				.andDo(print());
	}
}
