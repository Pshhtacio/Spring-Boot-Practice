package com.thoughtworks.springboot.service;

import com.thoughtworks.springboot.exception.EmployeeCreateException;
import com.thoughtworks.springboot.exception.EmployeeNotFoundException;
import com.thoughtworks.springboot.model.Employee;
import com.thoughtworks.springboot.repository.EmployeeRepository;

public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {
        if (employee.hasInvalidAge()) {
            throw new EmployeeCreateException("Employee must be 18-65");
        }
        return employeeRepository.insert(employee);
    }

    public boolean delete(Long employeeId) {
        Employee existingEmployee = employeeRepository.findById(employeeId);

        if (existingEmployee == null) {
            throw new EmployeeNotFoundException("Employee not found with id: " + employeeId);
        } else {
            existingEmployee.setIsActive(false);
            employeeRepository.updateEmployee(existingEmployee);
            return true;
        }
    }
}
