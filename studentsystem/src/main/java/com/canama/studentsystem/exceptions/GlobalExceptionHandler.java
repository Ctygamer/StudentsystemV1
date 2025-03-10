package com.canama.studentsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

/**
 * Globaler Exception-Handler, der alle unerwarteten Ausnahmen abfängt und eine
 * entsprechende Fehlermeldung zurückgibt.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Behandelt alle RuntimeExceptions und gibt eine Fehlermeldung zurück.
     *
     * @param ex Die aufgetretene Ausnahme.
     * @return Eine ResponseEntity mit einer Fehlermeldung.
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("error", ex.getMessage()));
    }
}
