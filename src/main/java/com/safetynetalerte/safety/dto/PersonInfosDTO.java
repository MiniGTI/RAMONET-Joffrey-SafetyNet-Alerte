package com.safetynetalerte.safety.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonInfosDTO {
	
	private String firstName;
	private String lastName;
	private String address;
	private int age;
	private String mail;
	private MedicalrecordDTO medicalrecord;
	
	
	public PersonInfosDTO(String firstName, String lastName, String address, int age, String mail, MedicalrecordDTO medicalrecord) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.age = age;
		this.mail = mail;
		this.medicalrecord = medicalrecord;
	}
	
}
