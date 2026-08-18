package org.example.payroll.service;

import org.example.payroll.dao.EmployeeDAO;
import org.example.payroll.exception.EmployeeNotFoundException;
import org.example.payroll.model.Employee;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;


    public EmployeeServiceImpl(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }



    // ADD EMPLOYEE


    @Override
    public Employee addEmployee(Employee employee) {

        validateEmployee(employee);

        return employeeDAO.save(employee);
    }


    // GET EMPLOYEE


    @Override
    public Employee getEmployee(Long id) {

        Employee employee =
                employeeDAO.findById(id);

        if (employee == null) {

            throw new EmployeeNotFoundException(
                    "Employee with id "
                            + id
                            + " not found"
            );
        }

        return employee;
    }


    // GET ALL EMPLOYEES


    @Override
    public List<Employee> getAllEmployees() {

        return employeeDAO.findAll();
    }


    // UPDATE EMPLOYEE

    @Override
    public Employee updateEmployee(
            Long id,
            Employee employee) {

        // First check employee exists
        Employee existingEmployee =
                employeeDAO.findById(id);

        if (existingEmployee == null) {

            throw new EmployeeNotFoundException(
                    "Employee with id "
                            + id
                            + " not found"
            );
        }

        validateEmployee(employee);

        // Check whether email already belongs
        // to another employee
        Employee employeeWithSameEmail =
                employeeDAO.findByEmail(
                        employee.getEmail()
                );

        if (employeeWithSameEmail != null
                && !employeeWithSameEmail
                .getId()
                .equals(id)) {

            throw new IllegalArgumentException(
                    "Email already exists"
            );
        }

        // Keep the original ID
        employee.setId(id);

        return employeeDAO.update(employee);
    }


    // DELETE EMPLOYEE

    @Override
    public void deleteEmployee(Long id) {

        Employee employee =
                employeeDAO.findById(id);

        if (employee == null) {

            throw new EmployeeNotFoundException(
                    "Employee with id "
                            + id
                            + " not found"
            );
        }

        employeeDAO.delete(id);
    }


    // SEARCH


    @Override
    public List<Employee> searchEmployees(
            String name) {

        return employeeDAO.searchByName(name);
    }


    // VALIDATION

    private void validateEmployee(
            Employee employee) {

        if (employee.getName() == null
                || employee.getName().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Employee name is mandatory"
            );
        }


        if (employee.getEmail() == null
                || employee.getEmail().trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Employee email is mandatory"
            );
        }


        if (!employee.getEmail().matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            throw new IllegalArgumentException(
                    "Invalid email format"
            );
        }


        if (employee.getBasicSalary() == null
                || employee.getBasicSalary()
                .compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "Basic salary must be greater than zero"
            );
        }


        if (employee.getHra() == null) {

            employee.setHra(BigDecimal.ZERO);
        }


        if (employee.getDa() == null) {

            employee.setDa(BigDecimal.ZERO);
        }
    }

}