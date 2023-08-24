package com.thoughtworks.springboot.service;

import com.thoughtworks.springboot.exception.EmployeeCreateException;
import com.thoughtworks.springboot.exception.EmployeeNotFoundException;
import com.thoughtworks.springboot.model.Employee;
import com.thoughtworks.springboot.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    public Employee create(Employee employee) {
        validateAge(employee);
        return employeeRepository.insert(employee);
    }

    public boolean delete(Long employeeId) {
        Employee existingEmployee = getExistingEmployee(employeeId);
        existingEmployee.setIsActive(false);
        employeeRepository.updateEmployee(existingEmployee);
        return true;
    }

    public Employee update(Employee updatedEmployee) {
        Employee existingEmployee = getExistingEmployee(updatedEmployee.getId());
        return employeeRepository.updateEmployee(existingEmployee);
    }

    public List<Employee> listAllEmployees() {
        return employeeRepository.listAll();
    }

    public Employee findEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee not found with id: " + id);
        }
        return employee;
    }

    public List<Employee> findEmployeeByGender(String gender) {
        return employeeRepository.findByGender(gender);
    }

    public List<Employee> findEmployeesByPage(Long pageNumber, Long pageSize) {
        if (pageNumber <= 0 || pageSize <= 0) {
            //TODO out of scope need to clarify
            throw new IllegalArgumentException("Page number and page size must be greater than zero.");
        }
        return employeeRepository.listByPage(pageNumber, pageSize);
    }

    private static void validateAge(Employee employee) {
        if (employee.hasInvalidAge()) {
            throw new EmployeeCreateException("Employee must be 18-65");
        }
    }

    private Employee getExistingEmployee(Long employeeId) {
        Employee existingEmployee = employeeRepository.findById(employeeId);
        if (existingEmployee == null) {
            throw new EmployeeNotFoundException("Employee not found with id: " + employeeId);
        }
        return existingEmployee;
    }
}
