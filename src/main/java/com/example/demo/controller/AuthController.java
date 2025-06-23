package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Employee;

import com.example.demo.responseStructure.ResponseStructure;
import com.example.demo.service.EmployeeService;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private com.example.demo.jwtConfig.JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<Employee>> register(@RequestBody Employee employee) {
        ResponseStructure<Employee> response = new ResponseStructure<>();
        try {
            Employee saved = employeeService.saveEmployee(employee);
            response.setStatus(HttpStatus.CREATED.value());
            response.setMessage("Employee registered successfully");
            response.setData(saved);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // This handles duplicate username/email errors thrown from service layer
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setMessage(e.getMessage()); // e.g. "Email already exists"
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Handle unexpected errors
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setMessage("An unexpected error occurred");
            response.setData(null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<String>> login(@RequestBody Employee employee) {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(employee.getUsername(), employee.getPassword())
            );

            if (auth.isAuthenticated()) {
                String jwtToken = jwtService.generateToken(
                    employee.getUsername(),
                    auth.getAuthorities().iterator().next().getAuthority()
                );

                ResponseStructure<String> response = new ResponseStructure<>();
                response.setMessage("Login Successful");
                response.setStatus(200);
                response.setData(jwtToken);  // return JWT token here

                return ResponseEntity.ok(response);
            }
        } catch (AuthenticationException e) {
            ResponseStructure<String> error = new ResponseStructure<>();
            error.setMessage("Invalid Credentials");
            error.setStatus(HttpStatus.UNAUTHORIZED.value());
            error.setData(null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }

        ResponseStructure<String> error = new ResponseStructure<>();
        error.setMessage("Authentication failed");
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        error.setData(null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }
}

