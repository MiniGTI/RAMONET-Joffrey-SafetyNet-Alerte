package com.safetynetalerte.safety.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class ListPersonsCoveredDTO {
	
	private String address;
	@JsonProperty("persons")
	private List<PersonAndMedicalrecordsDTO> personAndMedicalrecordsList = new ArrayList<>();
	
}
