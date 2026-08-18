package org.example.payroll.dao;

import org.example.payroll.model.Employee;

import java.util.List;

public interface EmployeeDAO {

    Employee save(Employee employee);

    Employee findById(Long id);

    List<Employee> findAll();

    Employee update(Employee employee);

    void delete(Long id);

    List<Employee> searchByName(String name);
    Employee findByEmail(String email);
}