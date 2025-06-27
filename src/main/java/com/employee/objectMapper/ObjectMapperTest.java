package com.employee.objectMapper;

import com.employee.model.Employee;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class ObjectMapperTest {
	
	private ObjectMapper objectMapper = new ObjectMapper();

	public void objectMapperMethods() throws JsonProcessingException {
		// converting json string to Employee object need to take care datatype,variable name,setter getter, all args and no args constructor mandatory
			String json=jsonEmployeeData();
			Employee emp = objectMapper.readValue(json,Employee.class);

		// converting employee object to json string
			String empJson = objectMapper.writeValueAsString(emp);
			
		//  conveting json string to Json object
			// Option 1: Using JsonParser
			JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
			System.out.println(jsonObject.get("name").getAsString()); 

			// Option 2: Using Gson directly (for known POJO)
			Gson gson = new Gson();
			JsonObject jsonobj= gson.fromJson(json,JsonObject.class);
			Employee gsonEmp = gson.fromJson(json, Employee.class);
			
		}
		
		public String jsonEmployeeData() {
			return "{\r\n"
					+ "   \"empId\": \"23566\",\r\n"
					+ "    \"name\":\"rashh\",\r\n"
					+ "    \"age\":\"25\",\r\n"
					+ "    \"sallary\":\"100000\"\r\n"
					+ "}";   // paste the raw json data created from postman  inside " "
		}
}
