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
import org.springframework.security.config.http.SessionCreationPolicy;
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
		    http.csrf(csrf -> csrf.disable())
				.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless session
				.authorizeHttpRequests(auth -> auth.antMatchers("/h2-console/**", "/auth/**","/swagger-ui/**", "/swagger-ui.html","/v3/api-docs/**","/v3/api-docs.yaml","/actuator/**")
						.permitAll().antMatchers("/employee/**").hasAnyRole("EMPLOYEE")
				.antMatchers("/admin/**").hasAnyRole("ADMIN").anyRequest()  // has role expect ROLE_USER or ROLE_ADMIN while fetching data from database
				.authenticated())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);     // custom filter
	//			.formLogin(Customizer.withDefaults()).httpBasic(Customizer.withDefaults()); 
		    // normal form login which internally implements UsernamePasswordAuthenticationFilter
		return http.build();
		
	//	.antMatchers("/hr/**", "/manager/**").hasAnyRole("HR", "ADMIN")
    // if using form login then do not use statess, if using jwt then use stateless to treat every request same or new as new request

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