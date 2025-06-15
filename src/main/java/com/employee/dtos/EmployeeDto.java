package com.employee.dtos;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

	private int id;
	@NotBlank(message = "please provide employee id")
	private String empId;
	private String name;
	private String age;
	private String sallary;
	private String password;
	private String role;
}
