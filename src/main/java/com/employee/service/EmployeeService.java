package com.employee.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.entity.Employee;
import com.employee.repository.EmployeeRepository;

@Service
public class EmployeeService {
	@Autowired
	public EmployeeRepository employeeRepository;

	public List<Employee> findAllEmployee() {
		return employeeRepository.findAll();
	}

	public Employee addEmployee(Employee employee) {
		return employeeRepository.save(employee);
	}

	public Optional<Employee> findEmployeeById(Long id) {
		return employeeRepository.findById(id);
	}

	public Employee updateEmployeeById(Long id, Employee updatedEmployee) {
		Optional<Employee> optionalEmployee = employeeRepository.findById(id);
		if (optionalEmployee.isPresent()) {
			Employee existingEmployee = optionalEmployee.get();
			// Update the existing employee with the new details
			existingEmployee.setFirstName(updatedEmployee.getFirstName());
			existingEmployee.setLastName(updatedEmployee.getLastName());
			existingEmployee.setEmailId(updatedEmployee.getEmailId());
			// You can update other fields as needed
			return employeeRepository.save(existingEmployee);
		} else {
			// Handle the case where employee with the given ID is not found
			return null;
		}
	}

	public void deleteEmployeeById(Long id) {
		employeeRepository.deleteById(id);
	}

}
