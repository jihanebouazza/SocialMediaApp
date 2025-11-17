package org.example.socialmediaapp.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.Instant;
import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private final MessageSource messages;

    public GlobalExceptionHandler(MessageSource messages) {
        this.messages = messages;
    }

    private ApiError build(String code, String message, HttpStatus status, HttpServletRequest req) {
        return new ApiError(Instant.now(), status.value(), code, message, req.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(Exception ex, HttpServletRequest req, Locale locale) {
        String m = messages.getMessage("error.params.invalid", null, "Invalid parameters", locale);
        return ResponseEntity.badRequest().body(build("PARAMS_NOT_VALID", m + ": " + ex.getMessage(), HttpStatus.BAD_REQUEST, req));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req, Locale locale) {
        String m = messages.getMessage("error.body.invalid", null, "Invalid request body", locale);
        return ResponseEntity.badRequest().body(build("BODY_NOT_VALID", m, HttpStatus.BAD_REQUEST, req));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req, Locale locale) {
        String m = messages.getMessage("error.resource.notfound", new Object[]{ex.getResourceName(), ex.getId()}, ex.getMessage(), locale);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(build("RESOURCE_NOT_FOUND", m, HttpStatus.NOT_FOUND, req));
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ApiError> handleNoHandler(NoHandlerFoundException ex, HttpServletRequest req, Locale locale) {
        String m = messages.getMessage("error.path.notfound", null, "Path not found", locale);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(build("PATH_NOT_FOUND", m, HttpStatus.NOT_FOUND, req));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleAll(Exception ex, HttpServletRequest req, Locale locale) {

        log.error("Unhandled exception at path: {}", req.getRequestURI(), ex);

        String m = messages.getMessage("error.server", null, "Server error", locale);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(build("SERVER_ERROR", m, HttpStatus.INTERNAL_SERVER_ERROR, req));
    }

}
