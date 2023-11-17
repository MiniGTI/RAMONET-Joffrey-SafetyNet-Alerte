package com.safetynetalerte.safety.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoniter.JsonIterator;
import com.safetynetalerte.safety.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * To read the Jsonfile and push Data in object lists.
 */
public class JsonReader {
	/**
	 * Logger.
	 */
	private static final Logger logger = LoggerFactory.getLogger(JsonReader.class);
	/**
	 * The file path of the data.json.
	 */
	private static final String filepath = "/data.json";
	
	/**
	 * method to read the json file.
	 *
	 * @return a JsonNode.
	 */
	private static JsonNode jsonReader() {
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode json;
		
		try(InputStream inputStream = TypeReference.class.getResourceAsStream(filepath)) {
			json = objectMapper.readValue(inputStream, JsonNode.class);
		} catch(IOException e) {
			logger.error("Failed to create the jsonNode in the jsonReader method");
			throw new RuntimeException("Failed to load the file.json", e);
		}
		logger.debug("Creation of the jsonNode");
		return json;
	}
	
	/**
	 * create the list of Person object.
	 *
	 * @return the list of Person object from the json file.
	 */
	public static List<Person> personsList() {
		JsonNode jsonNode = jsonReader();
		PersonsList deserialize = JsonIterator.deserialize(jsonNode.toString(), PersonsList.class);
		logger.debug("Creation of the personList");
		return deserialize.getPersons();
	}
	
	/**
	 * create the list of Firestation object.
	 *
	 * @return the list of Firestation object from the json file.
	 */
	public static List<Firestation> firestationsList() {
		JsonNode jsonNode = jsonReader();
		FirestationsList deserialize = JsonIterator.deserialize(jsonNode.toString(), FirestationsList.class);
		logger.debug("Creation of the firestationList");
		return deserialize.getFirestations();
	}
	
	/**
	 * create the list of Medicalrecord object.
	 *
	 * @return the list of Medicalrecord object from the json file.
	 */
	public static List<Medicalrecord> medicalrecordsList() {
		JsonNode jsonNode = jsonReader();
		MedicalrecordsList deserialize = JsonIterator.deserialize(jsonNode.toString(), MedicalrecordsList.class);
		logger.debug("Creation of the medicalrecordsList");
		return deserialize.getMedicalrecords();
	}
}
