package com.thoughtworks.springboot.utility;

import com.thoughtworks.springboot.exception.EmployeeValidationException;
import com.thoughtworks.springboot.model.Employee;

public class EmployeeValidator {

    public static void validateEmployee(Employee employee) {
        validateName(employee);
        validateAge(employee);
        validateGender(employee);
        validateSalary(employee);
    }

    private static void validateSalary(Employee employee) {
        if (employee.getSalary() <= 0) {
            throw new EmployeeValidationException("Salary must be a positive number.");
        }
    }

    private static void validateGender(Employee employee) {
        if (employee.getGender() == null || !employee.getGender().equalsIgnoreCase("Male")
                && !employee.getGender().equalsIgnoreCase("Female")) {
            throw new EmployeeValidationException("Gender must be 'Male' or 'Female'.");
        }
    }

    private static void validateAge(Employee employee) {
        if (employee.getAge() <= 0) {
            throw new EmployeeValidationException("Age must be a positive integer.");
        }
    }

    private static void validateName(Employee employee) {
        if (employee.getName() == null || employee.getName().isEmpty()) {
            throw new EmployeeValidationException("Name cannot be null or empty.");
        }
    }
}
