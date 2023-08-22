package com.thoughtworks.springboot.company.repository;

import com.thoughtworks.springboot.company.model.Company;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {

    private static final List<Company> companies = new ArrayList<>();

    static {
        companies.add(new Company(1L, "OOCL"));
        companies.add(new Company(2L, "Thoughtworks"));
    }

    public List<Company> listAll() {
        return companies;
    }
}
