package org.example.payroll.controller;

import org.example.payroll.model.Employee;
import org.example.payroll.model.Payroll;
import org.example.payroll.service.EmployeeService;
import org.example.payroll.service.PayrollService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final PayrollService payrollService;

    public EmployeeController(
            EmployeeService employeeService,
            PayrollService payrollService) {

        this.employeeService = employeeService;
        this.payrollService = payrollService;
    }


    // UC-01
    // ADD EMPLOYEE
    // POST /employees


    @PostMapping("/save")
    public ResponseEntity<Employee> addEmployee(
            @RequestBody Employee employee) {

        Employee savedEmployee =
                employeeService.addEmployee(employee);

        return new ResponseEntity<>(
                savedEmployee,
                HttpStatus.CREATED
        );
    }



    // UC-02
    // GET EMPLOYEE
    // GET /employees/{id}


    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployee(
            @PathVariable("id") Long id) {

        Employee employee =
                employeeService.getEmployee(id);

        return ResponseEntity.ok(employee);
    }



    // UC-03
    // GET ALL EMPLOYEES
    // GET /employees


    @GetMapping
    public ResponseEntity<List<Employee>>
    getAllEmployees() {

        List<Employee> employees =
                employeeService.getAllEmployees();

        return ResponseEntity.ok(employees);
    }



    // UC-04
    // UPDATE EMPLOYEE
    // PUT /employees/{id}


    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable("id") Long id,
            @RequestBody Employee employee) {

        Employee updatedEmployee =
                employeeService.updateEmployee(
                        id,
                        employee
                );

        return ResponseEntity.ok(
                updatedEmployee
        );
    }



    // UC-05
    // DELETE EMPLOYEE
    // DELETE /employees/{id}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable("id") Long id) {

        employeeService.deleteEmployee(id);

        return ResponseEntity.noContent().build();
    }



    // UC-06
    // SEARCH EMPLOYEE
    // GET /employees/search?name=Ravi

    @GetMapping("/search")
    public ResponseEntity<List<Employee>>
    searchEmployees(
            @RequestParam("name") String name) {

        List<Employee> employees =
                employeeService.searchEmployees(name);

        return ResponseEntity.ok(employees);
    }

    //UC-07
    //Calculating salary

    @GetMapping("/{id}/salary")
    public BigDecimal calculateSalary(@PathVariable("id")Long id){
        return payrollService.calculateSalary(id);
    }


    //UC-09
    //Getting employee payroll

    @GetMapping("/{id}/payroll")
    public List<Payroll> getEmployeePayrolls(@PathVariable("id") Long id){
        return payrollService.getEmployeePayroll(id);
    }
}