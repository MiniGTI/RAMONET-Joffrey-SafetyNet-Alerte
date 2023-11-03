package com.safetynetalerte.safety.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsoniter.JsonIterator;
import com.safetynetalerte.safety.model.Person;
import com.safetynetalerte.safety.model.PersonsList;
import org.springframework.asm.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonReader {
	
	private static final String filepath = "/data.json";
	
	private static JsonNode jsonReader(){
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode;
		
		try(InputStream inputStream = TypeReference.class.getResourceAsStream(filepath)){
			jsonNode = objectMapper.readValue(inputStream, JsonNode.class);
		} catch( IOException e){
			throw new RuntimeException("Failed to load the file.json", e);
		}
		return jsonNode;
	}
	
	public static List<Person> personList(){
		JsonNode jsonNode = jsonReader();
		PersonsList deserialize = JsonIterator.deserialize(jsonNode.toString(), PersonsList.class);
		return deserialize.getPersons();
	}
}
