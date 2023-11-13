package com.safetynetalerte.safety.service;

import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.repository.FirestationRepositoryImpl;
import com.safetynetalerte.safety.repository.PersonRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressCalculatorService {
	
	private final FirestationRepositoryImpl firestationRepositoryImpl;
	private final PersonRepositoryImpl personRepositoryImpl;
	
	public AddressCalculatorService(FirestationRepositoryImpl firestationRepositoryImpl, PersonRepositoryImpl personRepositoryImpl) {
		this.firestationRepositoryImpl = firestationRepositoryImpl;
		this.personRepositoryImpl = personRepositoryImpl;
	}
	
	/** Return a List of Person with the same address of the station number
	 *
	 * @param station - number id of the firestation parsed in the @mapping
	 * @return List<Person>
	 */
	public List<Person> personsCoveredByAFirestation(String station) {
		List<Firestation> firestations = firestationRepositoryImpl.findByStation(station);
		List<Person> persons = personRepositoryImpl.getAll();
		List<Person> personsCoveredList = new ArrayList<>();
		for(Firestation firestation : firestations)
			for(Person person : persons) {
				if(person.getAddress()
						.equals(firestation.getAddress())) {
					personsCoveredList.add(person);
				}
		}
		return personsCoveredList;
	}
}
