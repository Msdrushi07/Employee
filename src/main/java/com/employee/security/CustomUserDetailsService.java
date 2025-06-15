package com.employee.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.employee.model.Employee;
import com.employee.repository.EmployeeRepository;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         Employee employee = repository.findByName(username);
         CustomUserDetails userDetails = null;
         if(employee != null) {
       return  userDetails = new CustomUserDetails(employee); 
         }
         else {
        	 throw new UsernameNotFoundException("employee not found with employee name :"+username);
         }
        
    }
}
