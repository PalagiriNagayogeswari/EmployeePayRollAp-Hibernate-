package org.example.payroll.controller;

import org.example.payroll.model.Payroll;
import org.example.payroll.service.PayrollService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/payroll")
public class PayrollController {

    private final PayrollService payrollService;

    public PayrollController(
            PayrollService payrollService) {

        this.payrollService = payrollService;
    }



    // UC-08
    // POST /payroll


    @PostMapping
    public ResponseEntity<Payroll> generatePayroll(

            @RequestParam("employeeId") Long employeeId,

            @RequestParam("month") LocalDate month,

            @RequestParam(name="deductions",defaultValue = "0")
            BigDecimal deductions) {

        Payroll payroll =
                payrollService.generatePayroll(
                        employeeId,
                        month,
                        deductions
                );

        return new ResponseEntity<>(
                payroll,
                HttpStatus.CREATED
        );
    }



    // UC-10
    // GET /payroll/history


    @GetMapping("/history")
    public ResponseEntity<List<Payroll>>
    getPayrollHistory() {

        return ResponseEntity.ok(
                payrollService.getPayrollHistory()
        );
    }


    // UC-11
    // DELETE /payroll/{id}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deletePayroll(
            @PathVariable("id") Long id) {

        payrollService.deletePayroll(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}