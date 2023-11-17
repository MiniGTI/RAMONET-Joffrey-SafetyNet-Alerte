package com.safetynetalerte.safety.controller;


import com.safetynetalerte.safety.dto.ListPersonStationNumberDTO;
import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.service.FirestationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class of Firestation objects.
 */
@RestController
@RequestMapping("/api/v1/firestation")
public class FirestationController {
    /**
     * Logger.
     */
    private final static Logger logger = LoggerFactory.getLogger(FirestationController.class);
    /**
     * Class of the Firestation service class.
     */
    private final FirestationService firestationService;

    /**
     * Class constructor.
     *
     * @param firestationService Firestation service.
     */
    public FirestationController(FirestationService firestationService) {
        this.firestationService = firestationService;
    }

    /**
     * Call the firstation service getAll method.
     *
     * @return a list of Firestation Object.
     */
    @GetMapping
    public List<Firestation> getAll() {
        logger.info("Get all firestations from the firestationsList");
        return firestationService.getAll();
    }

    /**
     * Get all Person object with the sames address attribute as the address of the Firestation object called by the station number.
     *
     * @param station id of the Firestation objects.
     * @return a ListPersonStationNumberDTO object.
     */
    @GetMapping("/firestation/{station}")
    public ListPersonStationNumberDTO findPersonsCoveredByFirestationId(
            @PathVariable final String station) {
        logger.info("Get all persons covered by the station number: " + station +
                " and return the number of minor and major persons");
        return firestationService.findPersonsCoveredByFirestationId(station);
    }

    /**
     * Call the firstation service findBy method.
     *
     * @param station id of the Firestation objects.
     * @return a list of Firestation Object.
     */
    @GetMapping("{station}")
    public List<Firestation> findBy(
            @PathVariable final String station) {
        logger.info("Get the address of the firestation with station number: " + station);
        return firestationService.findBy(station);
    }

    /**
     * Call the firstation service save method.
     *
     * @param firestation Firestation object parsed in the body of @mapping.
     * @return the Firestation object saved.
     */
    @PostMapping("/add")
    public Firestation save(
            @RequestBody Firestation firestation) {
        logger.info(
                "Firestation of " + firestation.getAddress() + " are added successfully in the list of firestations");
        return firestationService.save(firestation);
    }

    /**
     * Call the firstation service deleteBy method.
     *
     * @param address id of the Firestation objects.
     * @param station id of the Firestation objects.
     */
    @DeleteMapping("/delete/{address}/{station}")
    public void deleteBy(
            @PathVariable final String address,
            @PathVariable final String station) {
        logger.info("Delete the firestation of the " + address + " with station number: " + station);
        firestationService.deleteBy(address, station);
    }

    /**
     * Call the firstation service update method.
     *
     * @param partialUpdate Firestation object with attributes have to be modified.
     * @param address       id of the Firestation objects.
     * @return the Firestation object updated.
     */
    @PatchMapping("/update/{address}")
    public Firestation update(
            @RequestBody Firestation partialUpdate,
            @PathVariable final String address) {
        logger.info("The firestation of " + address + " are updated successfully in the list of firestations");
        return firestationService.update(partialUpdate, address);
    }
}
