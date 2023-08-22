package com.thoughtworks.springboot.company.controller;

import com.thoughtworks.springboot.company.exception.CompanyNotFoundException;
import com.thoughtworks.springboot.company.model.Company;
import com.thoughtworks.springboot.company.repository.CompanyRepository;
import com.thoughtworks.springboot.employee.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/{id}")
    public Company findCompanyById(@PathVariable Long id) {
        return companyRepository.findById(id);
    }

    @GetMapping(params = {"pageNumber", "pageSize"})
    public List<Company> findCompaniesByPage(@RequestParam Long pageNumber, Long pageSize) {
        return companyRepository.listByPage(pageNumber, pageSize);
    }

    @GetMapping("/{companyId}/employees")
    public ResponseEntity<List<Employee>> getEmployeesByCompanyId(@PathVariable Long companyId) {
        try {
            List<Employee> employees = companyRepository.getEmployeesByCompanyId(companyId);
            return ResponseEntity.ok(employees);
        } catch (CompanyNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }
}
