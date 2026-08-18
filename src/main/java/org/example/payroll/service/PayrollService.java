package org.example.payroll.service;

import org.example.payroll.model.Payroll;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface PayrollService {

    // UC-07
    BigDecimal calculateSalary(Long employeeId);

    // UC-08
    Payroll generatePayroll(
            Long employeeId,
            LocalDate month,
            BigDecimal deductions
    );

    // UC-09
    List<Payroll> getEmployeePayroll(Long employeeId);

    // UC-10
    List<Payroll> getPayrollHistory();

    // UC-11
    void deletePayroll(Long id);
}