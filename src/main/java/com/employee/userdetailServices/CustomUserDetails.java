package com.employee.userdetailServices;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.employee.model.Employee;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class CustomUserDetails implements UserDetails{
	
	private Employee employee;
	
	public CustomUserDetails(Employee employee) {
		this.employee = employee;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		log.info(employee.getRole());
	    return List.of(new SimpleGrantedAuthority(employee.getRole()));
	}
// If multiple roles are present then use below	
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//	    return employee.getRoles().stream()
//	        .map(role -> new SimpleGrantedAuthority(role.getName()))
//	        .collect(Collectors.toList());
//	}



	@Override
	public String getPassword() {
		return employee.getPassword();
	}

	@Override
	public String getUsername() {
		return employee.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
