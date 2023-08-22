package com.thoughtworks.springbootemployee.repository;

import com.thoughtworks.springbootemployee.model.Company;
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

}
