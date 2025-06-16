package com.employee.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.employee.dtos.EmployeeDto;
import com.employee.exception.EmployeeAlreadyExist;
import com.employee.model.Employee;
import com.employee.service.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService empService;
	
	@PostMapping
	public ResponseEntity<String> saveEmplyee(@Valid @RequestBody EmployeeDto employeeDto) throws EmployeeAlreadyExist{
		String res= empService.saveEmployee(employeeDto);
		return new ResponseEntity<>(res,HttpStatus.CREATED);
	}
	
	@PostMapping("/saveAll")
	public ResponseEntity<String> saveListOFEmployees(@RequestBody List<EmployeeDto> employeesDto){
		String res = empService.saveListOfEmployee(employeesDto);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping
	public ResponseEntity<List<Employee>> findAllEmpoyees(){
		List<Employee> res = empService.findAllEmployee();
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@GetMapping("/{empId}")
	public ResponseEntity<Employee> findEmpByEmpId(@PathVariable("empId") String empId){
		Employee res = empService.findByEmpId(empId);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@GetMapping("/name")
	public ResponseEntity<Employee> findEmployeeByName(@RequestParam("name") String name){
		Employee res = empService.findEmpByName(name);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@GetMapping("/name/sallary")
	public ResponseEntity<Employee> findByNmaeAndSallary(@RequestHeader("name") String name, @RequestHeader("sallary") String sallary){
		Employee res = empService.findEmpByNameAndSalary(name, sallary);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@PutMapping("/{empId}")
	public ResponseEntity<String> updateEmployee(@PathVariable("empId") String employeeId, @RequestBody Employee employee){
		String res= empService.updateEmployee(employeeId, employee);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") int id){
		String res=empService.deleteEmpById(id);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteEmployeeByName(@RequestParam("name") String name){
		String res=empService.deleteEmployeeByName(name);
		return new ResponseEntity<>(res,HttpStatus.OK);
	}
	
}
