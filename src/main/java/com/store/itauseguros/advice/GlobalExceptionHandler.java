package com.store.itauseguros.advice;

import com.itauseguros.model.ErrorTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.UUID;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ResponseStatusException.class})
    public ResponseEntity<ErrorTemplate> handleBadRequestException(ResponseStatusException ex) {
        ErrorTemplate errorDetail = new ErrorTemplate();
        errorDetail.setErrorCode(ex.getStatusCode().value());
        errorDetail.setMessage(ex.getReason());
        errorDetail.setTimestamp(LocalDateTime.now().toString());
        errorDetail.setTraceId(UUID.fromString(UUID.randomUUID().toString()));

        return new ResponseEntity<>(errorDetail, ex.getStatusCode());
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorTemplate> handleGenericException(Exception ex) {
        ErrorTemplate errorDetail = new ErrorTemplate();
        errorDetail.setErrorCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        String message = ex.getCause() != null ? ex.getCause().getMessage() : ex.getMessage();
        errorDetail.setMessage(message);
        errorDetail.setTimestamp(LocalDateTime.now().toString());
        errorDetail.setTraceId(UUID.fromString(UUID.randomUUID().toString()));

        return new ResponseEntity<>(errorDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}