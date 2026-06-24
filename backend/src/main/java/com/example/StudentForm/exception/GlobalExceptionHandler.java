package com.example.StudentForm.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 1. Handles Validation Errors (e.g., @NotNull, @Email, @Size)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // 2. Handles Database Constraint Violations (e.g., duplicate unique keys)
@ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
public ResponseEntity<Map<String, String>> handleDatabaseConflict(org.springframework.dao.DataIntegrityViolationException ex) {
    Map<String, String> response = new HashMap<>();
    response.put("message", "Error: Duplicate record found. Please check your Email, Mobile, or Enrollment Number.");
    return new ResponseEntity<>(response, HttpStatus.CONFLICT);
}

 @ExceptionHandler(EntityNotFoundException.class)
public ResponseEntity<String> handleNotFound(EntityNotFoundException ex) { // Use the specific type
    return new ResponseEntity<>("Error: The requested resource was not found.", HttpStatus.NOT_FOUND);
}

    // 4. Handles Bad Requests (e.g., malformed JSON)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(Exception ex) {
        return new ResponseEntity<>("Error: Malformed JSON request.", HttpStatus.BAD_REQUEST);
    }

    // 5. Catch-All: Handles all other unexpected server errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        return new ResponseEntity<>("An internal error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}