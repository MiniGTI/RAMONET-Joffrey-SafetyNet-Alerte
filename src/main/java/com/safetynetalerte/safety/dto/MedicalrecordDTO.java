package com.safetynetalerte.safety.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class MedicalrecordDTO {
	

	private List<String> medications;
	private List<String> allergies;
	
	
	public MedicalrecordDTO(List<String> medications, List<String> allergies) {
		this.medications = medications;
		this.allergies = allergies;
	}
	
}
