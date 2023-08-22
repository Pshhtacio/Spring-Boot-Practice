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
    public void given_employees_when_listAll__then_list_of_employees_is_Returned() {
        // Given
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "John", 30, "Male", 50000));
        employees.add(new Employee(2L, "Alice", 25, "Female", 60000));
        when(employeeRepository.listAll()).thenReturn(employees);

        // When
        List<Employee> result = employeeController.listAll();

        // Then
        assertEquals(employees, result);
    }

    @Test
    void findById() {
    }

    @Test
    void findByGender() {
    }

    @Test
    void addEmployee() {
    }

    @Test
    void updateEmployee() {
    }

    @Test
    void findByPage() {
    }
}