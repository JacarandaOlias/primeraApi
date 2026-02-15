package com.jacaranda.primeraPrueba.Security;


import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.jacaranda.primeraPrueba.exception.ApiError;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//IMPORTANTE
/***************************************
* importa este ObjectMapper, 
* si importáis el otro no se crea un Beam y nos dará problemas
*/
import tools.jackson.databind.ObjectMapper;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

 private final ObjectMapper objectMapper;

 public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
     this.objectMapper = objectMapper;
 }

 @Override
 public void commence(HttpServletRequest request,
                      HttpServletResponse response,
                      AuthenticationException authException)
         throws IOException {

     response.setStatus(HttpStatus.UNAUTHORIZED.value());
     response.setContentType("application/json");

     ApiError error = new ApiError(
             HttpStatus.UNAUTHORIZED.value(),
             HttpStatus.UNAUTHORIZED.getReasonPhrase(),
             List.of("No estás autenticado o el token es inválido"),
             request.getRequestURI()
     );

     // Escribimos en la respuesta el error que hemos preparado.
     
     objectMapper.writeValue(response.getOutputStream(), error);
 }
}
