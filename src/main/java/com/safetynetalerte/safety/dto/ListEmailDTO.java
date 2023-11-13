package com.safetynetalerte.safety.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class ListEmailDTO {

	private List<EmailDTO> emailList = new ArrayList<>();
	
}
