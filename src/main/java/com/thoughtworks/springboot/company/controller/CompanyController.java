package com.thoughtworks.springboot.company.controller;

import com.thoughtworks.springboot.company.model.Company;
import com.thoughtworks.springboot.company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(path = "companies")
@RestController
public class CompanyController {

    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @GetMapping
    public ResponseEntity<List<Company>> listAllCompanies() {
        List<Company> companies = companyRepository.listAll();
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> findCompanyById(@PathVariable Long id) {
        Company company = companyRepository.findById(id);
        return ResponseEntity.ok(company);
    }
}
