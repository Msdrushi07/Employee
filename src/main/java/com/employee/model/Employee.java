package com.employee.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Employees")
@Schema(description = "User object representing a system user")
public class Employee {
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Schema(description = "Unique identifier of the user", example = "1", required = true)
	private String empId;
	private String name;
	private String age;
	private String sallary;
	private String password;
	private String role;
	
}
