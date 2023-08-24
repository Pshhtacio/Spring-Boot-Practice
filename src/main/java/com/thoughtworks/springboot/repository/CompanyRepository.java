package com.thoughtworks.springboot.repository;

import com.thoughtworks.springboot.exception.CompanyNotFoundException;
import com.thoughtworks.springboot.exception.CompanyValidationException;
import com.thoughtworks.springboot.model.Company;
import com.thoughtworks.springboot.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {

    private static final List<Company> companies = new ArrayList<>();
    private static final List<Employee> employees = new ArrayList<>();
    private static final Map<Long, Long> employeeCompanyMap = new HashMap<>();

    static {
        companies.add(new Company(1L, "OOCL"));
        companies.add(new Company(2L, "Thoughtworks"));
        companies.add(new Company(3L, "COSCO"));
        companies.add(new Company(4L, "DoubleDragon"));
        companies.add(new Company(5L, "Scape"));
        employees.add(new Employee(1L, "Ilnear", 42, "Male", 10000));
        employees.add(new Employee(2L, "Ilfar", 42, "Female", 20000));
        employees.add(new Employee(3L, "Ilclose", 42, "Male", 3000));
        employees.add(new Employee(4L, "Ilalmostthere", 42, "Female", 5000));
        employees.add(new Employee(5L, "Ilfaraway", 42, "Male", 14500));
        employeeCompanyMap.put(1L, 1L);
        employeeCompanyMap.put(2L, 1L);
        employeeCompanyMap.put(3L, 1L);
        employeeCompanyMap.put(4L, 2L);
        employeeCompanyMap.put(5L, 3L);
    }


    public List<Company> listAll() {
        return companies;
    }

    public Company findById(Long id) {
        return companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElseThrow(CompanyNotFoundException::new);
    }

    public List<Company> listByPage(Long pageNumber, Long pageSize) {
        return companies.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    public List<Employee> getEmployeesByCompanyId(Long companyId) {
        return employees.stream()
                .filter(employee -> employeeCompanyMap.containsValue(companyId) && employeeCompanyMap.get(employee.getId()).equals(companyId))
                .collect(Collectors.toList());
    }

    public Company addCompany(Company company) {
        Long id = generateNextId();
        validateCompanyName(company.getName());

        Company newCompany = new Company(id, company.getName());
        companies.add(newCompany);
        return newCompany;
    }

    //TODO Handle Business Logic and Validation in service, reference day8-Spring Boot.pdf page 8
    private void validateCompanyName(String name) {
        if (name == null || name.isEmpty()) {
            throw new CompanyValidationException("Company name cannot be null or empty.");
        }
    }

    private Long generateNextId() {
        //TODO local variables should be camel case, global contstant variables are upper case
        long STARTING_ID_MINUS_ONE = 0;
        return companies.stream()
                .mapToLong(Company::getId)
                .max()
                .orElse(STARTING_ID_MINUS_ONE) + 1;
    }

    public Company updateCompanyById(Long id, Company updatedCompany) {
        Company companyToUpdate = companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElse(null);

        //TODO Handle Business Logic and Validation in service, reference day8-Spring Boot.pdf page 8
        if (companyToUpdate != null) {
            companyToUpdate.setName(updatedCompany.getName());
            return companyToUpdate;
        }

        return null;
    }

    public void deleteCompanyById(Long id) {
        Company companyToDelete = companies.stream()
                .filter(company -> company.getId().equals(id))
                .findFirst()
                .orElse(null);

        //TODO Handle Business Logic and Validation in service, reference day8-Spring Boot.pdf page 8
        if (companyToDelete != null) {
            companies.remove(companyToDelete);
        } else {
            throw new CompanyNotFoundException("Company not found with id: " + id);
        }
    }
}
