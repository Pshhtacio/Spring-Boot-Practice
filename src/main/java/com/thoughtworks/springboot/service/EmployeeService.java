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

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setAge(updatedEmployee.getAge());
        existingEmployee.setGender(updatedEmployee.getGender());
        existingEmployee.setSalary(updatedEmployee.getSalary());

        return employeeRepository.updateEmployee(existingEmployee);
    }

    public List<Employee> listAllEmployees() {
        return employeeRepository.listAll();
    }


    public Employee findEmployeeById(Long id) {
        return employeeRepository.findById(id);
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
