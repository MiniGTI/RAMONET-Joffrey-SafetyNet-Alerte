package com.safetynetalerte.safety.controller;

import com.safetynetalerte.safety.dto.*;
import com.safetynetalerte.safety.service.SpecialsRequestsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class of dto package objects and special requests.
 */
@RestController
@RequestMapping("/api/v1")
public class SpecialsRequestsController {
    /**
     * Logger.
     */
    private final static Logger logger = LoggerFactory.getLogger(SpecialsRequestsController.class);
    /**
     * Call of the specials requests service class.
     */
    private final SpecialsRequestsService specialsRequestsService;

    /**
     * Class constructor.
     *
     * @param specialsRequestsService specials requests service.
     */
    public SpecialsRequestsController(SpecialsRequestsService specialsRequestsService) {
        this.specialsRequestsService = specialsRequestsService;
    }

    /**
     * Return a list with all children with their age and their family at the address parsed.
     * Call the findPhoneOfAllPersonsCoveredByFirestationNumber of specials requests service class.
     *
     * @param address address parsed.
     * @return ListChildAlertDTO  List of ChildAlertListDTO (List of ChildAlertDTO and List of FamilyDTO).
     */
    @GetMapping("/childAlert/{address}")
    public ListChildAlertDTO findChildrenAndFamilyAt(
            @PathVariable final String address) {
        logger.info("Get all children with their age and their family of the " + address);
        return specialsRequestsService.findChildrenAndFamilyByAddress(address);
    }

    /**
     * Return a list of phone numbers of the persons covered by the firestation with the station id parsed.
     * Call the findPhoneOfAllPersonsCoveredByFirestationNumber of specials requests service class.
     *
     * @param station firestation id parsed.
     * @return ListPhoneAlertDTO  List of PhoneAlertDTO.
     */
    @GetMapping("/phoneAlert/{station}")
    public ListPhoneAlertDTO findPhoneOfAllPersonsCoveredByFirestationNumber(
            @PathVariable final String station) {
        logger.info("Get phone number of all persons covered bye the firestation n°" + station);
        return specialsRequestsService.findPhoneOfAllPersonsCoveredByFirestationNumber(station);
    }

    /**
     * Return a list of Persons with their medicalrecords and their firestation with the address parsed.
     * Call the findPersonsAndTheirMedicalrecordsAndTheirFirestationByAddress of specials requests service class.
     *
     * @param address address parsed.
     * @return ListFirePersonDTO  List of PersonAndMedicalrecordsDTO and a String of Firestation station id.
     */
    @GetMapping("/fire/{address}")
    public ListFirePersonDTO findPersonsAndTheirMedicalrecordsFromAddressAndTheirFirestation(
            @PathVariable final String address) {
        logger.info("Get all persons lived at" + address + " with their medicalrecords and their firestation");
        return specialsRequestsService.findPersonsAndTheirMedicalrecordsAndTheirFirestationByAddress(address);
    }

    /**
     * Return a list of Persons with their Medicalrecords covered by the list of Firestation id parsed.
     * Call the findPersonsAndTheirMedicalrecordsByAFirestationList of specials requests service class.
     *
     * @param station list of firestation id parsed.
     * @return ListPersonsCoveredResponseDTO - List of ListPersonsCoveredDTO (String address, List of PersonAndMedicalrecordsDTO).
     */
    @GetMapping("/flood/{station}")
    public ListPersonsCoveredResponseDTO findPersonsAndTheirMedicalrecordsFromAFirestationList(
            @PathVariable final List<String> station) {
        logger.info("Get informations of Persons lived at the firestation(s) address");
        return specialsRequestsService.findPersonsAndTheirMedicalrecordsByAFirestationList(station);
    }

    /**
     * Return a list of the person asked and their age and medicalrecords with all persons sharing the same lastName by the first and last Name parsed.
     * Call the findPersonAndTheirFamily of specials requests service class.
     *
     * @param firstName firstName of the Person finded.
     * @param lastName  lastName of the Person finded.
     * @return ListPersonsInfoDTO  List of PersonInfosDTO.
     */
    @GetMapping("/personInfo/{firstName}/{lastName}")
    public ListPersonsInfoDTO findPersonAndTheirFamily(
            @PathVariable final String firstName,
            @PathVariable final String lastName) {
        logger.info("Get informations of a person and get their family");
        return specialsRequestsService.findPersonAndTheirFamily(firstName, lastName);
    }

    /**
     * Return a list with all mail address of the persons living in the city parsed.
     * Call the findAllMailOfTheCity of specials requests service class.
     *
     * @param city city name parsed.
     * @return ListEmailDTO  List of EmailDTO.
     */
    @GetMapping("/communityEmail/{city}")
    public ListEmailDTO findAllMailOfTheCity(
            @PathVariable final String city) {
        logger.info("Get all the mail address of the persons in " + city);
        return specialsRequestsService.findAllMailOfTheCity(city);
    }
}
