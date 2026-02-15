package com.jacaranda.primeraPrueba.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ElementRequest(

	    @NotBlank(message = "El nombre es obligatorio")
	    @Size(max = 255, message = "El nombre no puede superar los 255 caracteres")
	    String name,

	    @Min(value=0)
	    @NotNull
	    Integer value   

	    
	    
	) {}