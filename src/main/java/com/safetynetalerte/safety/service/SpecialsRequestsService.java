package com.safetynetalerte.safety.service;

import com.safetynetalerte.safety.dto.*;
import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.repository.FirestationRepositoryImpl;
import com.safetynetalerte.safety.repository.PersonRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class to perform specials request calculs.
 */
@Service
public class SpecialsRequestsService {
    /**
     * Logger.
     */
    private final static Logger logger = LoggerFactory.getLogger(SpecialsRequestsService.class);
    /**
     * Call of the implementation of the Person repository.
     */
    private final PersonRepositoryImpl personRepositoryImpl;
    /**
     * Call of the implementation of the Firestation repository.
     */
    private final FirestationRepositoryImpl firestationRepositoryImpl;
    /**
     * Call of the age calcul service class.
     */
    @Autowired
    private AgeCalculatorService ageCalculatorService;
    /**
     * Call of the Address calcul service class.
     */
    @Autowired
    private MedicalrecordService medicalrecordService;

    /**
     * Class constructor.
     *
     * @param personRepositoryImpl      Person repository.
     * @param firestationRepositoryImpl Firestation repository.
     */
    public SpecialsRequestsService(PersonRepositoryImpl personRepositoryImpl,
                                   FirestationRepositoryImpl firestationRepositoryImpl) {
        this.personRepositoryImpl = personRepositoryImpl;
        this.firestationRepositoryImpl = firestationRepositoryImpl;
    }

    /**
     * Return a list with all children with their age and their family at the address parsed.
     *
     * @param address address parsed.
     * @return ListChildAlertDTO  List of ChildAlertListDTO (List of ChildAlertDTO and List of FamilyDTO).
     */
    public ListChildAlertDTO findChildrenAndFamilyByAddress(String address) {
        List<Person> personsFindByAddress = personRepositoryImpl.findByAddress(address);
        logger.debug("personsFindByAddress size: " + personsFindByAddress.size());
        List<Person> minorPersonsList = ageCalculatorService.adultListFilter(personsFindByAddress);
        logger.debug("minorPersonsList size: " + minorPersonsList.size());
        personsFindByAddress.removeAll(minorPersonsList);
        logger.debug("personsFindByAddress after adult filter size: " + personsFindByAddress.size());
        ListChildAlertDTO childAlertList = new ListChildAlertDTO();

        minorPersonsList.forEach(child -> childAlertList.getChildAlertList()
                .add(new ChildAlertDTO(child.getFirstName(), child.getLastName(),
                        ageCalculatorService.ageCalculator(child.getFirstName(), child.getLastName()))));

        personsFindByAddress.forEach(person -> childAlertList.getFamilyList()
                .add(new FamilyDTO(person.getFirstName(), person.getLastName())));
        logger.debug("ChildAlertList size: " + childAlertList.getChildAlertList()
                .size() + " FamilyList size: " + childAlertList.getFamilyList()
                .size());
        return childAlertList;
    }

    /**
     * Return a list of phone numbers of the persons covered by the firestation with the station id parsed.
     *
     * @param station firestation id parsed.
     * @return ListPhoneAlertDTO  List of PhoneAlertDTO.
     */
    public ListPhoneAlertDTO findPhoneOfAllPersonsCoveredByFirestationNumber(String station) {
        List<Firestation> firestations = firestationRepositoryImpl.findByStation(station);
        List<Person> persons = new ArrayList<>();
        ListPhoneAlertDTO phoneAlertList = new ListPhoneAlertDTO();

        firestations.forEach(
                firestation -> persons.addAll(personRepositoryImpl.findByAddress(firestation.getAddress())));

        persons.forEach(person -> phoneAlertList.getPhoneAlertList()
                .add(new PhoneAlertDTO(person.getPhone())));
        // maybe need a double filter
        return phoneAlertList;
    }

