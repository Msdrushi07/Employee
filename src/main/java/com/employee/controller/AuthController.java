package com.employee.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.employee.dtos.AuthRequest;
import com.employee.jwt.jwtService;
import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;

@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private jwtService jjwtService;
	
	@Autowired
	private EmployeeRepository empRepository;
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	public String saveUser(@RequestBody Employee employee) {
		if(employee.getPassword() != null) {
			employee.setPassword(passwordEncoder.encode(employee.getPassword()));
		}
		empRepository.save(employee);
        return "user added to the system";
    }
	 @PostMapping("/token")
	    public String getToken(@RequestBody AuthRequest authRequest) {
	        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
	        if (authenticate.isAuthenticated()) {
	            return jjwtService.generateToken(authRequest.getUsername());
	        } else {
	            throw new RuntimeException("invalid access");
	        }
	    }
	
}
