package com.jacaranda.primeraPrueba.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jacaranda.primeraPrueba.dto.user.RegisterRequest;
import com.jacaranda.primeraPrueba.dto.user.UserResponse;
import com.jacaranda.primeraPrueba.exception.user.UserException;
import com.jacaranda.primeraPrueba.model.User;
import com.jacaranda.primeraPrueba.service.ServiceUser;

import jakarta.validation.Valid;

@RestController
public class ControllerUser {

	private final ServiceUser serviceUser;
	
	public ControllerUser(ServiceUser serviceUser) {
		super();
		this.serviceUser = serviceUser;
	
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(
	        @Valid @RequestBody RegisterRequest request,
	        BindingResult bindingResult) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	    if (bindingResult.hasErrors()) {

	        List<String> errores = bindingResult.getFieldErrors()
	                .stream()
	                .map(error -> error.getField() + ": " + error.getDefaultMessage())
	                .toList();

	        throw new UserException(errores);
	    }

	    if (!request.password().equals(request.confirmPassword())) {
	    	throw new UserException(List.of("Las contraseñas no coinciden"));
	    }

	    User user = new User();
	    user.setUsername(request.username());
	    user.setPassword(passwordEncoder.encode(request.password()));
	    user.setRol("USER");

	    User savedUser = serviceUser.createElement(user);
	    UserResponse response = new UserResponse(
	            savedUser.getUsername(),
	            savedUser.getRol()
	    );
	   

	    return ResponseEntity.status(HttpStatus.CREATED).body(response);

	}
}