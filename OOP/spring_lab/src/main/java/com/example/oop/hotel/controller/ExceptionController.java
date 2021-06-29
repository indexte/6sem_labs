package com.example.oop.hotel.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
@CrossOrigin(origins = "http://localhost:4200")

public class ExceptionController {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity handleError() {
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
