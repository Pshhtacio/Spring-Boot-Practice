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
                .filter(employee -> employee.getGender().equals(gender))
                .collect(Collectors.toList());
    }

    public Employee addEmployee(Employee employee) {
       Long id = generateNextId();

       //need to validate properties in Employee, e.g. age -1. We skip here
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
}
