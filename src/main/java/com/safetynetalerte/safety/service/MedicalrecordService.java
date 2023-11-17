package com.safetynetalerte.safety.service;

import com.safetynetalerte.safety.dto.MedicalrecordDTO;
import com.safetynetalerte.safety.model.Medicalrecord;
import com.safetynetalerte.safety.repository.MedicalrecordRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class of Medicalrecord object.
 * Service to generate the medicalrecordDTO object.
 */
@Service
public class MedicalrecordService {
	/**
	 * Call of the implementation of the Medicalrecord repository.
	 */
	private final MedicalrecordRepositoryImpl medicalrecordRepositoryImpl;
	
	/**
	 * Class constructor.
	 *
	 * @param medicalrecordRepositoryImpl Medicalrecord repository.
	 */
	public MedicalrecordService(MedicalrecordRepositoryImpl medicalrecordRepositoryImpl) {
		this.medicalrecordRepositoryImpl = medicalrecordRepositoryImpl;
	}
	
	/**
	 * Call the medicalrecord repository Impl getAll method.
	 *
	 * @return a list of Medicalrecord Object.
	 */
	public List<Medicalrecord> getAll() {
		return medicalrecordRepositoryImpl.getAll();
	}
	
	/**
	 * Call the medicalrecord repository Impl findBy method.
	 *
	 * @param firstName id of the Medicalrecord objects.
	 * @param lastName  id of the Medicalrecord objects.
	 * @return a Medicalrecord Object.
	 */
	public Medicalrecord findBy(final String firstName, final String lastName) {
		return medicalrecordRepositoryImpl.findById(firstName, lastName);
	}
	
	/**
	 * Call the medicalrecord repository Impl save method.
	 *
	 * @param medicalrecord Medicalrecord object parsed in the body of @mapping.
	 * @return the Medicalrecord object saved.
	 */
	public Medicalrecord save(Medicalrecord medicalrecord) {
		return medicalrecordRepositoryImpl.save(medicalrecord);
	}
	
	/**
	 * Call the medicalrecord repository Impl delete method.
	 *
	 * @param firstName id of the Medicalrecord objects.
	 * @param lastName  id of the Medicalrecord objects.
	 */
	public void deleteBy(final String firstName, final String lastName) {
		medicalrecordRepositoryImpl.deleteBy(firstName, lastName);
	}
	
	/**
	 * Call the medicalrecord repository Impl update method.
	 *
	 * @param partialUpdate Medicalrecord object with attributes have to be modified.
	 * @param firstName     id of the Medicalrecord objects.
	 * @param lastName      id of the Medicalrecord objects.
	 * @return the Medicalrecord object updated.
	 */
	public Medicalrecord update(Medicalrecord partialUpdate, final String firstName, final String lastName) {
		return medicalrecordRepositoryImpl.updateBy(partialUpdate, firstName, lastName);
	}
	
	/**
	 * Generate a MedicalrecordDTO object.
	 *
	 * @param firstName id of the Medicalrecord objects.
	 * @param lastName  id of the Medicalrecord objects.
	 * @return a MedicalrecordDTO object.
	 */
	public MedicalrecordDTO medicalrecordDTOGenerator(String firstName, String lastName) {
		Medicalrecord medicalrecord = findBy(firstName, lastName);
		return new MedicalrecordDTO(medicalrecord.getMedications(), medicalrecord.getAllergies());
	}
}
