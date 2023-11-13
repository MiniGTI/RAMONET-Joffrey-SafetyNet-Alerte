package com.safetynetalerte.safety.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhoneAlertDTO {
	
	private String phone;
	
	
	public PhoneAlertDTO(String phone) {
		this.phone = phone;
	}
}

