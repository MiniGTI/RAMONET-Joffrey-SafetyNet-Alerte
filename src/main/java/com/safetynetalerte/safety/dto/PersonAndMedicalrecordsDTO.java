package com.safetynetalerte.safety.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonAndMedicalrecordsDTO {
	
	private String firstName;
	private String lastName;
	private String phone;
	private int age;
	private MedicalrecordDTO medicalrecord;
	
	
	public PersonAndMedicalrecordsDTO(String firstName, String lastName, String phone, int age, MedicalrecordDTO medicalrecord) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phone = phone;
		this.age = age;
		this.medicalrecord = medicalrecord;
	}

	
}
