package com.employee.service;

import java.util.List;

import com.employee.dtos.EmployeeDto;
import com.employee.exception.EmployeeAlreadyExist;
import com.employee.model.Employee;

public interface EmployeeService {
	
	String saveEmployee(EmployeeDto employeeDto) throws EmployeeAlreadyExist;
	String saveListOfEmployee(List<EmployeeDto> employeesDto);
	List<Employee> findAllEmployee();
	Employee findByEmpId(String empid);
	Employee findEmpByName(String name);
	Employee findEmpByNameAndSalary(String name,String salary);
	String updateEmployee(String empId,Employee employee);
	String deleteEmpById(int id);
	String deleteEmployeeByName(String name);

}
