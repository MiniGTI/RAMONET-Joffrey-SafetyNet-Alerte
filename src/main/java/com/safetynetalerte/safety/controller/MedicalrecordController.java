package com.safetynetalerte.safety.controller;


import com.safetynetalerte.safety.model.Medicalrecord;
import com.safetynetalerte.safety.service.MedicalrecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class of Medicalrecord objects.
 */
@RestController
@RequestMapping("/api/v1/medicalrecord")
public class MedicalrecordController {
    /**
     * Logger
     */
    private final static Logger logger = LoggerFactory.getLogger(MedicalrecordController.class);
    /**
     * Class of the Medicalrecord service class.
     */
    private final MedicalrecordService medicalrecordService;

    /**
     * Class constructor.
     *
     * @param medicalrecordService Medicalrecord service.
     */
    public MedicalrecordController(MedicalrecordService medicalrecordService) {
        this.medicalrecordService = medicalrecordService;
    }

    /**
     * Call the medicalrecord service getAll method.
     *
     * @return a list of Medicalrecord Object.
     */
    @GetMapping
    public List<Medicalrecord> getAll() {
        logger.info("Get all medicalrecords from the medicalrecordsList");
        return medicalrecordService.getAll();
    }

    /**
     * Call the medicalrecord service findBy method.
     *
     * @param firstName id of the Medicalrecord objects.
     * @param lastName  id of the Medicalrecord objects.
     * @return a Medicalrecord Object.
     */
    @GetMapping("/{firstName}/{lastName}")
    public Medicalrecord findBy(
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        logger.info("Medicalrecords of " + firstName + " " + lastName + " are found successfully");
        return medicalrecordService.findBy(firstName, lastName);
    }

    /**
     * Call the medicalrecord service save method.
     *
     * @param medicalrecord Medicalrecord object parsed in the body of @mapping.
     * @return the Medicalrecord object saved.
     */
    @PostMapping("/add")
    public Medicalrecord save(
            @RequestBody Medicalrecord medicalrecord) {
        logger.info("Medicalrecords of " + medicalrecord.getFirstName() + " " + medicalrecord.getLastName() +
                "  are added successfully in the list of medicalrecords");
        return medicalrecordService.save(medicalrecord);
    }

    /**
     * Call the medicalrecord service update method.
     *
     * @param partialUpdate Medicalrecord object with attributes have to be modified.
     * @param firstName     id of the Medicalrecord objects.
     * @param lastName      id of the Medicalrecord objects.
     * @return the Medicalrecord object updated.
     */
    @PatchMapping("/update/{firstName}/{lastName}")
    public Medicalrecord update(
            @RequestBody Medicalrecord partialUpdate,
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        logger.info("Medicalrecords of " + firstName + " " + lastName + " are updated successfully");
        return medicalrecordService.update(partialUpdate, firstName, lastName);
    }

    /**
     * Call the medicalrecord service deleteBy method.
     *
     * @param firstName id of the Medicalrecord objects.
     * @param lastName  id of the Medicalrecord objects.
     */
    @DeleteMapping("/delete/{firstName}/{lastName}")
    public void deleteBy(
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        logger.info("The medicalrecord of " + firstName + " " + lastName +
                " are removed successfully of the list of medicalrecords");
        medicalrecordService.deleteBy(firstName, lastName);
    }
}
