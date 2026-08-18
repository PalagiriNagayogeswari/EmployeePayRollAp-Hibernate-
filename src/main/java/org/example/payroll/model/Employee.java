package org.example.payroll.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(
        name = "employees",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_employee_email",
                        columnNames = "email"
                )
        }
)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(length = 100)
    private String department;

    @Column(length = 100)
    private String designation;

    @Column(name = "basic_salary", nullable = false)
    private BigDecimal basicSalary;

    @Column
    private BigDecimal hra;

    @Column
    private BigDecimal da;


    // No-argument constructor required by Hibernate
    public Employee() {
    }


    public Employee(
            String name,
            String email,
            String department,
            String designation,
            BigDecimal basicSalary,
            BigDecimal hra,
            BigDecimal da) {

        this.name = name;
        this.email = email;
        this.department = department;
        this.designation = designation;
        this.basicSalary = basicSalary;
        this.hra = hra;
        this.da = da;
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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }


    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }


    public BigDecimal getBasicSalary() {
        return basicSalary;
    }

    public void setBasicSalary(BigDecimal basicSalary) {
        this.basicSalary = basicSalary;
    }


    public BigDecimal getHra() {
        return hra;
    }

    public void setHra(BigDecimal hra) {
        this.hra = hra;
    }


    public BigDecimal getDa() {
        return da;
    }

    public void setDa(BigDecimal da) {
        this.da = da;
    }
}