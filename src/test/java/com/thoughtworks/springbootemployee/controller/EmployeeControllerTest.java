package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class EmployeeControllerTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void given_employees_exists_when_listAll_then_list_of_employees_is_returned() {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "John", 30, "Male", 50000));
        employees.add(new Employee(2L, "Alice", 25, "Female", 60000));

        when(employeeRepository.listAll()).thenReturn(employees);
        List<Employee> result = employeeController.listAll();

        assertEquals(employees, result);
    }

    @Test
    public void given_employee_exists_when_findEmployeeById_then_employee_is_returned() {
        Long employeeId = 1L;
        Employee employee = new Employee(employeeId, "John", 30, "Male", 50000);

        when(employeeRepository.findById(employeeId)).thenReturn(employee);
        Employee result = employeeController.findById(employeeId);

        assertEquals(employee, result);
    }

    @Test
    public void given_new_employee_when_addEmployee_then_employee_is_saved_and_returned() {
        Employee newEmployee = new Employee(null, "Jane", 28, "Female", 55000);
        Employee savedEmployee = new Employee(1L, "Jane", 28, "Female", 55000);

        when(employeeRepository.addEmployee(newEmployee)).thenReturn(savedEmployee);
        Employee result = employeeController.addEmployee(newEmployee);

        assertEquals(savedEmployee, result);
    }

    @Test
    public void given_updatedEmployee_when_updateEmployee_then_employee_is_updated_and_returned() {
        Long employeeId = 1L;
        Employee updatedEmployee = new Employee(employeeId, "The Name", 35, "Male", 60000);

        when(employeeRepository.updateEmployee(updatedEmployee)).thenReturn(updatedEmployee);
        Employee result = employeeController.updateEmployee(employeeId, updatedEmployee);

        assertEquals(updatedEmployee, result);
    }

    @Test
    void findByPage() {
    }
}