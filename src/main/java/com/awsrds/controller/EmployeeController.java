package com.awsrds.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.awsrds.domain.EmployeeDomain;
import com.awsrds.service.EmployeeService;

@RestController
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;
	
	@GetMapping("/get-employee-by-id/{id}")
	public EmployeeDomain retrieveEmployeeById(@PathVariable(value = "id") Long id) {
		return employeeService.getEmployeeById(id);
	}
	
	@PostMapping("/save-employee")
	public String saveEmployeeDetails(@RequestBody EmployeeDomain employee)
	{
		employeeService.saveEmployee(employee);
		return "Employee saved successfully";
	}
	
	@GetMapping("/get-all-employees")
	public List<EmployeeDomain> getAllEmployees()
	{
		return employeeService.getAllEmployee();
	}
	
	@GetMapping("/test")
	public String testMapping()
	{
		return "Employee service";
	}
}