    /**
     * Return a list of Persons with their medicalrecords and their firestation with the address parsed.
     *
     * @param address address parsed.
     * @return ListFirePersonDTO  List of PersonAndMedicalrecordsDTO and a String of Firestation station id.
     */
    public ListFirePersonDTO findPersonsAndTheirMedicalrecordsAndTheirFirestationByAddress(String address) {
        List<Person> personsByAddressList = personRepositoryImpl.findByAddress(address);
        ListFirePersonDTO firePersonList = new ListFirePersonDTO();

        personsByAddressList.forEach(person -> firePersonList.getPersonFireAlertList()
                .add(new PersonAndMedicalrecordsDTO(person.getFirstName(), person.getLastName(), person.getPhone(),
                        ageCalculatorService.ageCalculator(person.getFirstName(), person.getLastName()),
                        medicalrecordService.medicalrecordDTOGenerator(person.getFirstName(), person.getLastName()))));
        firePersonList.setFirestationNumber(firestationRepositoryImpl.findByAddress(address)
                .getStation());
        return firePersonList;
    }

    /**
     * Return a list of Persons with their Medicalrecords covered by the list of Firestation id parsed.
     *
     * @param station list of firestation id parsed.
     * @return ListPersonsCoveredResponseDTO - List of ListPersonsCoveredDTO (String address, List of PersonAndMedicalrecordsDTO).
     */
    public ListPersonsCoveredResponseDTO findPersonsAndTheirMedicalrecordsByAFirestationList(List<String> station) {
        ListPersonsCoveredResponseDTO responseList = new ListPersonsCoveredResponseDTO();
        List<Firestation> firestations = new ArrayList<>();

        station.forEach(stationId -> firestations.addAll(firestationRepositoryImpl.findByStation(stationId)));

        firestations.forEach(firestation -> {
            List<Person> personsByAddressList = personRepositoryImpl.findByAddress(firestation.getAddress());

            ListPersonsCoveredDTO personsList = new ListPersonsCoveredDTO();
            personsList.setAddress(firestation.getAddress());
            personsByAddressList.forEach(person -> personsList.getPersonAndMedicalrecordsList()
                    .add(new PersonAndMedicalrecordsDTO(person.getFirstName(), person.getLastName(), person.getPhone(),
                            ageCalculatorService.ageCalculator(person.getFirstName(), person.getLastName()),
                            medicalrecordService.medicalrecordDTOGenerator(person.getFirstName(),
                                    person.getLastName()))));
            responseList.getListPersonsCoveredList()
                    .add(personsList);
        });

        return responseList;
    }

    /**
     * Return a list of the person asked and their age and medicalrecords with all persons sharing the same lastName by the first and last Name parsed.
     *
     * @param firstName firstName of the Person finded.
     * @param lastName  lastName of the Person finded.
     * @return ListPersonsInfoDTO  List of PersonInfosDTO.
     */
    public ListPersonsInfoDTO findPersonAndTheirFamily(String firstName, String lastName) {
        List<Person> persons = personRepositoryImpl.getAll();
        ListPersonsInfoDTO resultList = new ListPersonsInfoDTO();

        persons.forEach(person -> {
            if (person.getLastName()
                    .equals(lastName)) {
                resultList.getPersonsInfoList()
                        .add(new PersonInfosDTO(person.getFirstName(), person.getLastName(), person.getAddress(),
                                ageCalculatorService.ageCalculator(person.getFirstName(), person.getLastName()),
                                person.getEmail(), medicalrecordService.medicalrecordDTOGenerator(person.getFirstName(),
                                person.getLastName())));
            }
        });

        return resultList;
    }

    /**
     * Return a list with all mail address of the persons living in the city parsed.
     *
     * @param city city name parsed.
     * @return ListEmailDTO  List of EmailDTO.
     */
    public ListEmailDTO findAllMailOfTheCity(String city) {
        List<Person> persons = personRepositoryImpl.getAll();
        ListEmailDTO resultList = new ListEmailDTO();
        persons.forEach(person -> {
            if (person.getCity()
                    .equals(city)) {
                resultList.getEmailList()
                        .add(new EmailDTO(person.getEmail()));
            }
        });
        return resultList;
    }
}