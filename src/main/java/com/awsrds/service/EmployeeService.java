package com.awsrds.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awsrds.domain.EmployeeDomain;
import com.awsrds.repository.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepository employeeRepository;
	
	public void saveEmployee(EmployeeDomain employee) {
		try {
			employeeRepository.save(employee);	
		}
		catch (Exception e) {
			throw e;
		}
		
	}
	
	public EmployeeDomain getEmployeeById(Long id)
	{
		return employeeRepository.findById(id).get();
	}
	
	public List<EmployeeDomain> getAllEmployee()
	{
		return employeeRepository.findAll();
	}
}
