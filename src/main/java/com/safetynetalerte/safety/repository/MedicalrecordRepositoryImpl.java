package com.safetynetalerte.safety.repository;

import com.safetynetalerte.safety.model.Medicalrecord;
import com.safetynetalerte.safety.util.JsonReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * Implementation of the Medicalrecord object repository.
 */
@Repository
public class MedicalrecordRepositoryImpl implements MedicalrecordRepository {
	/**
	 * Logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(MedicalrecordRepositoryImpl.class);
	/**
	 * Creation of the list of Medicalrecord object from the data.json.
	 */
	private final List<Medicalrecord> medicalrecords = JsonReader.medicalrecordsList();
	
	/**
	 * CRUD read all
	 *
	 * @return a list of all Medicalrecords object.
	 */
	@Override
	public List<Medicalrecord> getAll() {
		logger.debug("medicalrecords: " + medicalrecords.size());
		return medicalrecords;
	}
	
	/**
	 * CRUD create
	 *
	 * @param medicalrecord - Medicalrecord object parsed in the body of the /add request.
	 * @return the Medicalrecord object parsed.
	 */
	@Override
	public Medicalrecord save(Medicalrecord medicalrecord) {
		medicalrecords.add(medicalrecord);
		logger.debug("medicalrecord: " + medicalrecord.getFirstName() + " " + medicalrecord.getLastName() + " " +
				medicalrecord.getBirthdate() + " " + medicalrecord.getMedications() + " " +
				medicalrecord.getAllergies());
		return medicalrecord;
	}
	
	/**
	 * CRUD read by id.
	 *
	 * @param firstName id parsed int the @mapping.
	 * @param lastName  id parsed int the @mapping.
	 * @return a Medicalrecord object with firstName and lastName values in their firstName and lastName attributes.
	 */
	@Override
	public Medicalrecord findById(final String firstName, final String lastName) {
		Medicalrecord medicalrecordFound = null;
		for(Medicalrecord medicalrecord : medicalrecords) {
			if(medicalrecord.getFirstName()
					.equals(firstName) && medicalrecord.getLastName()
					.equals(lastName)) {
				medicalrecordFound = medicalrecord;
			}
		}
		
		if(medicalrecordFound != null) {
			logger.debug("medicalrecord found: " + medicalrecordFound.getFirstName() + " " +
					medicalrecordFound.getLastName() + " " + medicalrecordFound.getBirthdate() + " " +
					medicalrecordFound.getMedications() + " " + medicalrecordFound.getAllergies());
		} else {
			logger.error("medicalrecordFound is null");
		}
		return medicalrecordFound;
	}
	
	/**
	 * CRUD delete by id.
	 *
	 * @param firstName id parsed int the @mapping.
	 * @param lastName  id parsed int the @mapping.
	 */
	@Override
	public void deleteBy(final String firstName, final String lastName) {
		medicalrecords.removeIf(medicalrecord -> medicalrecord.getFirstName()
				.equals(firstName) && medicalrecord.getLastName()
				.equals(lastName));
	}
	
	/**
	 * CRUD update by id.
	 *
	 * @param partialUpdate medicalrecord object with attributes have to be modified.
	 * @param firstName     id parsed int the @mapping.
	 * @param lastName      id parsed int the @mapping.
	 * @return the Medicalrecord object modified.
	 */
	@Override
	public Medicalrecord updateBy(Medicalrecord partialUpdate, String firstName, String lastName) {
		Medicalrecord medicalrecordToUpdate = findById(firstName, lastName);
		
		if(Objects.nonNull(partialUpdate.getBirthdate())) {
			medicalrecordToUpdate.setBirthdate(partialUpdate.getBirthdate());
		}
		if(Objects.nonNull(partialUpdate.getMedications())) {
			medicalrecordToUpdate.setMedications(partialUpdate.getMedications());
		}
		if(Objects.nonNull(partialUpdate.getAllergies())) {
			medicalrecordToUpdate.setAllergies(partialUpdate.getAllergies());
		}
		
		for(int i = 0; i < medicalrecords.size(); i++) {
			if(medicalrecords.get(i)
					.getFirstName()
					.equals(firstName) && medicalrecords.get(i)
					.getLastName()
					.equals(lastName)) {
				medicalrecords.set(i, medicalrecordToUpdate);
			}
		}
		logger.debug("medicalrecord updated: " + medicalrecordToUpdate.getFirstName() + " " +
				medicalrecordToUpdate.getLastName() + " " + medicalrecordToUpdate.getBirthdate() + " " +
				medicalrecordToUpdate.getMedications() + " " + medicalrecordToUpdate.getAllergies());
		return medicalrecordToUpdate;
	}
}
