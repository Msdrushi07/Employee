package com.employee.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Employees")
public class Employee {
    
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String empId;
	private String name;
	private String age;
	private String sallary;
	private String password;
	private String role;
	
}
