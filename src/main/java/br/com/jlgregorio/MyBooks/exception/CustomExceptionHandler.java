package br.com.jlgregorio.MyBooks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<CustomExceptionResponse> handleGenericExceptions(Exception ex, WebRequest request){
        CustomExceptionResponse customExceptionResponse = new CustomExceptionResponse(
                new Date(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(customExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public final ResponseEntity<CustomExceptionResponse> handleResourceNotFoundException(Exception ex, WebRequest request){
        String message = ex.getMessage().equals("") ? "Resource Not Found!" : ex.getMessage();
        CustomExceptionResponse customExceptionResponse = new CustomExceptionResponse(
                new Date(),
                message,
                request.getDescription(false)
        );
        return new ResponseEntity<>(customExceptionResponse, HttpStatus.NOT_FOUND);

    }



}
