package com.thoughtworks.springboot.employee.service;

import com.thoughtworks.springboot.employee.model.Employee;
import com.thoughtworks.springboot.employee.repository.EmployeeRepository;

public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {
        return employeeRepository.insert(employee);
    }
}
