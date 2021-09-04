package com.example.assignment.controllerAdvice;

import com.example.assignment.exceptions.AssignmentException;
import com.example.assignment.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class DefaultControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AssignmentException.class)
    public ResponseEntity<ErrorResponse> handleAssignmentException(AssignmentException exception, WebRequest request){
        ErrorResponse errorResponse = ErrorResponse.builder().message(exception.getMessage()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
