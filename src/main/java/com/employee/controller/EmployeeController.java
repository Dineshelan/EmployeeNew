package com.employee.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.entity.Employee;
import com.employee.exception.ResourceNotFoundException;
import com.employee.exception.UserNotFoundException;
import com.employee.service.EmployeeService;

@CrossOrigin("http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {
	@Autowired
	public EmployeeService employeeService;

	// get all employees
	@GetMapping("/getAll")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		List<Employee> employees = employeeService.findAllEmployee();
		return new ResponseEntity<>(employees,HttpStatus.OK);
	}		
	
	// create employee rest api
	@PostMapping("/add")
	public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
		Employee addemployee = employeeService.addEmployee(employee);
		return new ResponseEntity<>(addemployee,HttpStatus.CREATED);
	}

	// get employee by id rest api
	@GetMapping("/find/{id}")
	public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) throws UserNotFoundException {
		Optional<Employee> employee = employeeService.findEmployeeById(id);
		if (employee.isPresent()) {
            return new ResponseEntity<>(employee.get(), HttpStatus.OK);
        } else {
            throw new UserNotFoundException("Employee not found with id: " + id);
        }
	}
	
	// update employee rest api
	@PutMapping("/update/{id}")
	public ResponseEntity<Employee> updateEmployeeById(@PathVariable Long id, @RequestBody Employee employee) {
	    Employee updatedEmployee = employeeService.updateEmployeeById(id, employee);
	    if (updatedEmployee != null) {
	        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	
	// delete employee rest api
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) throws ResourceNotFoundException {
		if (!employeeService.findEmployeeById(id).isPresent()) {
            throw new ResourceNotFoundException("Employee not found with id: " + id);
        }
        employeeService.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
