package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@ControllerAdvice
public class ExceptionHandling {

    private final Logger logger = Logger.getLogger(ExceptionHandling.class.getName());

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiExceptionResponse> onApiBadRequestException(BusinessException exception) {
        ApiExceptionResponse apiException = new ApiExceptionResponse(exception.getMessage(), exception.getStatus().value());
        return new ResponseEntity<>(apiException, exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiExceptionResponse> onException(Exception exception) {
        HttpStatus internalError = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiExceptionResponse apiException = new ApiExceptionResponse(exception.getMessage(), internalError.value());
        logger.log(Level.SEVERE, exception.getMessage(), exception);
        return new ResponseEntity<>(apiException, internalError);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiExceptionResponse> onConstraintValidationException(MethodArgumentNotValidException e) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;
        String error = Optional.ofNullable(e.getBindingResult()).map(BindingResult::getFieldError).map(FieldError::getDefaultMessage).orElse("");
        ApiExceptionResponse apiException = new ApiExceptionResponse(error, badRequest.value());
        return new ResponseEntity<>(apiException, badRequest);
    }

}