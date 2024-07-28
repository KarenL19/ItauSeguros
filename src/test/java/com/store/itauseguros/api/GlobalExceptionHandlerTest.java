package com.store.itauseguros.api;

import com.itauseguros.model.ErrorTemplate;
import com.store.itauseguros.advice.GlobalExceptionHandler;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    public void testHandleBadRequestException() {
        // Arrange
        ResponseStatusException ex = new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad Request");

        // Act
        ResponseEntity<ErrorTemplate> response = exceptionHandler.handleBadRequestException(ex);
        ErrorTemplate body = response.getBody();

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(body);
        assertEquals(HttpStatus.BAD_REQUEST.value(), body.getErrorCode());
        assertEquals("Bad Request", body.getMessage());
        assertNotNull(body.getTimestamp());
        assertNotNull(body.getTraceId());
    }

    @Test
    public void testHandleGenericException() {
        // Arrange
        Exception ex = new Exception("Generic error");

        // Act
        ResponseEntity<ErrorTemplate> response = exceptionHandler.handleGenericException(ex);
        ErrorTemplate body = response.getBody();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(body);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), body.getErrorCode());
        assertEquals("Generic error", body.getMessage());
        assertNotNull(body.getTimestamp());
        assertNotNull(body.getTraceId());
    }

}
