package com.employee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.employee.filter.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails admin = User.withDefaultPasswordEncoder().username("admin").password("admin123").roles("ADMIN")
//				.build();
//
//		UserDetails user = User.withDefaultPasswordEncoder().username("user").password("user123").roles("USER").build();
//
//		return new InMemoryUserDetailsManager(admin, user);
//	}
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private JwtFilter jwtFilter;
	
	@Bean
	public PasswordEncoder noPasswordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
	
//	@Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeHttpRequests(auth -> auth.antMatchers("/h2-console/**", "/auth/**").permitAll().antMatchers("/employee/**").hasAnyRole("EMPLOYEE")
				.antMatchers("/admin/**").hasAnyRole("ADMIN").anyRequest()
				.authenticated())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);     // custom filter
	//			.formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults());
		return http.build();

		
	//	.antMatchers("/hr/**", "/manager/**").hasAnyRole("HR", "ADMIN")

	}

	 @Bean
	    public AuthenticationProvider authenticationProvider(){
	        DaoAuthenticationProvider authenticationProvider=new DaoAuthenticationProvider();
	        authenticationProvider.setUserDetailsService(userDetailService);
	        authenticationProvider.setPasswordEncoder(noPasswordEncoder());
	        return authenticationProvider;
	    }

	    @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }
}
