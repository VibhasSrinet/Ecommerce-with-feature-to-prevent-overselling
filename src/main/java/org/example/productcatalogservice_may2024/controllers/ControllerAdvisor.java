package org.example.productcatalogservice_may2024.controllers;

import org.example.productcatalogservice_may2024.exceptions.InvalidRoleAccessException;
import org.example.productcatalogservice_may2024.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<String> handleExceptions(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidRoleAccessException.class)
    public  ResponseEntity<String> handleInvalidRoleAccessException(InvalidRoleAccessException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

}
