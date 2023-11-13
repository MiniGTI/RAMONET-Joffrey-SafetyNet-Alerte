package com.safetynetalerte.safety.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class ListPhoneAlertDTO {

	List<PhoneAlertDTO> phoneAlertList = new ArrayList<>();

	
}
