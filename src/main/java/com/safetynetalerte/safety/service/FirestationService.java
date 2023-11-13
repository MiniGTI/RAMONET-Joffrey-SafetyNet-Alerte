package com.safetynetalerte.safety.service;

import com.safetynetalerte.safety.dto.ListPersonStationNumberDTO;
import com.safetynetalerte.safety.dto.PersonStationNumberDTO;
import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.repository.FirestationRepositoryImpl;
import com.safetynetalerte.safety.repository.PersonRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FirestationService {
	
	private final FirestationRepositoryImpl firestationRepositoryImpl;
	@Autowired
	private AddressCalculatorService addressCalculatorService;
	@Autowired
	private AgeCalculatorService ageCalculatorService;
	
	public FirestationService(FirestationRepositoryImpl firestationRepositoryImpl, PersonRepositoryImpl personRepositoryImpl) {
		this.firestationRepositoryImpl = firestationRepositoryImpl;
		
	}
	
	public List<Firestation> getAll() {
		return firestationRepositoryImpl.getAll();
	}
	
	public List<Firestation> findBy(final String station) {
		return firestationRepositoryImpl.findByStation(station);
	}
	
	public Firestation save(Firestation firestation) {
		return firestationRepositoryImpl.save(firestation);
	}
	
	public void deleteBy(final String address, final String station) {
		firestationRepositoryImpl.deleteBy(address, station);
	}
	
	public Firestation update(Firestation partialupdate, final String address) {
		return firestationRepositoryImpl.update(partialupdate, address);
	}
	
	public ListPersonStationNumberDTO findPersonsCoveredByFirestationId(String station) throws Exception {
		List<Person> persons = addressCalculatorService.personsCoveredByAFirestation(station);
		ListPersonStationNumberDTO personsInfosFiltered = new ListPersonStationNumberDTO();
		
		persons.forEach(person -> {
			personsInfosFiltered.getPersonStationNumberList()
					.add(new PersonStationNumberDTO(person.getFirstName(), person.getLastName(), person.getAddress(), person.getPhone()));
		});
		int adult = ageCalculatorService.counterOfAdultListFilter(persons);
		int minor = persons.size() - adult;
		
		personsInfosFiltered.setAdult(adult);
		personsInfosFiltered.setChild(minor);
		
		return personsInfosFiltered;
	}
	
}
