package com.teste.manager.systems.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErroHandle handleInvalidArguments(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(
                fieldError -> {
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                });

        return new ErroHandle(errorMap);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ErroHandle handleErrorConstraintViolationException(ConstraintViolationException exception) {
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", exception.getMessage());

        return new ErroHandle(errorMap);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ErroHandle handleErrorUsernameNotFound(UsernameNotFoundException exception) {
        Map<String, String> erroMap = new HashMap<>();
        erroMap.put("message", exception.getMessage());

        return new ErroHandle(erroMap);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationErrorException.class)
    public ErroHandle handleErrorAuthentication(AuthenticationErrorException exception) {
        Map<String, String> erroMap = new HashMap<>();
        erroMap.put("message", exception.getMessage());

        return new ErroHandle(erroMap);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public ErroHandle handleErrorBadCredentials(BadCredentialsException exception) {
        Map<String, String> erroMap = new HashMap<>();
        erroMap.put("message", exception.getMessage());

        return new ErroHandle(erroMap);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErroHandle handleErrorDataIntegrityViolationException(DataIntegrityViolationException exception) {
        Map<String, String> erroMap = new HashMap<>();
        erroMap.put("message", exception.getMessage());

        return new ErroHandle(erroMap);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ErroHandle handleErrorEntityNotFoundException(EntityNotFoundException exception) {
        Map<String, String> erroMap = new HashMap<>();
        erroMap.put("message", exception.getMessage());

        return new ErroHandle(erroMap);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataAccessException.class)
    public ErroHandle handleErrorDataAccessException(DataAccessException exception) {
        Map<String, String> erroMap = new HashMap<>();
        erroMap.put("message", exception.getMessage());

        return new ErroHandle(erroMap);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(DisabledException.class)
    public ErroHandle handleErrorDisabledException(DisabledException exception) {
        Map<String, String> erroMap = new HashMap<>();
        erroMap.put("message", exception.getMessage());

        return new ErroHandle(erroMap);
    }
    
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public ErroHandle handleAccessDeniedException(AccessDeniedException exception) {
        Map<String, String> erroMap = new HashMap<>();
        erroMap.put("message", exception.getMessage());

        return new ErroHandle(erroMap);
    }
        
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<String> handleAccessDeniedException1(AccessDeniedException ex) {
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso Negado: " + ex.getMessage());
//    }
}
