package com.safetynetalerte.safety.service;

import com.safetynetalerte.safety.model.Medicalrecord;
import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.repository.MedicalrecordRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Class to perform age calculs or age filter.
 */
@Service
public class AgeCalculatorService {
	/**
	 * Logger.
	 */
	private final static Logger logger = LoggerFactory.getLogger(AgeCalculatorService.class);
	/**
	 * Call of the implementation of the Medicalrecord repository.
	 */
	private final MedicalrecordRepositoryImpl medicalrecordRepositoryImpl;
	
	/**
	 * Class constructor.
	 *
	 * @param medicalrecordRepositoryImpl Medicalrecord repository.
	 */
	public AgeCalculatorService(MedicalrecordRepositoryImpl medicalrecordRepositoryImpl) {
		this.medicalrecordRepositoryImpl = medicalrecordRepositoryImpl;
	}
	
	
	/**
	 * Formate the birthdate string from medicalrecords.
	 *
	 * @param birthdate string contain a date with dd/MM/yyyy format.
	 * @return string contain the birthdate reformatted like yyyy/MM/dd.
	 */
	private String birthdateFormater(String birthdate) {
		final String OLD_FORMAT = "dd/MM/yyyy";
		final String NEW_FORMAT = "yyyy/MM/dd";
		
		String formatedBirthdate;
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(OLD_FORMAT);
			Date date = simpleDateFormat.parse(birthdate);
			simpleDateFormat.applyPattern(NEW_FORMAT);
			formatedBirthdate = simpleDateFormat.format(date);
			formatedBirthdate = formatedBirthdate.replaceAll("/", "");
		} catch(ParseException e) {
			logger.error("Error in the birthdate format process, birthdate: " + birthdate);
			throw new RuntimeException("Error in the birthdate format process", e);
		}
		return formatedBirthdate;
	}
	
	/**
	 * Return True if the medicalrecord parsed in @param is over 18years old.
	 *
	 * @param medicalrecord Medicalrecord object.
	 * @return Boolean  True if >18 or false if <18.
	 */
	private Boolean adultFiltrer(Medicalrecord medicalrecord) {
		boolean major = false;
		
		int toDay = Integer.parseInt(LocalDate.now()
				.toString()
				.replaceAll("-", ""));
		int birthdate = Integer.parseInt(birthdateFormater(medicalrecord.getBirthdate()));
		int age = toDay - birthdate;
		
		if(age < 0) {
			logger.error("The birthdate can't be later than current date");
		}
		
		if(age > 180000) {
			major = true;
		}
		return major;
	}
	
	/**
	 * Return an int with the count of adult from the list of Person object parsed.
	 *
	 * @param personsListToFilter list of Person object.
	 * @return int  count of adult.
	 */
	
	public int counterOfAdultListFilter(List<Person> personsListToFilter) {
		List<Medicalrecord> medicalrecordsList = medicalrecordRepositoryImpl.getAll();
		List<Medicalrecord> medicalrecordsListToFilter = new ArrayList<>();
		
		for(Person person : personsListToFilter) {
			for(Medicalrecord medicalrecord : medicalrecordsList) {
				if(person.getFirstName()
						.equals(medicalrecord.getFirstName()) && person.getLastName()
						.equals(medicalrecord.getLastName())) {
					medicalrecordsListToFilter.add(medicalrecord);
				}
			}
		}
		int major = 0;
		for(Medicalrecord medicalrecord : medicalrecordsListToFilter) {
			if(adultFiltrer(medicalrecord)) {
				major += 1;
			}
		}
		return major;
	}
	
	/**
	 * Return a list of Person object under 18years old from the list of Person object.
	 *
	 * @param personsListToFilter List of Person object have to be filtered.
	 * @return a list of person Object under 18 years old.
	 */
	public List<Person> adultListFilter(List<Person> personsListToFilter) {
		List<Medicalrecord> medicalrecordsList = medicalrecordRepositoryImpl.getAll();
		List<Person> minorList = new ArrayList<>();
		
		for(Person person : personsListToFilter) {
			for(Medicalrecord medicalrecord : medicalrecordsList) {
				if(person.getFirstName()
						.equals(medicalrecord.getFirstName()) && person.getLastName()
						.equals(medicalrecord.getLastName())) {
					if(!adultFiltrer(medicalrecord)) {
						minorList.add(person);
					}
				}
			}
		}
		return minorList;
	}
	
	/**
	 * Calcul the age of a Person.
	 *
	 * @param firstName Person firstName field.
	 * @param lastName  Person lastName field.
	 * @return int  the age of the Person.
	 */
	public int ageCalculator(String firstName, String lastName) {
		List<Medicalrecord> medicalrecordsList = medicalrecordRepositoryImpl.getAll();
		int age = 0;
		for(Medicalrecord medicalrecord : medicalrecordsList) {
			if(medicalrecord.getFirstName()
					.equals(firstName) && medicalrecord.getLastName()
					.equals(lastName)) {
				DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd/yyyy");
				String stringFormat = "MM/dd/yyyy";
				String now = new SimpleDateFormat(stringFormat).format(new Date());
				LocalDate birthdate = LocalDate.parse(medicalrecord.getBirthdate(), format);
				LocalDate current = LocalDate.parse(now, format);
				Period period = Period.between(birthdate, current);
				age = period.getYears();
			}
		}
		return age;
	}
}
