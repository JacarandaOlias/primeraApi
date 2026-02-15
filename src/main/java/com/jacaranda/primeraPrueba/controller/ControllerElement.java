package com.jacaranda.primeraPrueba.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jacaranda.primeraPrueba.exception.BadRequestElementException;
import com.jacaranda.primeraPrueba.model.Element;
import com.jacaranda.primeraPrueba.service.ServiceElement;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/elements")
public class ControllerElement {

	private ServiceElement serviceElement;

	public ControllerElement(ServiceElement serviceElement) {
		super();
		this.serviceElement = serviceElement;
	}

	// READ - Todos los elementos
	@GetMapping
	public ResponseEntity<?> findAll() {
		return ResponseEntity.ok(serviceElement.getAllElements());
	}

	// READ - Un elemento por ID
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) {
		int elementId = Integer.parseInt(id); // puede lanzar NumberFormatException
		Element element = serviceElement.getElementById(elementId);
		return ResponseEntity.ok(element);
	}

	// CREATE - Nuevo elemento
	@PostMapping
	public ResponseEntity<?> add(@Valid @RequestBody Element element, BindingResult result) {
		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors()
	                .stream()
	                .map(error -> error.getField() + ": " + error.getDefaultMessage())
	                .toList();
	    	throw new BadRequestElementException(errores);

		}

		Element saved = serviceElement.createElement(element);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	// UPDATE - Modificación de un elemento
	@PutMapping("/{id}")
	public ResponseEntity<?> edit(@PathVariable String id, @Valid @RequestBody Element element, BindingResult result) {
		if (result.hasErrors()) {
			throw new IllegalArgumentException(result.getFieldErrors().toString());
		}

		int elementId = Integer.parseInt(id); // puede lanzar NumberFormatException
		element.setId(elementId);
		Element updated = serviceElement.updateElement(elementId, element);
		return ResponseEntity.ok(updated);
	}

	// DELETE - Borrar un elemento
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable String id) {
		int elementId = Integer.parseInt(id); // puede lanzar NumberFormatException
		serviceElement.deleteElement(elementId);
		return ResponseEntity.noContent().build();
	}
}
