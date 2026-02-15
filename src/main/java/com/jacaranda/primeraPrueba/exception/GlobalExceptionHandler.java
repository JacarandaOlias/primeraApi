package com.jacaranda.primeraPrueba.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.jacaranda.primeraPrueba.exception.element.BadRequestElementException;
import com.jacaranda.primeraPrueba.exception.element.DatabaseException;
import com.jacaranda.primeraPrueba.exception.element.ElementNotFoundException;
import com.jacaranda.primeraPrueba.exception.user.UserException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity<ApiError> handleAuthenticationExceptions(Exception ex,  HttpServletRequest request) {

        ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),
	            List.of("Credenciales incorrectas"),request.getRequestURI()
	    );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
	
	//400 error en JSON
		@ExceptionHandler(HttpMessageNotReadableException.class)
		public ResponseEntity<ApiError> handleJsonParseError(HttpMessageNotReadableException ex,  HttpServletRequest request) {
		    ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(),HttpStatus.BAD_REQUEST.getReasonPhrase(),
		            List.of("El JSON enviado no es válido"),request.getRequestURI()
		    );

		    return ResponseEntity.badRequest().body(error);
		}
		
	@ExceptionHandler(UserException.class)
	public ResponseEntity<ApiError> UserException(UserException ex, HttpServletRequest request) {

		ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
				ex.getMessages(), request.getRequestURI());

		return ResponseEntity.badRequest().body(error);
	}
	
	
	@ExceptionHandler(BadRequestElementException.class)
	public ResponseEntity<ApiError> BadRequestElementException(BadRequestElementException ex, HttpServletRequest request) {

		ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
				ex.getMessages(), request.getRequestURI());

		return ResponseEntity.badRequest().body(error);
	}
	
	// 404 - Elemento no encontrado
	@ExceptionHandler(ElementNotFoundException.class)
	public ResponseEntity<ApiError> handleElementNotFound(ElementNotFoundException ex, HttpServletRequest request) {

		ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(),
				List.of(ex.getMessage()), request.getRequestURI());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	// 500 - Error de base de datos
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<ApiError> handleDatabase(DatabaseException ex, HttpServletRequest request) {

		ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), List.of("Error de acceso a datos. Inténtelo más tarde."),
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}

	// 400 - ID inválido
	@ExceptionHandler(NumberFormatException.class)
	public ResponseEntity<ApiError> handleNumberFormat(NumberFormatException ex, HttpServletRequest request) {

		ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
				List.of("El ID debe ser un número válido"), request.getRequestURI());

		return ResponseEntity.badRequest().body(error);
	}

	// 400 - Validación manual
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {

		ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
				List.of("Datos inválidos: " + ex.getMessage()), request.getRequestURI());

		return ResponseEntity.badRequest().body(error);
	}

	// 500 - cualquier otro error
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGeneral(Exception ex, HttpServletRequest request) {

		ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), List.of("Error interno del servidor"),
				request.getRequestURI());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}
