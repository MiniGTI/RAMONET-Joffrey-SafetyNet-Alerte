package com.safetynetalerte.safety.controller;


import com.safetynetalerte.safety.dto.ListPersonStationNumberDTO;
import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.service.FirestationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/firestation")
public class FirestationController {
	private final static Logger logger = LoggerFactory.getLogger(FirestationController.class);
	private final FirestationService firestationService;
	
	public FirestationController(FirestationService firestationService) {
		this.firestationService = firestationService;
	}
	
	@GetMapping
	public List<Firestation> getAll() {
		logger.info("Get all firestations from the firestationsList");
		return firestationService.getAll();
	}
	
	
	@GetMapping("/firestation/{station}")
	public ListPersonStationNumberDTO findPersonsCoveredByFirestationId(
			@PathVariable
			final String station) throws Exception {
		logger.info("Get all persons covered by the station number: " + station + " and return the number of minor and major persons");
		return firestationService.findPersonsCoveredByFirestationId(station);
	}
	
	@GetMapping("{station}")
	public List<Firestation> findBy(
			@PathVariable
			final String station) {
		logger.info("Get the address of the firestation with station number: " + station);
		return firestationService.findBy(station);
	}
	
	@PostMapping("/add")
	public Firestation save(
			@RequestBody Firestation firestation) {
		logger.info("Firestation of " + firestation.getAddress() + " are added successfully in the list of firestations");
		return firestationService.save(firestation);
	}
	
	@DeleteMapping("/delete/{address}/{station}")
	public void deleteBy(
			@PathVariable
			final String address,
			@PathVariable
			final String station) {
		logger.info("Delete the firestation of the " + address + " with station number: " + station);
		firestationService.deleteBy(address, station);
	}
	
	@PatchMapping("/update/{address}")
	public Firestation update(
			@RequestBody Firestation partialUpdate,
			@PathVariable
			final String address) {
		logger.info("The firestation of " + address + " are updated successfully in the list of firestations");
		return firestationService.update(partialUpdate, address);
	}
}
