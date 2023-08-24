package com.thoughtworks.springboot.model;

public class Employee {
    private static final Long MIN_VALID_AGE = 18L;
    private static final Long MAX_VALID_AGE = 65L;
    private Boolean isActive;
    private Long id;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;

    private Long companyId;

    public Employee() {
        this.isActive = true;
    }

    public Employee(Long id, String name, Integer age, String gender, Integer salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.isActive = true;
    }

    public Employee(String name, int age, String gender, int salary) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.isActive = true;
    }

    //TODO Remove unused getters
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public boolean hasInvalidAge() {
        return getAge() < MIN_VALID_AGE || getAge() > MAX_VALID_AGE;
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
    //TODO Remove unused setter
    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }
    //TODO Remove unused setter
    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
