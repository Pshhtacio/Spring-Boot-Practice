package com.thoughtworks.springbootemployee.controller;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeRepository {
    private static final List<Employee> employees = new ArrayList<>();
    private static Long STARTING_ID_MINUS_ONE = 0L;

    static {
        employees.add(new Employee(1l, "Ilnear", 42, "Male", 10000));
        employees.add(new Employee(2l, "Ilfar", 42, "Female", 20000));
        employees.add(new Employee(3l, "Ilclose", 42, "Male", 3000));
        employees.add(new Employee(4l, "Ilalmostthere", 42, "Female", 5000));
        employees.add(new Employee(5l, "Ilfaraway", 42, "Male", 14500));
    }

    public List<Employee> listAll() {
        return employees;
    }

    public Employee findById(Long id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst()
                .orElseThrow(EmployeeNotFoundException::new);
    }

    public List<Employee> findByGender(String gender) {
        return employees.stream()
                .filter(employee -> employee.getGender().equalsIgnoreCase(gender))
                .collect(Collectors.toList());
    }

    public Employee addEmployee(Employee employee) {
        Long id = generateNextId();
        if (employee.getName() == null || employee.getName().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        if (employee.getAge() <= 0) {
            throw new IllegalArgumentException("Age must be a positive integer.");
        }
        if (employee.getGender() == null || !employee.getGender().equalsIgnoreCase("Male")
                && !employee.getGender().equalsIgnoreCase("Female")) {
            throw new IllegalArgumentException("Gender must be 'Male' or 'Female'.");
        }
        if (employee.getSalary() <= 0) {
            throw new IllegalArgumentException("Salary must be a positive number.");
        }
        Employee newEmployee = new Employee(id,
                employee.getName(),
                employee.getAge(),
                employee.getGender(),
                employee.getSalary());

        employees.add(newEmployee);
        return newEmployee;
    }

    private Long generateNextId() {
        return employees.stream()
                .mapToLong(Employee::getId)
                .max()
                .orElse(STARTING_ID_MINUS_ONE) + 1;
    }

    public List<Employee> listByPage(Long pageNumber, Long pageSize) {
        return employees.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public Employee updateEmployee(Employee employeeToBeUpdated) {
        Employee employeeToUpdate = employees.stream()
                .filter(employee -> employee.getId().equals(employeeToBeUpdated.getId()))
                .findFirst()
                .orElse(null);

        if (employeeToUpdate != null) {
            employeeToUpdate.setName(employeeToBeUpdated.getName());
            employeeToUpdate.setGender(employeeToBeUpdated.getGender());
            employeeToUpdate.setAge(employeeToBeUpdated.getAge());
            return employeeToUpdate;
        } else {
            throw new EmployeeNotFoundException("Employee not found with id: " + employeeToBeUpdated.getId());
        }
    }
}
