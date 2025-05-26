package com.example.demo.controller;

import com.example.demo.entity.Employee;
import com.example.demo.responseStructure.ResponseStructure;
import com.example.demo.service.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private AuthenticationManager authenticationManager;

//    @PostMapping
//    public ResponseEntity<ResponseStructure<Employee>> saveEmployee(@RequestBody Employee employee) {
//        ResponseStructure<Employee> response = new ResponseStructure<>();
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(employee.getUsername(), employee.getPassword());
//
//        try {
//            Authentication authenticate = authenticationManager.authenticate(token);
//
//            if (authenticate.isAuthenticated()) {
//                // Only save if authenticated
//                Employee saved = employeeService.saveEmployee(employee);
//
//                response.setStatus(HttpStatus.CREATED.value());
//                response.setMessage("Employee created successfully");
//                response.setData(saved);
//                return new ResponseEntity<>(response, HttpStatus.CREATED);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Failed to authenticate
//        response.setStatus(HttpStatus.UNAUTHORIZED.value());
//        response.setMessage("Employee not created successfully");
//        response.setData(null); // Or return the original `employee`, but usually null on failure
//        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseStructure<Employee>> getEmployee(@PathVariable int id) {
        Optional<Employee> employee = employeeService.findEmployeeById(id);
        ResponseStructure<Employee> response = new ResponseStructure<>();

        if (employee.isPresent()) {
            response.setStatus(200);
            response.setMessage("Employee found");
            response.setData(employee.get());
            return ResponseEntity.ok(response);
        } else {
            response.setStatus(404);
            response.setMessage("Employee not found with ID: " + id);
            response.setData(null);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseStructure<List<Employee>>> getAllEmployees() {
        List<Employee> employees = employeeService.findAll();
        ResponseStructure<List<Employee>> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMessage("List of employees retrieved");
        response.setData(employees);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ResponseStructure<Employee>> updateEmployee(@RequestBody Employee employee) {
        Employee updated = employeeService.UpdateEmployee(employee);
        ResponseStructure<Employee> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMessage("Employee updated successfully");
        response.setData(updated);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseStructure<String>> deleteEmployee(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        ResponseStructure<String> response = new ResponseStructure<>();
        response.setStatus(200);
        response.setMessage("Employee deleted successfully");
        response.setData("Deleted ID: " + id);
        return ResponseEntity.ok(response);
    }
}
