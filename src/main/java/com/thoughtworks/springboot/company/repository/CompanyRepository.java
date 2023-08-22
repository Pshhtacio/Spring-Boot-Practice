package com.thoughtworks.springboot.company.repository;

import com.thoughtworks.springboot.company.exception.CompanyNotFoundException;
import com.thoughtworks.springboot.company.model.Company;
import com.thoughtworks.springboot.employee.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class CompanyRepository {

    private static final List<Company> companies = new ArrayList<>();
    private static final Map<Long, Company> employeeCompanyMap = new HashMap<>();

    static {
        companies.add(new Company(1L, "OOCL"));
        companies.add(new Company(2L, "Thoughtworks"));
        companies.add(new Company(3L, "COSCO"));
        companies.add(new Company(4L, "DoubleDragon"));
        companies.add(new Company(5L, "Scape"));
        employeeCompanyMap.put(1L, companies.get(0));
        employeeCompanyMap.put(2L, companies.get(1));
        employeeCompanyMap.put(3L, companies.get(3));
        employeeCompanyMap.put(4L, companies.get(1));
        employeeCompanyMap.put(5L, companies.get(0));
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
}
