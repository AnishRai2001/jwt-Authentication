package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.Employee;

public interface EmployeeService {
	
	Employee saveEmployee(Employee empoyee);
	Optional<Employee>findEmployeeById(int id);
	Employee UpdateEmployee(Employee employee);
	void deleteEmployee(int id);
	List<Employee>findAll();

}
