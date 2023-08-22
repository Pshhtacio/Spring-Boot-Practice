package com.thoughtworks.springboot.company.controller;

import com.thoughtworks.springboot.company.model.Company;
import com.thoughtworks.springboot.company.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CompanyControllerTest {//HAPPY CASE ONLY

    @InjectMocks
    private CompanyController companyController;

    @Mock
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void given_existing_companies_when_listAllCompanies_then_return_company_list() {
        Company company1 = new Company(1L, "Company A");
        Company company2 = new Company(2L, "Company B");
        List<Company> companies = Arrays.asList(company1, company2);

        when(companyRepository.listAll()).thenReturn(companies);
        ResponseEntity<List<Company>> response = companyController.listAllCompanies();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(companies, response.getBody());
    }

    @Test
    public void given_existing_company_id_when_FindCompanyById_then_return_company() {
        Long companyId = 1L;
        Company expectedCompany = new Company(companyId, "Company A");

        when(companyRepository.findById(companyId)).thenReturn(expectedCompany);
        ResponseEntity<Company> response = companyController.findCompanyById(companyId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCompany, response.getBody());
    }
}
