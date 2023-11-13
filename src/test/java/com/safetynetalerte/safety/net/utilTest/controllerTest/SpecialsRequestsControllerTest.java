package com.safetynetalerte.safety.net.utilTest.controllerTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynetalerte.safety.controller.SpecialsRequestsController;
import com.safetynetalerte.safety.dto.*;
import com.safetynetalerte.safety.service.SpecialsRequestsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SpecialsRequestsController.class)
public class SpecialsRequestsControllerTest {
	
	private ObjectMapper objectMapper;
	
	@MockBean
	private SpecialsRequestsService specialsRequestsService;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	void findChildrenAndFamilyAt() throws Exception {
		String address = "address";
		ListChildAlertDTO listChildAlertDTO = new ListChildAlertDTO();
		listChildAlertDTO.getChildAlertList()
				.add(new ChildAlertDTO("firstName1", "lastName1", 12));
		listChildAlertDTO.getChildAlertList()
				.add(new ChildAlertDTO("firstName2", "lastName1", 4));
		listChildAlertDTO.getFamilyList()
				.add(new FamilyDTO("firstName3", "lastName1"));
		listChildAlertDTO.getFamilyList()
				.add(new FamilyDTO("firstName4", "lastName1"));
		
		when(specialsRequestsService.findChildrenAndFamilyByAddress(address)).thenReturn(listChildAlertDTO);
		
		mockMvc.perform(get("/api/v1/childAlert/address", address))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.Children.size()").value(listChildAlertDTO.getChildAlertList()
						.size()))
				.andExpect(jsonPath("$.Family.size()").value(listChildAlertDTO.getFamilyList()
						.size()))
				.andDo(print());
	}
	
	@Test
	void findPhoneOfAllPersonsCoveredByFirestationNumberTest() throws Exception {
		String station = "station";
		ListPhoneAlertDTO listPhoneAlertDTO = new ListPhoneAlertDTO();
		listPhoneAlertDTO.getPhoneAlertList()
				.add(new PhoneAlertDTO("111-111-1111"));
		listPhoneAlertDTO.getPhoneAlertList()
				.add(new PhoneAlertDTO("222-222-2222"));
		listPhoneAlertDTO.getPhoneAlertList()
				.add(new PhoneAlertDTO("333-333-3333"));
		
		when(specialsRequestsService.findPhoneOfAllPersonsCoveredByFirestationNumber(station)).thenReturn(listPhoneAlertDTO);
		
		mockMvc.perform(get("/api/v1/phoneAlert/{station}", station))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.phoneAlertList.size()").value(listPhoneAlertDTO.getPhoneAlertList()
						.size()))
				.andDo(print());
	}
	
	@Test
	void findPersonsAndTheirMedicalrecordsFromAddressAndTheirFirestationTest() throws Exception {
		String address = "address";
		ListFirePersonDTO listFirePersonDTO = new ListFirePersonDTO();
		List<String> medications = new ArrayList<>(List.of("medication1", "medication2"));
		List<String> allergies = new ArrayList<>(List.of("allergie1", "allergie2"));
		MedicalrecordDTO medicalrecordDTO = new MedicalrecordDTO(medications, allergies);
		listFirePersonDTO.getPersonFireAlertList()
				.add(new PersonAndMedicalrecordsDTO("firstName1", "lastName1", "111-111-1111", 22, medicalrecordDTO));
		listFirePersonDTO.getPersonFireAlertList()
				.add(new PersonAndMedicalrecordsDTO("firstName2", "lastName2", "222-222-2222", 17, medicalrecordDTO));
		listFirePersonDTO.setFirestationNumber("9");
		
		when(specialsRequestsService.findPersonsAndTheirMedicalrecordsAndTheirFirestationByAddress(address)).thenReturn(listFirePersonDTO);
		
		mockMvc.perform(get("/api/v1/fire/{address}", address))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.Persons.size()").value(listFirePersonDTO.getPersonFireAlertList()
						.size()))
				.andExpect(jsonPath("$.firestationNumber").value(listFirePersonDTO.getFirestationNumber()))
				.andDo(print());
	}
	
	@Test
	void findPersonsAndTheirMedicalrecordsFromAFirestationListTest() throws Exception {
		List<String> station = new ArrayList<>(List.of("1", "2"));
		String argURI = "1,2";
		List<String> medications = new ArrayList<>(List.of("medication1", "medication2"));
		List<String> allergies = new ArrayList<>(List.of("allergie1", "allergie2"));
		MedicalrecordDTO medicalrecordDTO = new MedicalrecordDTO(medications, allergies);
		ListPersonsCoveredDTO listPersonsCoveredDTO1 = new ListPersonsCoveredDTO();
		listPersonsCoveredDTO1.getPersonAndMedicalrecordsList()
				.add(new PersonAndMedicalrecordsDTO("firstName1", "lastName1", "111-111-1111", 22, medicalrecordDTO));
		listPersonsCoveredDTO1.getPersonAndMedicalrecordsList()
				.add(new PersonAndMedicalrecordsDTO("firstName2", "lastName2", "222-222-2222", 17, medicalrecordDTO));
		listPersonsCoveredDTO1.setAddress("address1");
		ListPersonsCoveredDTO listPersonsCoveredDTO2 = new ListPersonsCoveredDTO();
		listPersonsCoveredDTO2.getPersonAndMedicalrecordsList()
				.add(new PersonAndMedicalrecordsDTO("firstName1", "lastName1", "111-111-1111", 22, medicalrecordDTO));
		listPersonsCoveredDTO2.getPersonAndMedicalrecordsList()
				.add(new PersonAndMedicalrecordsDTO("firstName2", "lastName2", "222-222-2222", 17, medicalrecordDTO));
		listPersonsCoveredDTO2.setAddress("address2");
		ListPersonsCoveredResponseDTO listPersonsCoveredResponseDTO = new ListPersonsCoveredResponseDTO();
		listPersonsCoveredResponseDTO.getListPersonsCoveredList()
				.add(listPersonsCoveredDTO1);
		listPersonsCoveredResponseDTO.getListPersonsCoveredList()
				.add(listPersonsCoveredDTO2);
		
		when(specialsRequestsService.findPersonsAndTheirMedicalrecordsByAFirestationList(station)).thenReturn(listPersonsCoveredResponseDTO);
		
		mockMvc.perform(get("/api/v1/flood/{station}", argURI))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.PersonsCovered.size()").value(listPersonsCoveredResponseDTO.getListPersonsCoveredList()
						.size()))
				.andExpect(jsonPath("$.PersonsCovered[0].address").value(listPersonsCoveredDTO1.getAddress()))
				.andExpect(jsonPath("$.PersonsCovered[1].address").value(listPersonsCoveredDTO2.getAddress()))
				.andDo(print());
	}
	
	@Test
	void findPersonAndTheirFamilyTest() throws Exception{
		String firstName = "firstName";
		String lastName = "lastName";
		ListPersonsInfoDTO listPersonsInfoDTO = new ListPersonsInfoDTO();
		List<String> medications = new ArrayList<>(List.of("medication1", "medication2"));
		List<String> allergies = new ArrayList<>(List.of("allergie1", "allergie2"));
		MedicalrecordDTO medicalrecordDTO = new MedicalrecordDTO(medications, allergies);
		listPersonsInfoDTO.getPersonsInfoList().add(new PersonInfosDTO("firstName1", "lastName1", " 42 bvd 1st", 35, "test@test.net", medicalrecordDTO));
		listPersonsInfoDTO.getPersonsInfoList().add(new PersonInfosDTO("firstName2", "lastName1", " 42 bvd 1st", 26, "test1@test.net", medicalrecordDTO));
		
		when(specialsRequestsService.findPersonAndTheirFamily(firstName, lastName)).thenReturn(listPersonsInfoDTO);
	
		mockMvc.perform(get("/api/v1/personInfo/{firstName}/{lastName}", firstName, lastName))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.Persons.size()").value(listPersonsInfoDTO.getPersonsInfoList().size()))
				.andDo(print());
	
	}
	
	@Test
	void findAllMailOfTheCityTest() throws Exception{
		String city = "city";
		ListEmailDTO listEmailDTO = new ListEmailDTO();
		listEmailDTO.getEmailList().add(new EmailDTO("test1@test.com"));
		listEmailDTO.getEmailList().add(new EmailDTO("test2@test.com"));
		
		when(specialsRequestsService.findAllMailOfTheCity(city)).thenReturn(listEmailDTO);
		
		mockMvc.perform(get("/api/v1/communityEmail/{city}", city))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.emailList.size()").value(listEmailDTO.getEmailList().size()))
				.andDo(print());
	}
}
