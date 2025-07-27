package com.employee.service;

import java.util.Arrays;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.employee.dtos.EmployeeDto;
import com.employee.exception.EmployeeAlreadyExist;
import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository empRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public String saveEmployee(EmployeeDto employeeDto) throws EmployeeAlreadyExist {
		log.info("inside save employee service");
		Employee employee= modelMapper.map(employeeDto, Employee.class);
		log.info("converted the employee dto to employee object");
		Employee emp = empRepository.findByEmpId(employee.getEmpId());
		if(emp != null) {
			throw new EmployeeAlreadyExist("employee already exist with employee id :"+emp.getEmpId());
		}
		empRepository.save(employee);
		return "employee saved successfully with id :" + employee.getEmpId();
	}

	@Override
	public String saveListOfEmployee(List<EmployeeDto> employeeDto) {
		Employee[] emploee=modelMapper.map(employeeDto,Employee[].class);
		List<Employee> employees=Arrays.asList(emploee);
		empRepository.saveAll(employees);
		return "saved all employees";
	}

	@Override
	public List<Employee> findAllEmployee() {
		return empRepository.findAll();
	}

	@Override
	public Employee findByEmpId(String empid) {
		return empRepository.findByEmpId(empid);
	}

	@Override
	public Employee findEmpByName(String name) {
		return empRepository.findByName(name);
	}

	@Override
	public Employee findEmpByNameAndSalary(String name, String salary) {
		return empRepository.findByNameAndSallary(name, salary);
	}

	@Override
	public String updateEmployee(String empId, Employee employee) {
		Employee emp = empRepository.findByEmpId(empId);
		emp.setName(employee.getName());
		emp.setAge(employee.getAge());
		emp.setSallary(employee.getSallary());
		empRepository.save(emp);
		return "updated the employee details";
	}

	@Override
	public String deleteEmpById(int id) {
		empRepository.deleteById(id);
		return "employee deleted successfully";
	}

	@Override
	@Transactional
	public String deleteEmployeeByName(String name) {
		empRepository.deleteByName(name);
		return "employee deleted with name :" + name;
	}
	
	public void objectMapperMethods() throws JsonProcessingException {
	// converting json string to Employee object need to take care datatype,variable name,setter getter, all args and no args constructor mandatory
		String json=jsonEmployeeData();
		Employee emp = objectMapper.readValue(json,Employee.class);

	// converting employee object to json string
		String empJson = objectMapper.writeValueAsString(emp);
		
	}
	
	public String jsonEmployeeData() {
		return "{\r\n"
				+ "   \"empId\": \"23566\",\r\n"
				+ "    \"name\":\"rashh\",\r\n"
				+ "    \"age\":\"25\",\r\n"
				+ "    \"sallary\":\"100000\"\r\n"
				+ "}";
	}
	
}
