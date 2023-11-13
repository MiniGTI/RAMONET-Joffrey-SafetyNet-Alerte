package com.safetynetalerte.safety.controller;


import com.safetynetalerte.safety.model.Medicalrecord;
import com.safetynetalerte.safety.service.MedicalrecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medicalrecord")
public class MedicalrecordController {
	private final static Logger logger = LoggerFactory.getLogger(MedicalrecordController.class);
	private final MedicalrecordService medicalrecordService;
	
	public MedicalrecordController(MedicalrecordService medicalrecordService) {
		this.medicalrecordService = medicalrecordService;
	}
	
	@GetMapping
	public List<Medicalrecord> getAll() {
		logger.info("Get all medicalrecords from the medicalrecordsList");
		return medicalrecordService.getAll();
	}
	
	@GetMapping("/{firstName}/{lastName}")
	public Medicalrecord findBy(
			@PathVariable
			final String firstName,
			@PathVariable
			final String lastName) {
		logger.info("Medicalrecords of " + firstName + " " + lastName + " are found successfully");
		return medicalrecordService.findBy(firstName, lastName);
	}
	
	@PostMapping("/add")
	public Medicalrecord save(
			@RequestBody Medicalrecord medicalrecord) {
		logger.info("Medicalrecords of " + medicalrecord.getFirstName() + " " + medicalrecord.getLastName() + "  are added successfully in the list of medicalrecords");
		return medicalrecordService.save(medicalrecord);
	}
	
	@PatchMapping("/update/{firstName}/{lastName}")
	public Medicalrecord update(
			@RequestBody Medicalrecord partialUpdate,
			@PathVariable
			final String firstName,
			@PathVariable
			final String lastName) {
		logger.info("Medicalrecords of " + firstName + " " + lastName + " are updated successfully");
		return medicalrecordService.update(partialUpdate, firstName, lastName);
	}
	
	@DeleteMapping("/delete/{firstName}/{lastName}")
	public void deleteBy(
			@PathVariable
			final String firstName,
			@PathVariable
			final String lastName) {
		logger.info("The medicalrecord of " + firstName + " " + lastName + " are removed successfully of the list of medicalrecords");
		medicalrecordService.deleteBy(firstName, lastName);
	}
}
