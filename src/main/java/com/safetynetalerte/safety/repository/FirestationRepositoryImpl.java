package com.safetynetalerte.safety.repository;

import com.safetynetalerte.safety.model.Firestation;
import com.safetynetalerte.safety.util.JsonReader;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository public class FirestationRepositoryImpl implements FirestationRepository {
	private final List<Firestation> firestations = JsonReader.firestationsList();
	
	
	@Override
	public List<Firestation> getAll() {
		return firestations;
	}
	
	@Override
	public Firestation save(Firestation firestation) {
		firestations.add(firestation);
		return firestation;
	}
	
	@Override
	public Firestation findById(String address, String station) {
		return null;
	}
	
	public List<Firestation> findByStation(String station) {
		List<Firestation> firestationList = new ArrayList<>();
		for(Firestation firestation : firestations) {
			if(firestation.getStation()
					.equals(station)) {
				firestationList.add(firestation);
			}
		}
		return firestationList;
	}
	
	public Firestation findByAddress(String address) {
		Firestation firestationFound = null;
		for(Firestation firestation : firestations) {
			if(firestation.getAddress()
					.equals(address)) {
				firestationFound = firestation;
			}
		}
		return firestationFound;
	}
	
	@Override
	public void deleteBy(String address, String station) {
		firestations.removeIf(firestation -> firestation.getAddress()
				.equals(address) && firestation.getStation()
				.equals(station));
	}
	
	@Override
	public Firestation updateBy(Firestation firestation, String key0, String key1) {
		return null;
	}
	
	public Firestation update(Firestation partialUpdate, String address) {
		Firestation firestationToUpdate = new Firestation();
		
		firestationToUpdate.setAddress(address);
		if(Objects.nonNull(partialUpdate.getStation())) {
			firestationToUpdate.setStation(partialUpdate.getStation());
		}
		
		for(int i = 0; i < firestations.size(); i++) {
			if(firestations.get(i)
					.getAddress()
					.equals(address)) {
				firestations.set(i, firestationToUpdate);
			}
		}
		return firestationToUpdate;
	}
	
	public List<String> findPersonsCoveredByFirestationId(List<String> persons) {
		return persons;
	}
}
