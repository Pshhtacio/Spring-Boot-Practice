package com.thoughtworks.springboot.service;

import com.thoughtworks.springboot.exception.EmployeeCreateException;
import com.thoughtworks.springboot.model.Employee;
import com.thoughtworks.springboot.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    private EmployeeService employeeService;
    private EmployeeRepository mockedEmployeeRepository;

    @BeforeEach
    void setUp() {
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
}
