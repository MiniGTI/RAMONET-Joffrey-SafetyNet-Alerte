package com.safetynetalerte.safety.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonStationNumberDTO {

	private String firstName;
	private String lastName;
	private String address;
	private String phone;

	
	public PersonStationNumberDTO(String firstName, String lastName, String address, String phone) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phone = phone;
	}

}
