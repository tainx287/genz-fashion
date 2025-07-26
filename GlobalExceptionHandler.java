package com.ecommerce.genz_fashion.exception;

import com.ecommerce.genz_fashion.payload.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.io.PrintWriter;
import java.io.StringWriter;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Đọc giá trị từ application.properties. Mặc định là 'never'.
    @Value("${server.error.include-stacktrace:never}")
    private String includeStackTrace;

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return buildErrorResponse(ex, ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
        return buildErrorResponse(ex, ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException(DuplicateResourceException ex) {
        return buildErrorResponse(ex, ex.getMessage(), HttpStatus.CONFLICT);
    }

    /**
     * Handles validation errors for request bodies annotated with @Valid.
     * Returns a list of invalid fields.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Validation Failed");
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errorResponse.addValidationError(error.getField(), error.getDefaultMessage())
        );
        addStackTraceIfEnabled(errorResponse, ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Handler chung cho tất cả các lỗi không xác định (lỗi 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request) {
        // Log the exception for debugging purposes, especially for unexpected errors.
        logger.error("An unexpected internal server error occurred:", ex);

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected internal server error occurred.");
        addStackTraceIfEnabled(errorResponse, ex);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(Exception ex, String message, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(status.value(), message);
        addStackTraceIfEnabled(errorResponse, ex);
        return new ResponseEntity<>(errorResponse, status);
    }

    private void addStackTraceIfEnabled(ErrorResponse errorResponse, Exception ex) {
        if ("always".equalsIgnoreCase(includeStackTrace)) {
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            errorResponse.setStackTrace(sw.toString());
        }
    }
}