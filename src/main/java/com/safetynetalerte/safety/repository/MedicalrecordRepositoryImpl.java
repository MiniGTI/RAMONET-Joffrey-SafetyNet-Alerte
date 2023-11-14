package com.safetynetalerte.safety.repository;

import com.safetynetalerte.safety.model.Medicalrecord;
import com.safetynetalerte.safety.util.JsonReader;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository public class MedicalrecordRepositoryImpl implements MedicalrecordRepository {
	private final List<Medicalrecord> medicalrecords = JsonReader.medicalrecordsList();
	
	@Override
	public List<Medicalrecord> getAll() {
		return medicalrecords;
	}
	
	@Override
	public Medicalrecord save(Medicalrecord medicalrecord) {
		medicalrecords.add(medicalrecord);
		return medicalrecord;
	}
	
	@Override
	public Medicalrecord findById(final String firstName, final String lastName) {
		Medicalrecord findedMedicalrecord = null;
		for(Medicalrecord medicalrecord : medicalrecords) {
			if(medicalrecord.getFirstName()
					.equals(firstName) && medicalrecord.getLastName()
					.equals(lastName)) {
				findedMedicalrecord = medicalrecord;
			}
		}
		
		return findedMedicalrecord;
	}
	
	@Override
	public void deleteBy(final String firstName, final String lastName) {
		medicalrecords.removeIf(medicalrecord -> medicalrecord.getFirstName()
				.equals(firstName) && medicalrecord.getLastName()
				.equals(lastName));
	}
	
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
		return medicalrecordToUpdate;
	}
}
