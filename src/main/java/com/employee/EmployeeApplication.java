package com.employee;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@OpenAPIDefinition(info = @Info(title = "User Management API", version = "1.0.0", description = "REST API for managing users in the system", contact = @Contact(name = "Rushikesh Mane", email = "rushikesh@example.com", url = "https://www.linkedin.com/in/your-profile"), license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0")),
servers = {
		@Server(url = "http://localhost:8081", description = "Local development server"),
		@Server(url = "https://api.example.com", description = "Production server") }, tags = {
				@Tag(name = "User APIs", description = "All operations related to User"),
				@Tag(name = "Admin APIs", description = "Administrative operations") }
//	    security = @SecurityRequirement(name = "JWT Token")

)
@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class EmployeeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeApplication.class, args);
		log.info("Hello from Spring Boot, testing Kibana logs!");
	}
	


	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
