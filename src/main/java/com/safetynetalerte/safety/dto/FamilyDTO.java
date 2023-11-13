package com.safetynetalerte.safety.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FamilyDTO {

	private String firstName;
	private String lastName;
	
	
	public FamilyDTO(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	
}
