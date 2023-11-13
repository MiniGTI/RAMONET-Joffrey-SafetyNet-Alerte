package com.safetynetalerte.safety.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class ListChildAlertDTO {

	@JsonProperty("Children")
	private List<ChildAlertDTO> childAlertList = new ArrayList<>();
	@JsonProperty("Family")
	private List<FamilyDTO> familyList = new ArrayList<>();
	

}
