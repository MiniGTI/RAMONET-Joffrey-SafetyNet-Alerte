package com.safetynetalerte.safety.service;

import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.repository.FirestationRepositoryImpl;
import com.safetynetalerte.safety.repository.PersonRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * To create a list of Person object following the address of a Firestation object.
 */
@Service
public class AddressCalculatorService {
    /**
     * Logger.
     */
    private final static Logger logger = LoggerFactory.getLogger(AddressCalculatorService.class);
    /**
     * Call of the implementation of the Firestation repository.
     */
    private final FirestationRepositoryImpl firestationRepositoryImpl;
    /**
     * Call of the implementation of the Person repository.
     */
    private final PersonRepositoryImpl personRepositoryImpl;

    /**
     * Class constructor.
     *
     * @param firestationRepositoryImpl Firestation repository.
     * @param personRepositoryImpl      Person repository.
     */
    public AddressCalculatorService(FirestationRepositoryImpl firestationRepositoryImpl,
                                    PersonRepositoryImpl personRepositoryImpl) {
        this.firestationRepositoryImpl = firestationRepositoryImpl;
        this.personRepositoryImpl = personRepositoryImpl;
    }

    /**
     * Find all Person object with the same address of the station number parsed.
     *
     * @param station - id of the Firestation object parsed in the @mapping.
     * @return List of Person Object.
     */
    public List<Person> personsCoveredByAFirestation(String station) {
        List<Firestation> firestations = firestationRepositoryImpl.findByStation(station);
        logger.debug("firestations size: " + firestations.size());
        List<Person> persons = personRepositoryImpl.getAll();
        List<Person> personsCoveredList = new ArrayList<>();

        for (Firestation firestation : firestations)
            for (Person person : persons) {
                if (person.getAddress()
                        .equals(firestation.getAddress())) {
                    personsCoveredList.add(person);
                }
            }
        if (personsCoveredList.isEmpty()) {
            logger.error("personsCoveredList is empty");
        }
        return personsCoveredList;
    }
}
