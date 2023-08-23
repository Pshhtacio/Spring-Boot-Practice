package com.thoughtworks.springboot.employee.controller;


import com.thoughtworks.springboot.employee.model.Employee;
import com.thoughtworks.springboot.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeApiTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private MockMvc mockMvcClient;

    EmployeeApiTest() {
    }

    @BeforeEach
    void cleanUpEmployeeData() {
        employeeRepository.cleanUpEmployeeData();
    }

    @Test
    void should_return_all_given_employees_when_perform_get_employees() throws Exception{
        Employee johnDoe = employeeRepository.insert(new Employee("John Doe", 42, "Male", 696969));
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(johnDoe.getId()))
                .andExpect(jsonPath("$[0].name").value(johnDoe.getName()))
                .andExpect(jsonPath("$[0].age").value(johnDoe.getAge()))
                .andExpect(jsonPath("$[0].gender").value(johnDoe.getGender()))
                .andExpect(jsonPath("$[0].salary").value(johnDoe.getSalary()));
    }

    @Test
    void should_return_the_employees_when_perform_get_employee_given_an_employeed_id() throws Exception{
        Employee johnDoe = employeeRepository.insert(new Employee("John Doe", 42, "Male", 696969));
        employeeRepository.insert(new Employee("Jane Doe", 69, "Female", 101010));
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + johnDoe.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(johnDoe.getId()))
                .andExpect(jsonPath("$[0].name").value(johnDoe.getName()))
                .andExpect(jsonPath("$[0].age").value(johnDoe.getAge()))
                .andExpect(jsonPath("$[0].gender").value(johnDoe.getGender()))
                .andExpect(jsonPath("$[0].salary").value(johnDoe.getSalary()));
    }

}
