package com.kafe.koffee.exception;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status,String message, String path) {

        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        status.value(),
                        status.getReasonPhrase(),
                        message,
                        path
                );
        return ResponseEntity
                .status(status)
                .body(response);
    }
    /*
     * 404 - Resource not found
     */

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(
            ResourceNotFoundException exception,
            HttpServletRequest request
    ) {

        logger.warn(
                "Resource not found: {}",
                exception.getMessage()
        );

        return buildResponse(
                HttpStatus.NOT_FOUND,
                exception.getMessage(),
                request.getRequestURI()
        );
    }
    /*
     * 409 - Duplicate resource
     */

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicate(
            DuplicateResourceException exception,
            HttpServletRequest request
    ) {

        logger.warn(
                "Duplicate resource: {}",
                exception.getMessage()
        );

        return buildResponse(
                HttpStatus.CONFLICT,
                exception.getMessage(),
                request.getRequestURI()
        );
    }
    /*
     * 400 - Invalid verification code
     */

    @ExceptionHandler(InvalidVerificationCodeException.class)
    public ResponseEntity<ErrorResponse> handleVerificationCode(
            InvalidVerificationCodeException exception,
            HttpServletRequest request
    ) {

        logger.warn(
                "Invalid verification code: {}",
                exception.getMessage()
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI()
        );
    }
    /*
     * 400 - Invalid reset token
     */

    @ExceptionHandler(InvalidPasswordResetTokenException.class)
    public ResponseEntity<ErrorResponse> handleResetToken(
            InvalidPasswordResetTokenException exception,
            HttpServletRequest request
    ) {

        logger.warn(
                "Invalid password reset token"
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI()
        );
    }
    /*
     * 400 - @Valid validation errors
     */

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            MethodArgumentNotValidException exception,
            HttpServletRequest request
    ) {
    	String message =
                exception.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error ->
                                error.getField()
                                        + ": "
                                        + error.getDefaultMessage()
                        )
                        .collect(Collectors.joining(", "));

        logger.warn(
                "Validation error: {}",
                message
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                message,
                request.getRequestURI()
        );
    }
    /*
     * Validation on request parameters/path variables
     */

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException exception,
            HttpServletRequest request
    ) {

        logger.warn(
                "Constraint violation: {}",
                exception.getMessage()
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI()
        );
    }
    /*
     * Database constraint errors
     */

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDatabaseError(
            DataIntegrityViolationException exception,
            HttpServletRequest request
    ) {

        logger.error(
                "Database constraint violation",
                exception
        );

        return buildResponse(
                HttpStatus.CONFLICT,
                "The requested operation violates a database constraint.",
                request.getRequestURI()
        );
    }
    /*
     * Spring Security - forbidden
     */

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(
            AccessDeniedException exception,
            HttpServletRequest request
    ) {

        logger.warn(
                "Access denied: {}",
                request.getRequestURI()
        );

        return buildResponse(
                HttpStatus.FORBIDDEN,
                "You do not have permission to perform this operation.",
                request.getRequestURI()
        );
    }
    /*
     * Illegal arguments
     */

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(
            IllegalArgumentException exception,
            HttpServletRequest request
    ) {

        logger.warn(
                "Illegal argument: {}",
                exception.getMessage()
        );

        return buildResponse(
                HttpStatus.BAD_REQUEST,
                exception.getMessage(),
                request.getRequestURI()
        );
    }
    /*
     * Everything else
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(
            Exception exception,
            HttpServletRequest request
    ) {

        logger.error(
                "Unhandled application exception",
                exception
        );

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred. Please try again later.",
                request.getRequestURI()
        );
    }


}
