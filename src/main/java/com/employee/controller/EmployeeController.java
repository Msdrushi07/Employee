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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Employee APIs", description = "Employee operation")
@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

	@Autowired
	private EmployeeService empService;

	@Operation(summary = "Create a new user", description = "Creates a new user with name and email")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "User created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))),
			@ApiResponse(responseCode = "400", description = "Invalid input") })
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User object to create", required = true, content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class), examples = @ExampleObject(name = "User Create Request", value = "{ \"name\": \"John Doe\", \"email\": \"john@example.com\" }")))
	@SecurityRequirement(name = "bearerAuth") // Secures only this endpoint
	@PostMapping
	public ResponseEntity<String> saveEmplyee(@Valid @RequestBody EmployeeDto employeeDto) throws EmployeeAlreadyExist {
		log.info("inside save employee controller");
		String res = empService.saveEmployee(employeeDto);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

	@Operation(summary = "Create user")
	@ApiResponse(responseCode = "201", description = "User created", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class), examples = {
			@ExampleObject(name = "User Example", value = "{ \"id\": 1, \"name\": \"John Doe\", \"email\": \"john@example.com\" }") }))
	@PostMapping("/saveAll")
	public ResponseEntity<String> saveListOFEmployees(@RequestBody List<EmployeeDto> employeesDto) {
		log.info("inside save employee list controller");
		String res = empService.saveListOfEmployee(employeesDto);
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}

	@PreAuthorize("hasRole('EMPLOYEE')")
	@GetMapping
	public ResponseEntity<List<Employee>> findAllEmpoyees() {
		log.info("inside find all employee controller");
		List<Employee> res = empService.findAllEmployee();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@Operation(summary = "Get employee by ID", description = "Retrieve a single user by their unique ID", responses = {
			@ApiResponse(responseCode = "200", description = "User found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Employee.class))),
			@ApiResponse(responseCode = "404", description = "User not found"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@Parameter(name = "id", description = "ID of the user", required = true, in = ParameterIn.HEADER)
	@GetMapping("/{empId}")
	public ResponseEntity<Employee> findEmpByEmpId(@PathVariable("empId") String empId) {
		Employee res = empService.findByEmpId(empId);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/name")
	public ResponseEntity<Employee> findEmployeeByName(@RequestParam("name") String name) {
		Employee res = empService.findEmpByName(name);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/name/sallary")
	public ResponseEntity<Employee> findByNmaeAndSallary(@RequestHeader("name") String name,
			@RequestHeader("sallary") String sallary) {
		Employee res = empService.findEmpByNameAndSalary(name, sallary);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PutMapping("/{empId}")
	public ResponseEntity<String> updateEmployee(@PathVariable("empId") String employeeId,
			@RequestBody Employee employee) {
		String res = empService.updateEmployee(employeeId, employee);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteEmployeeById(@PathVariable("id") int id) {
		String res = empService.deleteEmpById(id);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<String> deleteEmployeeByName(@RequestParam("name") String name) {
		String res = empService.deleteEmployeeByName(name);
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
