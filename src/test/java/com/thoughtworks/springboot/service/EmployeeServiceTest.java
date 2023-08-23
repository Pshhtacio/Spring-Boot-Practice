package com.thoughtworks.springboot.service;

import com.thoughtworks.springboot.exception.EmployeeCreateException;
import com.thoughtworks.springboot.exception.EmployeeNotFoundException;
import com.thoughtworks.springboot.model.Employee;
import com.thoughtworks.springboot.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {


    private EmployeeService employeeService;
    private EmployeeRepository mockedEmployeeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize the mocks
        mockedEmployeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeService(mockedEmployeeRepository);
    }

    @Test
    void should_return_created_employee_when_create_given_employee_service_and_employee_with_valid_age() {
        //given
        Employee employee = new Employee(null, "Lucy", 20, "Female", 3000);
        Employee savedEmployee = new Employee(1L, "Lucy", 20, "Female", 3000);
        when(mockedEmployeeRepository.insert(employee)).thenReturn(savedEmployee);

        //when
        Employee employeeResponse = employeeService.create(employee);

        //then
        assertEquals(savedEmployee.getId(), employeeResponse.getId());
        assertEquals("Lucy", employee.getName());
        assertEquals(20, employee.getAge());
        assertEquals("Female", employee.getGender());
        assertEquals(3000, employee.getSalary());
    }

    @Test
    void should_throw_exception_when_create_given_employee_service_and_employee_whose_age_is_less_than_18() {
        //Given
        Employee employee = new Employee(null, "Lucy", 17, "Female", 3000);

        //When
        EmployeeCreateException employeeCreateException = assertThrows(EmployeeCreateException.class, () -> {
            employeeService.create(employee);
        });

        //then
        assertEquals("Employee must be 18-65", employeeCreateException.getMessage());
    }

    @Test
    void should_set_isActive_to_true_when_new_employee_is_created_given_employee_service_and_valid_employee() {
        //given
        Employee employee = new Employee(null, "Lucy", 20, "Female", 3000);
        Employee savedEmployee = new Employee(1L, "Lucy", 20, "Female", 3000);
        when(mockedEmployeeRepository.insert(employee)).thenReturn(savedEmployee);

        //when
        Employee employeeResponse = employeeService.create(employee);

        //then
        assertEquals(savedEmployee.getIsActive(), employeeResponse.getIsActive());
    }

    @Test
    void should_set_isActive_to_false_when_existing_employee_is_deleted_given_employee_service_and_existing_employee_id() {
        // Given
        Long employeeId = 1L;
        Employee existingEmployee = new Employee(1L, "Lucy", 20, "Female", 3000);
        when(mockedEmployeeRepository.findById(employeeId)).thenReturn(existingEmployee);

        // When
        boolean isEmployeeDeleted = employeeService.delete(employeeId);

        // Then
        assertTrue(isEmployeeDeleted);
        assertFalse(existingEmployee.getIsActive());

        verify(mockedEmployeeRepository).updateEmployee(argThat(tempEmployee -> {
            assertFalse(tempEmployee.getIsActive());
            assertEquals(employeeId, tempEmployee.getId());
            assertEquals("Lucy", tempEmployee.getName());
            assertEquals(20, tempEmployee.getAge());
            assertEquals("Female", tempEmployee.getGender());
            assertEquals(3000, tempEmployee.getSalary());

            return true;
        }));
    }

    @Test
    void should_update_employee_when_update_given_employee_service_and_valid_employee() {
        Long employeeId = 1L;
        Employee existingEmployee = new Employee(employeeId, "Lucy", 20, "Female", 3000);
        Employee updatedEmployee = new Employee(employeeId, "Updated Name", 21, "Female", 426969);

        when(mockedEmployeeRepository.findById(employeeId)).thenReturn(existingEmployee);
        when(mockedEmployeeRepository.updateEmployee(updatedEmployee)).thenReturn(updatedEmployee);

        Employee result = employeeService.update(updatedEmployee);

        assertEquals(updatedEmployee.getAge(), result.getAge());
        assertEquals(updatedEmployee.getSalary(), result.getSalary());
        assertTrue(existingEmployee.getIsActive());

        ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(mockedEmployeeRepository).updateEmployee(employeeCaptor.capture());

        Employee updatedResultEmployee = employeeCaptor.getValue();
        assertTrue(updatedResultEmployee.getIsActive());
        assertEquals(employeeId, updatedResultEmployee.getId());
        assertEquals(21, updatedResultEmployee.getAge());
        assertEquals(426969, updatedResultEmployee.getSalary());
    }

    @Test
    void should_throw_exception_when_update_given_employee_service_and_invalid_employee() {
        long invalidEmployeeId = 99L;
        Employee updatedEmployee = new Employee(1L, "Updated Name", 21, "Female", 4000);
        when(mockedEmployeeRepository.findById(invalidEmployeeId)).thenReturn(null);

        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.update(updatedEmployee);
        });
    }

    @Test
    void should_return_all_employees_when_listAllEmployees_called() {
        List<Employee> mockEmployees = new ArrayList<>();
        mockEmployees.add(new Employee(1L, "John Doe", 30, "Male", 50000));
        mockEmployees.add(new Employee(2L, "Jane Smith", 25, "Female", 45000));
        when(mockedEmployeeRepository.listAll()).thenReturn(mockEmployees);

        List<Employee> result = employeeService.listAllEmployees();

        assertEquals(2, result.size());
        assertEquals("John Doe", result.get(0).getName());
        assertEquals("Jane Smith", result.get(1).getName());
    }

    @Test
    void should_return_employee_when_findEmployeeById_called_with_valid_id() {
        Long employeeId = 1L;
        Employee mockEmployee = new Employee(employeeId, "John Doe", 30, "Male", 50000);
        when(mockedEmployeeRepository.findById(employeeId)).thenReturn(mockEmployee);

        Employee result = employeeService.findEmployeeById(employeeId);

        assertEquals(employeeId, result.getId());
        assertEquals("John Doe", result.getName());
    }

    @Test
    void should_throw_exception_when_findEmployeeById_called_with_invalid_id() {
        Long invalidEmployeeId = 99L;
        when(mockedEmployeeRepository.findById(invalidEmployeeId)).thenReturn(null);

        assertThrows(EmployeeNotFoundException.class, () -> {
            employeeService.findEmployeeById(invalidEmployeeId);
        });
    }


}
