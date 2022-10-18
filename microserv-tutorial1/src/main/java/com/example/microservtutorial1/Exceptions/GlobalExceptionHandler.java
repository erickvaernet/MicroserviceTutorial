package com.example.microservtutorial1.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsMessage> handleGeneralExceptions(Exception ex, WebRequest request){
        return getResponseEntityWithErrorMessage(request,ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ErrorDetailsMessage> handleNotValidArgumentsException(MethodArgumentNotValidException ex, WebRequest request){
        String errorMessage = new FieldErrorMessage(ex.getFieldErrors()).toString();
        return getResponseEntityWithErrorMessage(request,errorMessage,HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorDetailsMessage> getResponseEntityWithErrorMessage(WebRequest request, String message, HttpStatus status ){
        ErrorDetailsMessage errorMessage = new ErrorDetailsMessage(
                LocalDateTime.now(),
                message,
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorMessage,status);
    }
}
