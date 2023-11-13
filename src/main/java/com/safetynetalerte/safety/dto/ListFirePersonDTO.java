package com.safetynetalerte.safety.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class ListFirePersonDTO {
	
@JsonProperty("Persons")
	private List<PersonAndMedicalrecordsDTO> personFireAlertList = new ArrayList<>();

	private String firestationNumber;
	

	
}
