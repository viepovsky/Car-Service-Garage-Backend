package com.backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MyEntityNotFoundException.class)
    public ResponseEntity<Object> handleMyEntityNotFoundException(MyEntityNotFoundException exception){
        if (exception.getRecordId() != null) {
            return new ResponseEntity<>(exception.getText() + " record with given id: " + exception.getRecordId() + " doesn't exist", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(exception.getText() + " doesn't exist in database.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WrongInputDataException.class)
    public ResponseEntity<Object> handleWrongInputDataException(WrongInputDataException exception){
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
