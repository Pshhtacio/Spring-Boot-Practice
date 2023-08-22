package com.thoughtworks.springbootcompany.controller;

import com.thoughtworks.springbootcompany.model.Company;
import com.thoughtworks.springbootcompany.repository.CompanyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CompanyControllerTest {

    @InjectMocks
    private CompanyController companyController;

    @Mock
    private CompanyRepository companyRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void given_existing_companies_when_listAllCompanies_then_return_company_list() {
        // Arrange
        Company company1 = new Company(1L, "Company A");
        Company company2 = new Company(2L, "Company B");
        List<Company> companies = Arrays.asList(company1, company2);

        when(companyRepository.listAll()).thenReturn(companies);

        ResponseEntity<List<Company>> response = companyController.listAllCompanies();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(companies, response.getBody());
    }
}
