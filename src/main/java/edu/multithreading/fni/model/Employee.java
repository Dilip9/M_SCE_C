package edu.multithreading.fni.model;


import java.util.Objects;

public class Employee {


    private Long id;

    private String name;

    private String email;

    private Double salary;

    private String phone;


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(name, employee.name) && Objects.equals(email, employee.email) && Objects.equals(salary, employee.salary) && Objects.equals(phone, employee.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, salary, phone);
    }

    public Employee() {

    }

    public Employee(Long id, String name, String email, Double salary, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.salary = salary;
        this.phone = phone;
    }

    public Employee(String name, Long id, Double salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }

    public Employee(Long id, String email, Double salary) {
        this.id = id;
        this.email = email;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee {" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                ", phone='" + phone + '\'' +
                '}';
    }
}
