package com.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import com.employee.dtos.EmployeeDto;
import com.employee.exception.EmployeeAlreadyExist;
import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;
import com.employee.service.EmployeeService;
import com.employee.service.EmployeeServiceImpl;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

	@Mock
	private EmployeeRepository employeeRepository;
	
	@InjectMocks
	private EmployeeServiceImpl employeeService;
	
	@Mock
	private ModelMapper modelMapper;
	
	@Test
	void SaveEmployeeTest() throws EmployeeAlreadyExist {
		EmployeeDto empDto = new EmployeeDto();
		empDto.setEmpId("12345");
		empDto.setName("rushi");
		empDto.setAge("25");
		empDto.setSallary("1838388");
		Employee employee = new Employee();
		employee.setEmpId("12345");
		employee.setName("rushi");
		employee.setAge("25");
		employee.setSallary("1838388");
		when(modelMapper.map(empDto,Employee.class)).thenReturn(employee);
		when(employeeRepository.findByEmpId(anyString())).thenReturn(null);
		String actual = employeeService.saveEmployee(empDto);
		assertEquals("employee saved successfully with id :"+employee.getEmpId(),actual);
		
	}
	
	@Test
	void SaveEmployeeExceptionTest() throws EmployeeAlreadyExist {
		EmployeeDto empDto = mock(EmployeeDto.class);
		Employee employee = new Employee();
		employee.setEmpId("12345");
		when(modelMapper.map(any(),any())).thenReturn(employee);
		when(employeeRepository.findByEmpId(anyString())).thenReturn(employee);
		EmployeeAlreadyExist exception= assertThrows(EmployeeAlreadyExist.class,() ->
	    	employeeService.saveEmployee(empDto)	
	    );
	assertEquals("employee already exist with employee id :"+employee.getEmpId(),exception.getMessage());
	assertTrue(true);
	}
}
