package com.employee.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "User object representing a system user")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Userss")
public class User {

	@Id
	private int userId;
    @Schema(description = "Unique identifier of the user", example = "1", required = true)
    private Long id;

    @Schema(description = "Full name of the user", example = "John Doe", required = true)
    private String name;

    @Schema(description = "Email address of the user", example = "john.doe@example.com", required = true)
    private String email;

    
}

