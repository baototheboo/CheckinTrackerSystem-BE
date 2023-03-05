package com.example.ctsbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionObject  handleMethodArgumentException(MethodArgumentNotValidException exception){
        ExceptionObject exceptionObject = new ExceptionObject();
        Map<String,String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(
                fieldError -> {
                    errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
                    exceptionObject.setError(errorMap);
                    exceptionObject.setCode(HttpStatus.OK.value());
                }
        );
        return exceptionObject;
    }
}
