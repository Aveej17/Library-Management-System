package com.jeeva.LibraryManagementSystem.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(value = TxnException.class)
    public ResponseEntity<Object> handle(TxnException txnException){
        return new ResponseEntity<>(txnException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handle(Exception e){
        return new ResponseEntity<>(e.getMessage(),  HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ParameterMissingException.class)
    public ResponseEntity<Object> handle(ParameterMissingException parameterMissingException){
        return new ResponseEntity<>(parameterMissingException.getMessage(),  HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handle(MethodArgumentNotValidException methodArgumentNotValidException){
        return new ResponseEntity<>(methodArgumentNotValidException.getBindingResult().getFieldError().getDefaultMessage(),
                HttpStatus.BAD_REQUEST);
    }


}
