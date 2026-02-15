package com.jacaranda.primeraPrueba.dto.user;

import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(

        @NotBlank(message = "El username no puede estar vacío")
        String username,

        @NotBlank(message = "La contraseña no puede estar vacía")
        String password,

        @NotBlank(message = "La confirmación de contraseña no puede estar vacía")
        String confirmPassword

) {}