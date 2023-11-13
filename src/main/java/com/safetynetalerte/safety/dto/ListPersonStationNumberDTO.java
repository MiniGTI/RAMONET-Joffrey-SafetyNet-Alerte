package com.safetynetalerte.safety.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class ListPersonStationNumberDTO {
	
	@JsonProperty("Persons")
	private List<PersonStationNumberDTO> personStationNumberList = new ArrayList<>();
	
	private int adult;
	private int child;
	
}
