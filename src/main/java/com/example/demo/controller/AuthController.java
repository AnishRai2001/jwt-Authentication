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

    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<Employee>> register(@RequestBody Employee employee) {
        Employee saved = employeeService.saveEmployee(employee);  // Save with encoded password
        ResponseStructure<Employee> response = new ResponseStructure<>();
        response.setStatus(HttpStatus.CREATED.value());
        response.setMessage("Employee registered successfully");
        response.setData(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Employee employee) {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(employee.getUsername(), employee.getPassword())
            );

            if (auth.isAuthenticated()) {
                return ResponseEntity.ok("Login successful");
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }
}
