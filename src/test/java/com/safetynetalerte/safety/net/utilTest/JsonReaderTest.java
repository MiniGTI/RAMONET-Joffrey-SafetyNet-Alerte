package com.safetynetalerte.safety.net.utilTest;

import com.safetynetalerte.safety.util.JsonReader;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JsonReaderTest {
	JsonReader jsonReader = new JsonReader();
	@Test
	public void getPersonsFromDataJsonTest(){
	
		System.out.println(jsonReader.personList());
	
	}
}
