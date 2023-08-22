package com.thoughtworks.springboot.company.model;

import com.thoughtworks.springboot.employee.model.Employee;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Resource
public class Company {
    private Long id;
    private String name;
    private List<Employee> employees = new ArrayList<>();

    public Company(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}