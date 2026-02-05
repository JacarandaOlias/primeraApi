package com.jacaranda.primeraPrueba.exception;

//Recurso no encontrado
//Error lógico / de negocio
//Se traduce a 404 Not Found

public class ElementNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public ElementNotFoundException(Integer id) {
        super("Elemento no encontrado con ID: " + id);
    }
}
