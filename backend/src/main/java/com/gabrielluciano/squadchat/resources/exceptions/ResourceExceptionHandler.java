package com.gabrielluciano.squadchat.resources.exceptions;

import com.gabrielluciano.squadchat.model.exceptions.DuplicatedResourceException;
import com.gabrielluciano.squadchat.model.exceptions.InvalidCredentialsException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ResourceExceptionHandler {

    @ExceptionHandler(DuplicatedResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> handleDuplicatedResourceException(DuplicatedResourceException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("Duplicated Resource");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setTimestamp(Instant.now());
        errorResponse.setStatus(HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorResponse> handleInvalidCredentialsException(InvalidCredentialsException ex, HttpServletRequest request) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setError("Invalid Credentials");
        errorResponse.setMessage(ex.getMessage());
        errorResponse.setPath(request.getRequestURI());
        errorResponse.setTimestamp(Instant.now());
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED.value());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
