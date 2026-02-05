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
	        try {
	            List<Element> elements = serviceElement.getAllElements();
	            return ResponseEntity.ok(elements);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                 .body("Error al acceder a la base de datos: " + e.getMessage());
	        }
	    }

	    // READ - Un elemento por ID
	    @GetMapping("/{id}")
	    public ResponseEntity<?> findById(@PathVariable String id) {
	        Integer elementId;
	        try {
	            elementId = Integer.parseInt(id);
	        } catch (NumberFormatException e) {
	            return ResponseEntity.badRequest().body("ID inválido: debe ser un número");
	        }

	        try {
	            Element element = serviceElement.getElementById(elementId);
	            if (element == null) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                     .body("No se encontró el elemento con ID " + id);
	            }
	            return ResponseEntity.ok(element);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                 .body("Error al acceder a la base de datos: " + e.getMessage());
	        }
	    }

	    // CREATE - Nuevo elemento
	    @PostMapping
	    public ResponseEntity<?> add(@Valid @RequestBody Element element, BindingResult result) {
	        if (result.hasErrors()) {
	            // Construimos un mensaje con todos los errores
	            StringBuilder errors = new StringBuilder();
	            result.getFieldErrors().forEach(error -> 
	                errors.append(error.getField())
	                      .append(": ")
	                      .append(error.getDefaultMessage())
	                      .append("; ")
	            );
	            return ResponseEntity.badRequest().body(errors.toString());
	        }

	    	try {
	            Element savedElement = serviceElement.createElement(element);
	            return ResponseEntity.status(HttpStatus.CREATED).body(savedElement);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                 .body("Error al guardar el elemento: " + e.getMessage());
	        }
	    }

	    // UPDATE - Modificación de un elemento
	    @PutMapping("/{id}")
	    public ResponseEntity<?> edit(@PathVariable String id, @Valid @RequestBody Element element, BindingResult result) {
	        Integer elementId;
	        if (result.hasErrors()) {
	            // Construimos un mensaje con todos los errores
	            StringBuilder errors = new StringBuilder();
	            result.getFieldErrors().forEach(error -> 
	                errors.append(error.getField())
	                      .append(": ")
	                      .append(error.getDefaultMessage())
	                      .append("; ")
	            );
	            return ResponseEntity.badRequest().body(errors.toString());
	        }
	        try {
	            elementId = Integer.parseInt(id);
	        } catch (NumberFormatException e) {
	            return ResponseEntity.badRequest().body("ID inválido: debe ser un número");
	        }

	        try {
	            if (serviceElement.getElementById(elementId) == null) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                     .body("No se encontró el elemento con ID " + id);
	            }
	            element.setId(elementId);
	            Element updatedElement = serviceElement.createElement(element);
	            return ResponseEntity.ok(updatedElement);
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                 .body("Error al actualizar el elemento: " + e.getMessage());
	        }
	    }

	    // DELETE - Borrar un elemento
	    @DeleteMapping("/{id}")
	    public ResponseEntity<?> delete(@PathVariable String id) {
	        Integer elementId;
	        try {
	            elementId = Integer.parseInt(id);
	        } catch (NumberFormatException e) {
	            return ResponseEntity.badRequest().body("ID inválido: debe ser un número");
	        }

	        try {
	            Element element = serviceElement.getElementById(elementId);
	            if (element == null) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND)
	                                     .body("No se encontró el elemento con ID " + id);
	            }
	            serviceElement.deleteElement(element.getId());
	            return ResponseEntity.noContent().build();
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	                                 .body("Error al borrar el elemento: " + e.getMessage());
	        }
	    }
	}
