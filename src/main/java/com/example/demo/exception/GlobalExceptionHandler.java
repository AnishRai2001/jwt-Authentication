package com.example.demo.exception;

import com.example.demo.responseStructure.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseStructure<String>> handleAllExceptions(Exception ex) {
        ex.printStackTrace();  // Log the full error in console for debugging

        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setMessage("Error: " + ex.getMessage());
        response.setData(null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
