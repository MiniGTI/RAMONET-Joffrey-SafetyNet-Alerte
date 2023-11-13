package com.safetynetalerte.safety.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChildAlertDTO {

	private String firstName;
	private String lastName;
	private int age;
	
	
	public ChildAlertDTO(String firstName, String lastName, int age) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}

}
