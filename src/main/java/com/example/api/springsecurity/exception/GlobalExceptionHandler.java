/*
 *                     GNU GENERAL PUBLIC LICENSE
 *                        Version 3, 29 June 2007
 *
 *  Copyright (C) 2007 Free Software Foundation, Inc. <https://fsf.org/>
 *  Everyone is permitted to copy and distribute verbatim copies
 *  of this license document, but changing it is not allowed.
 *
 *                             Preamble
 *
 *   The GNU General Public License is a free, copyleft license for
 * software and other kinds of works.
 *
 *   The licenses for most software and other practical works are designed
 * to take away your freedom to share and change the works.  By contrast,
 * the GNU General Public License is intended to guarantee your freedom to
 * share and change all versions of a program--to make sure it remains free
 * software for all its users.  We, the Free Software Foundation, use the
 * GNU General Public License for most of our software; it applies also to
 * any other work released this way by its authors.  You can apply it to
 * your programs, too.
 *
 * Nombre de archivo: GlobalExceptionHandler
 * Autor: 319207
 * Fecha de creación: septiembre 15, 2023
 */

package com.example.api.springsecurity.exception;

import com.example.api.springsecurity.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ApiError globalErrorHandler(Exception ex, HttpServletRequest request) {
    ApiError error = new ApiError();
    error.setBackMessage(ex.getMessage());
    error.setUrl(request.getRequestURL().toString());
    error.setMethod(request.getMethod());
    error.setTimeStamp(LocalDateTime.now());
    error.setMessage("Error interno en el servidor");
    return error;
  }

  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ResponseBody
  public ApiError handlerAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
    ApiError error = new ApiError();
    error.setBackMessage(ex.getMessage());
    error.setUrl(request.getRequestURL().toString());
    error.setMethod(request.getMethod());
    error.setTimeStamp(LocalDateTime.now());
    error.setMessage("Acceso denegado. No tienes los permisos necesraios para acceder a esta funcion. Por favor, contacta al adminisstrador si crees que es un error");
    return error;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ApiError globalErrorHandler(MethodArgumentNotValidException ex, HttpServletRequest request) {
    ApiError error = new ApiError();
    error.setBackMessage(ex.getMessage());
    error.setUrl(request.getRequestURL().toString());
    error.setMethod(request.getMethod());
    error.setTimeStamp(LocalDateTime.now());
    error.setMessage("Error en la peticion enviada");
    log.error(ex.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
            .collect(Collectors.toList()));
    return error;
  }
}
