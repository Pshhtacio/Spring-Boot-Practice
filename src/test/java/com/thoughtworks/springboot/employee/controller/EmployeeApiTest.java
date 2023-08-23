package com.thoughtworks.springboot.employee.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.thoughtworks.springboot.employee.model.Employee;
import com.thoughtworks.springboot.employee.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
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
    void should_return_all_given_employees_when_perform_get_employees() throws Exception {
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
    void should_return_the_employees_when_perform_get_employee_given_an_employeed_id() throws Exception {
        Employee johnDoe = employeeRepository.insert(new Employee("John Doe", 42, "Male", 696969));
        employeeRepository.insert(new Employee("Jane Doe", 69, "Female", 101010));

        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + johnDoe.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(johnDoe.getId()))
                .andExpect(jsonPath("$.name").value(johnDoe.getName()))
                .andExpect(jsonPath("$.age").value(johnDoe.getAge()))
                .andExpect(jsonPath("$.gender").value(johnDoe.getGender()))
                .andExpect(jsonPath("$.salary").value(johnDoe.getSalary()));
    }

    @Test
    void should_return_404_not_found_when_perform_get_employee_given_a_nonexistent_id() throws Exception {
        Long nonExistentEmployeeId = 99L;
        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees/" + nonExistentEmployeeId))
                .andExpect(status().isNotFound());
    }

    @Test
    void should_return_the_employee_by_given_gender_when_perform_get_employee() throws Exception {
        Employee johnDoe = employeeRepository.insert(new Employee("John Doe", 42, "Male", 696969));
        employeeRepository.insert(new Employee("Jane Doe", 69, "Female", 101010));

        mockMvcClient.perform(MockMvcRequestBuilders.get("/employees").param("gender", "Male"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(johnDoe.getId()))
                .andExpect(jsonPath("$[0].name").value(johnDoe.getName()))
                .andExpect(jsonPath("$[0].age").value(johnDoe.getAge()))
                .andExpect(jsonPath("$[0].gender").value(johnDoe.getGender()))
                .andExpect(jsonPath("$[0].salary").value(johnDoe.getSalary()));
    }

    @Test
    void should_return_the_employee_when_perform_post_employee_given_a_new_employee_with_json_format() throws Exception {
        Employee newEmployee = new Employee("John Doe", 42, "Male", 696969);

        mockMvcClient.perform(MockMvcRequestBuilders.post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").value(newEmployee.getName()))
                .andExpect(jsonPath("$.age").value(newEmployee.getAge()))
                .andExpect(jsonPath("$.gender").value(newEmployee.getGender()))
                .andExpect(jsonPath("$.salary").value(newEmployee.getSalary()));
    }

    @Test
    void should_return_updated_employee_by_given_id_when_perform_put_employee() throws Exception {
        Employee johnDoe = employeeRepository.insert(new Employee("John Doe", 42, "Male", 696969));
        Employee updatedEmployee = new Employee(johnDoe.getId(), "John Doe", 69, "Male", 426942);

        mockMvcClient.perform(MockMvcRequestBuilders.put("/employees/" + johnDoe.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedEmployee.getId()))
                .andExpect(jsonPath("$.name").value(updatedEmployee.getName()))
                .andExpect(jsonPath("$.age").value(updatedEmployee.getAge()))
                .andExpect(jsonPath("$.gender").value(updatedEmployee.getGender()))
                .andExpect(jsonPath("$.salary").value(updatedEmployee.getSalary()));
    }

    @Test
    void should_return_204_when_perform_delete_employee_by_given_id() throws Exception {
        Employee johnDoe = employeeRepository.insert(new Employee("John Doe", 42, "Male", 696969));
        employeeRepository.insert(new Employee("Jane Doe", 69, "Female", 101010));

        mockMvcClient.perform(MockMvcRequestBuilders.delete("/employees/" + johnDoe.getId()))
                .andExpect(status().isNoContent());

    }
}
