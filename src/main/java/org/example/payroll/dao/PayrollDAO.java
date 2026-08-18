package org.example.payroll.dao;

import org.example.payroll.model.Payroll;

import java.time.LocalDate;
import java.util.List;


//Payroll DAO interface defining the payroll DAO methods.
public interface PayrollDAO {

    Payroll save(Payroll payroll);

    Payroll findById(Long id);

    List<Payroll> findByEmployeeId(Long employeeId);

    List<Payroll> findAll();

    boolean existsByEmployeeAndMonth(
            Long employeeId,
            LocalDate month
    );

    void delete(Long id);
}