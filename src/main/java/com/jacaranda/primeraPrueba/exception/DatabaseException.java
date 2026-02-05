package com.jacaranda.primeraPrueba.exception;

// Datos inválidos de negocio

public class DatabaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public DatabaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
