package org.example.payroll.service;

import org.example.payroll.dao.EmployeeDAO;
import org.example.payroll.dao.PayrollDAO;
import org.example.payroll.exception.EmployeeNotFoundException;
import org.example.payroll.model.Employee;
import org.example.payroll.model.Payroll;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PayrollServiceImpl implements PayrollService {

    private final EmployeeDAO employeeDAO;
    private final PayrollDAO payrollDAO;

    public PayrollServiceImpl(
            EmployeeDAO employeeDAO,
            PayrollDAO payrollDAO) {

        this.employeeDAO = employeeDAO;
        this.payrollDAO = payrollDAO;
    }



    // UC-07
    // CALCULATE SALARY
    // GET /employees/{id}/salary


    @Override
    public BigDecimal calculateSalary(Long employeeId) {

        Employee employee =
                employeeDAO.findById(employeeId);

        if (employee == null) {

            throw new EmployeeNotFoundException(
                    "Employee with id "
                            + employeeId
                            + " not found"
            );
        }

        BigDecimal basic =
                employee.getBasicSalary();

        BigDecimal hra =
                employee.getHra() != null
                        ? employee.getHra()
                        : BigDecimal.ZERO;

        BigDecimal da =
                employee.getDa() != null
                        ? employee.getDa()
                        : BigDecimal.ZERO;


        // Gross Salary = Basic + HRA + DA

        BigDecimal grossSalary =
                basic
                        .add(hra)
                        .add(da);

        return grossSalary;
    }


    // UC-08
    // GENERATE PAYROLL
    // POST /payroll


    @Override
    public Payroll generatePayroll(
            Long employeeId,
            LocalDate month,
            BigDecimal deductions) {

        // Find employee

        Employee employee =
                employeeDAO.findById(employeeId);

        if (employee == null) {

            throw new EmployeeNotFoundException(
                    "Employee with id "
                            + employeeId
                            + " not found"
            );
        }


        // Month is mandatory

        if (month == null) {

            throw new IllegalArgumentException(
                    "Payroll month is required"
            );
        }


        // Store only first day of month

        month = month.withDayOfMonth(1);


        // Check duplicate payroll

        if (payrollDAO.existsByEmployeeAndMonth(
                employeeId,
                month)) {

            throw new IllegalArgumentException(
                    "Payroll already exists for employee "
                            + employeeId
                            + " for "
                            + month
            );
        }


        // If deductions are not provided

        if (deductions == null) {

            deductions = BigDecimal.ZERO;
        }


        // Deductions cannot be negative

        if (deductions.compareTo(
                BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Deductions cannot be negative"
            );
        }


        // Get salary details

        BigDecimal basic =
                employee.getBasicSalary();

        BigDecimal hra =
                employee.getHra() != null
                        ? employee.getHra()
                        : BigDecimal.ZERO;

        BigDecimal da =
                employee.getDa() != null
                        ? employee.getDa()
                        : BigDecimal.ZERO;


        // Gross Salary

        BigDecimal grossSalary =
                basic
                        .add(hra)
                        .add(da);


        // Net Salary

        BigDecimal netSalary =
                grossSalary.subtract(
                        deductions
                );


        // Net salary cannot be negative

        if (netSalary.compareTo(
                BigDecimal.ZERO) < 0) {

            throw new IllegalArgumentException(
                    "Net salary cannot be negative"
            );
        }


        // Create Payroll

        Payroll payroll = new Payroll();

        payroll.setEmployee(employee);
        payroll.setMonth(month);
        payroll.setGrossSalary(grossSalary);
        payroll.setDeductions(deductions);
        payroll.setNetSalary(netSalary);


        // Save Payroll

        return payrollDAO.save(payroll);
    }


    // UC-09
    // GET EMPLOYEE PAYROLL
    // GET /employees/{id}/payroll


    @Override
    public List<Payroll> getEmployeePayroll(
            Long employeeId) {

        Employee employee =
                employeeDAO.findById(employeeId);

        if (employee == null) {

            throw new EmployeeNotFoundException(
                    "Employee with id "
                            + employeeId
                            + " not found"
            );
        }

        return payrollDAO.findByEmployeeId(
                employeeId
        );
    }



    // UC-10
    // GET PAYROLL HISTORY
    // GET /payroll/history


    @Override
    public List<Payroll> getPayrollHistory() {

        return payrollDAO.findAll();
    }


    // UC-11
    // DELETE PAYROLL
    // DELETE /payroll/{id}


    @Override
    public void deletePayroll(Long id) {

        Payroll payroll =
                payrollDAO.findById(id);

        if (payroll == null) {

            throw new RuntimeException(
                    "Payroll with id "
                            + id
                            + " not found"
            );
        }

        payrollDAO.delete(id);
    }
}