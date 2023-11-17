package com.safetynetalerte.safety.service;

import com.safetynetalerte.safety.dto.ListPersonStationNumberDTO;
import com.safetynetalerte.safety.dto.PersonStationNumberDTO;
import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.repository.FirestationRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class of Firestation object.
 * Service to perform the findPersonsCoveredByFirestationId special request.
 */
@Service
public class FirestationService {
    /**
     * Logger.
     */
    private final static Logger logger = LoggerFactory.getLogger(FirestationService.class);
    /**
     * Call of the implementation of the Firestation repository.
     */
    private final FirestationRepositoryImpl firestationRepositoryImpl;
    /**
     * Call of the Address calcul service class.
     */
    @Autowired
    private AddressCalculatorService addressCalculatorService;
    /**
     * Call of the age calcul service class.
     */
    @Autowired
    private AgeCalculatorService ageCalculatorService;

    /**
     * Class constructor
     *
     * @param firestationRepositoryImpl Firestation repository.
     */
    public FirestationService(FirestationRepositoryImpl firestationRepositoryImpl) {
        this.firestationRepositoryImpl = firestationRepositoryImpl;
    }

    /**
     * Call the firstation repository Impl getAll method.
     *
     * @return a list of Firestation Object.
     */
    public List<Firestation> getAll() {
        return firestationRepositoryImpl.getAll();
    }

    /**
     * Call the firstation repository Impl findByStation method.
     *
     * @param station id of the Firestation objects.
     * @return a list of Firestation Object.
     */
    public List<Firestation> findBy(final String station) {
        return firestationRepositoryImpl.findByStation(station);
    }

    /**
     * Call the firstation repository Impl save method.
     *
     * @param firestation Firestation object parsed in the body of @mapping.
     * @return the Firestation object saved.
     */
    public Firestation save(Firestation firestation) {
        return firestationRepositoryImpl.save(firestation);
    }

    /**
     * Call the firstation repository Impl delete method.
     *
     * @param address id of the Firestation objects.
     * @param station id of the Firestation objects.
     */
    public void deleteBy(final String address, final String station) {
        firestationRepositoryImpl.deleteBy(address, station);
    }

    /**
     * Call the firstation repository Impl update method.
     *
     * @param partialupdate Firestation object with attributes have to be modified.
     * @param address       id of the Firestation objects.
     * @return the Firestation object updated.
     */
    public Firestation update(Firestation partialupdate, final String address) {
        return firestationRepositoryImpl.update(partialupdate, address);
    }

    /**
     * Get all Person object with the sames address attribute as the address of the Firestation object called by the station number.
     *
     * @param station id of the Firestation objects.
     * @return a ListPersonStationNumberDTO object.
     */
    public ListPersonStationNumberDTO findPersonsCoveredByFirestationId(String station) {
        List<Person> personsCoveredByAFirestation = addressCalculatorService.personsCoveredByAFirestation(station);
        logger.debug("personsCoveredByAFirestation.size: " + personsCoveredByAFirestation.size());
        ListPersonStationNumberDTO personsInfosFiltered = new ListPersonStationNumberDTO();

        personsCoveredByAFirestation.forEach(person ->
                personsInfosFiltered.getPersonStationNumberList()
                        .add(new PersonStationNumberDTO(person.getFirstName(), person.getLastName(), person.getAddress(),
                                person.getPhone()))
        );
        logger.debug("personsInfosFiltered size: " + personsInfosFiltered.getPersonStationNumberList()
                .size());
        int adult = ageCalculatorService.counterOfAdultListFilter(personsCoveredByAFirestation);
        int minor = personsCoveredByAFirestation.size() - adult;
        logger.debug("minor: " + minor + " adult: " + adult);
        personsInfosFiltered.setAdult(adult);
        personsInfosFiltered.setChild(minor);

        return personsInfosFiltered;
    }
}
