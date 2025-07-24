package com.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.employee.controller.EmployeeController;
import com.employee.dtos.EmployeeDto;
import com.employee.exception.EmployeeAlreadyExist;
import com.employee.model.Employee;
import com.employee.service.EmployeeService;

@ExtendWith(MockitoExtension.class)
public class ControllerTest {

	@Mock
	private EmployeeService employeeService;
	
	@InjectMocks
	private EmployeeController employeeController;
	
	@Test
	void saveEmployeeTest() throws EmployeeAlreadyExist {
		EmployeeDto dummy= mock(EmployeeDto.class);
		EmployeeDto empDto = new EmployeeDto();
		empDto.setEmpId("12345");
		empDto.setName("rushi");
		empDto.setAge("25");
		empDto.setSallary("1838388");
	 lenient().when(employeeService.saveEmployee(empDto)).thenReturn("employee saved successfully with id :" + empDto.getEmpId());
		ResponseEntity<String> result=employeeController.saveEmplyee(empDto);
		assertEquals(201,result.getStatusCode().value());
		assertNotNull(result);
	
	}
	
	@Test
	void findEmployeeByIdTest() {
		Employee employee= mock(Employee.class);
		when(employeeService.findByEmpId(anyString())).thenReturn(employee);
		ResponseEntity<Employee> actual= employeeController.findEmpByEmpId("12345");
		assertNotNull(actual);
	}
}
