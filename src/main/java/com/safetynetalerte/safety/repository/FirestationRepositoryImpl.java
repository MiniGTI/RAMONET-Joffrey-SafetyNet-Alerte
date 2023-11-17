package com.safetynetalerte.safety.repository;

import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.util.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Implementation of the Firestation object repository.
 */
@Repository
public class FirestationRepositoryImpl implements FirestationRepository {
    /**
     * Logger.
     */
    private final static Logger logger = LoggerFactory.getLogger(FirestationRepositoryImpl.class);
    /**
     * Creation of the list of Firestation object from the data.json.
     */
    private final List<Firestation> firestations = JsonReader.firestationsList();

    /**
     * CRUD read all.
     *
     * @return a list of all Firestation object.
     */
    @Override
    public List<Firestation> getAll() {
        logger.debug("firestations: " + firestations.size());
        return firestations;
    }

    /**
     * CRUD create.
     *
     * @param firestation Firestation object parsed in the body of the /add request.
     * @return the Firestation object parsed.
     */
    @Override
    public Firestation save(Firestation firestation) {
        firestations.add(firestation);
        logger.debug("firestation: " + firestation.getAddress() + " " + firestation.getStation());
        return firestation;
    }

    /**
     * CRUD read by id.
     *
     * @param address id parsed int the @mapping.
     * @param station id parsed int the @mapping.
     * @return null cause method never used.
     */
    @Override
    public Firestation findById(String address, String station) {
        return null;
    }

    /**
     * CRUD read by id.
     *
     * @param station id parsed in the @mapping.
     * @return a list of Firestation object with station value in their station attribute.
     */
    public List<Firestation> findByStation(String station) {
        List<Firestation> firestationList = new ArrayList<>();
        for (Firestation firestation : firestations) {
            if (firestation.getStation()
                    .equals(station)) {
                firestationList.add(firestation);
            }
        }
        logger.debug("firestationList: " + firestationList.size());
        return firestationList;
    }

    /**
     * CRUD read by id.
     *
     * @param address id parsed int the @mapping.
     * @return a Firestation object with address value in their address attribute.
     */
    public Firestation findByAddress(String address) {
        Firestation firestationFound = null;
        for (Firestation firestation : firestations) {
            if (firestation.getAddress()
                    .equals(address)) {
                firestationFound = firestation;
            }
        }
        if (firestationFound != null) {
            logger.debug("firestationFound: " + firestationFound.getAddress() + " " + firestationFound.getStation());
        } else {
            logger.error("firestationFound is null");
        }
        return firestationFound;
    }

    /**
     * CRUD delete by id.
     *
     * @param address id parsed int the @mapping.
     * @param station id parsed int the @mapping.
     */
    @Override
    public void deleteBy(String address, String station) {
        firestations.removeIf(firestation -> firestation.getAddress()
                .equals(address) && firestation.getStation()
                .equals(station));
    }

    /**
     * CRUD update by id.
     *
     * @param firestation id parsed int the @mapping.
     * @param key0        id parsed int the @mapping.
     * @param key1        id parsed int the @mapping.
     * @return null cause never used.
     */
    @Override
    public Firestation updateBy(Firestation firestation, String key0, String key1) {
        return null;
    }

    /**
     * CRUD update by id.
     *
     * @param partialUpdate Firestation object with attributes have to be modified.
     * @param address       id parsed int the @mapping.
     * @return the Firestation object modified.
     */
    public Firestation update(Firestation partialUpdate, String address) {
        Firestation firestationToUpdate = new Firestation();

        firestationToUpdate.setAddress(address);
        if (Objects.nonNull(partialUpdate.getStation())) {
            firestationToUpdate.setStation(partialUpdate.getStation());
        }
        for (int i = 0; i < firestations.size(); i++) {
            if (firestations.get(i)
                    .getAddress()
                    .equals(address)) {
                firestations.set(i, firestationToUpdate);
            }
        }
        logger.debug(
                "firestation to update: " + firestationToUpdate.getAddress() + " " + firestationToUpdate.getStation());
        return firestationToUpdate;
    }
}
