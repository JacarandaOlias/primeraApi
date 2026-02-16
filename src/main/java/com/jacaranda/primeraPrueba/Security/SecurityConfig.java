package com.jacaranda.primeraPrueba.Security;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jacaranda.primeraPrueba.exception.ApiError;

import tools.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
	        throws Exception {
	    return configuration.getAuthenticationManager();
	}
	
	 @Bean
	    AccessDeniedHandler accessDeniedHandler() {
	        return (request, response, ex) -> {
	            response.setStatus(HttpStatus.FORBIDDEN.value());
	            response.setContentType("application/json");

	            ApiError error = new ApiError(
	                    HttpStatus.FORBIDDEN.value(),
	                    HttpStatus.FORBIDDEN.getReasonPhrase(),
	                    List.of("No tienes permisos para acceder a este recurso"),
	                    request.getRequestURI()
	            );

	            new ObjectMapper().writeValue(response.getOutputStream(), error);
	        };
	    }
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {

		http
		.cors(cors->{})
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
         
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .requestMatchers("/register", "/login").permitAll()
            .requestMatchers(HttpMethod.GET, "/elements/**").hasAnyRole("USER", "ADMIN")
            .requestMatchers(HttpMethod.POST, "/elements/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/elements/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.DELETE, "/elements/**").hasRole("ADMIN")
            .anyRequest().denyAll()
        )
        .exceptionHandling(ex -> ex
        		.authenticationEntryPoint(jwtAuthenticationEntryPoint)
        		.accessDeniedHandler(accessDeniedHandler())

        		);

    	http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

}