package com.safetynetalerte.safety.controller;

import com.safetynetalerte.safety.dto.*;
import com.safetynetalerte.safety.service.SpecialsRequestsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SpecialsRequestsController {
	private final static Logger logger = LoggerFactory.getLogger(SpecialsRequestsController.class);
	private final SpecialsRequestsService specialsRequestsService;
	
	public SpecialsRequestsController(SpecialsRequestsService specialsRequestsService) {
		this.specialsRequestsService = specialsRequestsService;
	}
	
	@GetMapping("/childAlert/{address}")
	public ListChildAlertDTO findChildrenAndFamilyAt(
			@PathVariable final String address) throws Exception {
		logger.info("Get all children with their age and their family of the " + address);
		return specialsRequestsService.findChildrenAndFamilyByAddress(address);
	}
	
	@GetMapping("/phoneAlert/{station}")
	public ListPhoneAlertDTO findPhoneOfAllPersonsCoveredByFirestationNumber(@PathVariable final String station){
		logger.info("Get phone number of all persons covered bye the firestation n°" + station);
		return specialsRequestsService.findPhoneOfAllPersonsCoveredByFirestationNumber(station);
	}
	
	@GetMapping("/fire/{address}")
	public ListFirePersonDTO findPersonsAndTheirMedicalrecordsFromAddressAndTheirFirestation(@PathVariable final String address){
		logger.info("Get all persons lived at" + address + " with their medicalrecords and their firestation");
		return specialsRequestsService.findPersonsAndTheirMedicalrecordsAndTheirFirestationByAddress(address);
	}
	@GetMapping("/flood/{station}")
	public ListPersonsCoveredResponseDTO findPersonsAndTheirMedicalrecordsFromAFirestationList(@PathVariable final List<String> station){
		logger.info("Get informations of Persons lived at the firestation(s) address");
		return specialsRequestsService.findPersonsAndTheirMedicalrecordsByAFirestationList(station);
	}
	
	@GetMapping("/personInfo/{firstName}/{lastName}")
	public ListPersonsInfoDTO findPersonAndTheirFamily(@PathVariable final String firstName, @PathVariable final String lastName){
		logger.info("Get informations of a person and get their family");
		return specialsRequestsService.findPersonAndTheirFamily(firstName, lastName);
	}
	
	@GetMapping("/communityEmail/{city}")
	public ListEmailDTO findAllMailOfTheCity(@PathVariable final String city){
		logger.info("Get all the mail address of the persons in " + city);
		return specialsRequestsService.findAllMailOfTheCity(city);
	}
}
