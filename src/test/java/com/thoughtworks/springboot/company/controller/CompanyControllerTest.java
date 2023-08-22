package com.thoughtworks.springboot.company.controller;

import com.thoughtworks.springboot.company.model.Company;
import com.thoughtworks.springboot.company.repository.CompanyRepository;
import com.thoughtworks.springboot.employee.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CompanyControllerTest {//HAPPY CASE ONLY

    @InjectMocks
    private CompanyController companyController;

    @Mock
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void given_existing_companies_when_listAllCompanies_then_return_company_list() {
        Company company1 = new Company(1L, "Company A");
        Company company2 = new Company(2L, "Company B");
        List<Company> companies = Arrays.asList(company1, company2);

        when(companyRepository.listAll()).thenReturn(companies);
        ResponseEntity<List<Company>> response = companyController.listAllCompanies();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(companies, response.getBody());
    }

    @Test
    void given_existing_company_id_when_findCompanyById_then_return_company() {
        Long companyId = 1L;
        Company expectedCompany = new Company(companyId, "Company A");

        when(companyRepository.findById(companyId)).thenReturn(expectedCompany);
        Company result = companyController.findCompanyById(companyId);

        assertEquals(expectedCompany, result);
    }

    @Test
    void given_existing_company_id_when_getEmployeesByCompanyId_then_return_employee_list() {
        Long companyId = 1L;

        Employee employee1 = new Employee(1L, "John", 14, "Male", 5000);
        Employee employee2 = new Employee(2L, "Jane", 25, "Female",10000);
        List<Employee> employees = Arrays.asList(employee1, employee2);

        when(companyRepository.getEmployeesByCompanyId(companyId)).thenReturn(employees);

        ResponseEntity<List<Employee>> response = companyController.getEmployeesByCompanyId(companyId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(employees, response.getBody());
    }

    @Test
    void given_pageNumber_and_pageSize_when_findCompaniesByPage_then_return_company_list() {
        Long pageNumber = 1L;
        Long pageSize = 2L;
        List<Company> companies = new ArrayList<>();
        companies.add(new Company(1L, "Company A"));
        companies.add(new Company(2L, "Company B"));

        when(companyRepository.listByPage(pageNumber, pageSize)).thenReturn(companies);
        List<Company> response = companyController.findCompaniesByPage(pageNumber, pageSize);

        assertEquals(companies, response);
    }

    @Test
    void given_valid_company_when_addCompany_then_return_created_status_and_company() {
        Company validCompany = new Company(null, "New Company");

        when(companyRepository.addCompany(validCompany)).thenReturn(validCompany);

        ResponseEntity<Object> response = companyController.addCompany(validCompany);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(validCompany, response.getBody());
    }

    @Test
    void given_valid_company_when_updateCompanyById_then_return_updated_company() {
        Long companyId = 1L;
        Company updatedCompany = new Company(companyId, "Updated Company");

        when(companyRepository.updateCompanyById(companyId, updatedCompany)).thenReturn(updatedCompany);
        ResponseEntity<Object> response = companyController.updateCompanyById(companyId, updatedCompany);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedCompany, response.getBody());
    }

    @Test
    void given_existing_company_id_when_deleteCompanyById_then_return_no_content() {
        Long companyId = 1L;

        doNothing().when(companyRepository).deleteCompanyById(companyId);
        ResponseEntity<Void> response = companyController.deleteCompanyById(companyId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
