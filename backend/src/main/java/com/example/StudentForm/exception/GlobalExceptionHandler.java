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
// makes this class global exception handler.
public class GlobalExceptionHandler {

    // 1. Handles Validation Errors  @NotNull, @Email, @Size etc.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // 2. Handles field-specific duplicate checks done before save in the controller
    // (e.g. duplicate email, mobile, or enrollment number checked individually)
    @ExceptionHandler(DuplicateFieldException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateField(DuplicateFieldException ex) {
        return new ResponseEntity<>(ex.getFieldErrors(), HttpStatus.CONFLICT);
    }

    // 3. Handles Database Constraint Violations ->> duplicate unique keys
    // Acts as a safety net for race conditions where two requests slip past
    // the controller's existsBy... checks at the same instant.
    @ExceptionHandler(org.springframework.dao.DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDatabaseConflict(org.springframework.dao.DataIntegrityViolationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Error: Duplicate record found. Please check your Email, Mobile, or Enrollment Number.");
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    // 4. Handles Entity Not Found .
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException ex) { // Use the specific type
        return new ResponseEntity<>("Error: The requested resource was not found.", HttpStatus.NOT_FOUND);
    }

    // 5. Handles Bad Requests, Bad Json
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(Exception ex) {
        return new ResponseEntity<>("Error: Malformed JSON request.", HttpStatus.BAD_REQUEST);
    }

    // 6. Handles all other unexpected server errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex) {
        return new ResponseEntity<>("An internal error occurred. Please try again later.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}