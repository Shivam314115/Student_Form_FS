package com.example.StudentForm.exception;

import java.util.Map;

// carries field-specific duplicate messages, e.g. { "email": "Duplicate email entered" }
public class DuplicateFieldException extends RuntimeException {
    private final Map<String, String> fieldErrors;

    public DuplicateFieldException(Map<String, String> fieldErrors) {
        super("Duplicate field(s) found");
        this.fieldErrors = fieldErrors;
    }

    public Map<String, String> getFieldErrors() {
        return fieldErrors;
    }
}