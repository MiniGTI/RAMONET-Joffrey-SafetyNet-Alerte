package com.safetynetalerte.safety.net.utilTest.integrationsTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SpecialsRequestsTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private final String ADDRESS = "1509 Culver St";
	private final String STATION = "3";
	
	@Test
	void findChildrenAndFamilyAtTest() throws Exception{
		mockMvc.perform(get("/api/v1/childAlert/{address}", ADDRESS))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.Children.[1].age").value(6))
				.andExpect(jsonPath("$.Family.[2].firstName").value("Felicia"))
				.andDo(print());
	}
	
	@Test
	void findPhoneOfAllPersonsCoveredByFirestationNumberTest() throws Exception{
		mockMvc.perform(get("/api/v1/phoneAlert/{station}", STATION))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.phoneAlertList.[4].phone").value("841-874-6544"))
				.andDo(print());
	}
	
	@Test
	void findPersonsAndTheirMedicalrecordsFromAddressAndTheirFirestationTest() throws Exception{
		mockMvc.perform(get("/api/v1/fire/{address}", ADDRESS))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firestationNumber").value("3"))
				.andExpect(jsonPath("$.Persons.[4].age").value(37))
				.andDo(print());
	}
	
	@Test
	void findPersonsAndTheirMedicalrecordsFromAFirestationListTest() throws Exception{
		mockMvc.perform(get("/api/v1/flood/{station}", STATION))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.PersonsCovered.[1].address").value("834 Binoc Ave"))
				.andExpect(jsonPath("$.PersonsCovered.[2].persons.[1].lastName").value("Ferguson"))
				.andDo(print());
	}
	
	@Test
	void findPersonAndTheirFamilyTest() throws Exception{
		mockMvc.perform(get("/api/v1/personInfo/{firstName}/{lastName}", "Felicia", "Boyd"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.Persons.[2].firstName").value("Tenley"))
				.andExpect(jsonPath("$.Persons.[5].age").value(58))
				.andDo(print());
	}
	
	@Test
	void findAllMailOfTheCityTest() throws Exception{
		mockMvc.perform(get("/api/v1/communityEmail/{city}", "Culver"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.emailList.[3].email").value("jaboyd@email.com"))
				.andDo(print());
	}
}
