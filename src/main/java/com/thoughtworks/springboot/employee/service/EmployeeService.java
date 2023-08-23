package com.thoughtworks.springboot.employee.service;

import com.thoughtworks.springboot.employee.exception.EmployeeCreateException;
import com.thoughtworks.springboot.employee.model.Employee;
import com.thoughtworks.springboot.employee.repository.EmployeeRepository;

public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {
        if(employee.hasInvalidAge()){
            throw  new EmployeeCreateException("Employee must be 18-65");
        }
        return employeeRepository.insert(employee);
    }
}
