# Employee Payroll Application Using Hibernate

## Overview

Employee Payroll Application is a Java-based application developed using **Hibernate ORM** to manage employee and payroll information. The application performs database operations using Hibernate and PostgreSQL.

## Technologies Used

* Java
* Hibernate ORM
* PostgreSQL
* HQL
* Maven

## Features

* Add employee
* Get employee by ID
* Get all employees
* Update employee
* Delete employee
* Search employee by name
* Calculate salary
* Generate payroll
* Get employee payroll
* Get payroll history
* Delete payroll

## Hibernate Concepts Used

* Hibernate Configuration
* SessionFactory
* Session
* Transaction
* Entity Mapping
* HQL
* CRUD Operations

## Architecture

```text
Application
     |
     v
Hibernate
     |
     v
SessionFactory
     |
     v
Session
     |
     v
Transaction
     |
     v
PostgreSQL Database
```

## Database

The application uses **PostgreSQL** for storing employee and payroll information. Hibernate manages the persistence between Java entities and database tables.

## How to Run

Clone the repository:

```bash
git clone https://github.com/PalagiriNagayogeswari/EmployeePayRollAp-Hibernate-.git
```

Configure the PostgreSQL database and Hibernate connection properties, then build and run the application using Maven.

```bash
mvn clean install
```

## Author

**Palagiri Nagayogeswari**

GitHub: https://github.com/PalagiriNagayogeswari
