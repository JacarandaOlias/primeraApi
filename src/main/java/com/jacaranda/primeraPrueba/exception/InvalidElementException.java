package com.jacaranda.primeraPrueba.exception;

// Recurso no encontrado
// Error lógico / de negocio
// Se traduce a 404 Not Found

public class InvalidElementException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public InvalidElementException(String message) {
        super(message);
    }
}
