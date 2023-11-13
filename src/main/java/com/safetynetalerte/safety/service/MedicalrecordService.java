package com.safetynetalerte.safety.service;

import com.safetynetalerte.safety.dto.MedicalrecordDTO;
import com.safetynetalerte.safety.model.Medicalrecord;
import com.safetynetalerte.safety.repository.MedicalrecordRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicalrecordService {
	
	private final MedicalrecordRepositoryImpl medicalrecordRepositoryImpl;
	
	public MedicalrecordService(MedicalrecordRepositoryImpl medicalrecordRepositoryImpl) {
		this.medicalrecordRepositoryImpl = medicalrecordRepositoryImpl;
	}
	
	public List<Medicalrecord> getAll() {
		return medicalrecordRepositoryImpl.getAll();
	}
	
	public Medicalrecord findBy(final String firstName, final String lastName) {
		return medicalrecordRepositoryImpl.findById(firstName, lastName);
	}
	
	public Medicalrecord save(Medicalrecord medicalrecord) {
		return medicalrecordRepositoryImpl.save(medicalrecord);
	}
	
	public void deleteBy(final String firstName, final String lastName) {
		medicalrecordRepositoryImpl.deleteBy(firstName, lastName);
	}

	public Medicalrecord update(Medicalrecord partialUpdate, final String firstName, final String lastName) {
		return medicalrecordRepositoryImpl.updateBy(partialUpdate, firstName, lastName);
	}
	public MedicalrecordDTO medicalrecordDTOGenerator(String firstName, String lastName) {
		Medicalrecord medicalrecord = findBy(firstName, lastName);
		return new MedicalrecordDTO(medicalrecord.getMedications(), medicalrecord.getAllergies());
	}
}
