package com.thoughtworks.springbootemployee.controller;

import com.thoughtworks.springbootemployee.model.Employee;
import com.thoughtworks.springbootemployee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EmployeeControllerTest { //HAPPY CASES ONLY

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
    public void given_gender_exists_in_repository_when_findByGender_then_list_of_employees_with_matching_gender_is_returned() {
        String gender = "Male";
        List<Employee> employeesWithGender = new ArrayList<>();
        employeesWithGender.add(new Employee(1L, "John", 30, "Male", 50000));
        employeesWithGender.add(new Employee(2L, "Mike", 28, "Male", 55000));

        when(employeeRepository.findByGender(gender)).thenReturn(employeesWithGender);
        List<Employee> result = employeeController.findByGender(gender);

        assertEquals(employeesWithGender, result);
    }

    @Test
    public void given_new_employee_when_addEmployee_then_employee_is_saved_and_returned() {
        Employee newEmployee = new Employee(null, "Jane", 28, "Female", 55000);
        Employee savedEmployee = new Employee(1L, "Jane", 28, "Female", 55000);

        when(employeeRepository.addEmployee(newEmployee)).thenReturn(savedEmployee);
        ResponseEntity<Object> result = employeeController.addEmployee(newEmployee);

        assertEquals(201, result.getStatusCodeValue());
        assertEquals(savedEmployee, result.getBody());
    }

    @Test
    public void given_updatedEmployee_when_updateEmployee_then_employee_is_updated_and_returned() {
        Long employeeId = 1L;
        Employee updatedEmployee = new Employee(employeeId, "The Name", 35, "Male", 60000);

        when(employeeRepository.updateEmployee(updatedEmployee)).thenReturn(updatedEmployee);
        ResponseEntity<Object> result = employeeController.updateEmployee(employeeId, updatedEmployee);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(updatedEmployee, result.getBody());
    }

    @Test
    public void given_existing_employee_id_when_deleteEmployee_then_employee_is_deleted() {
        Long employeeIdToDelete = 1L;

        doNothing().when(employeeRepository).deleteEmployee(employeeIdToDelete);
        ResponseEntity<Void> result = employeeController.deleteEmployee(employeeIdToDelete);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        assertEquals(204, result.getStatusCodeValue());
    }

    @Test
    public void given_pageNumber_and_pageSize_when_findByPage_then_list_of_employees_for_page_is_returned() {
        Long pageNumber = 1L;
        Long pageSize = 2L;
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1L, "John", 30, "Male", 50000));
        employees.add(new Employee(2L, "Alice", 25, "Female", 60000));

        when(employeeRepository.listByPage(pageNumber, pageSize)).thenReturn(employees);
        List<Employee> result = employeeController.findByPage(pageNumber, pageSize);

        assertEquals(employees, result);
    }
}