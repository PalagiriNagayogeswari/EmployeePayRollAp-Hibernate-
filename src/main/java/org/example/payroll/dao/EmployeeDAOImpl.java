package org.example.payroll.dao;

import org.example.payroll.model.Employee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {

    private final SessionFactory sessionFactory;


    public EmployeeDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }



    // CREATE


    @Override
    public Employee save(Employee employee) {

        Session session = sessionFactory.openSession();

        Transaction transaction = null;

        try {

            transaction = session.beginTransaction();

            session.persist(employee);

            transaction.commit();

            return employee;

        } catch (Exception exception) {

            if (transaction != null) {
                transaction.rollback();
            }

            throw exception;

        } finally {

            session.close();
        }
    }



    // GET BY ID


    @Override
    public Employee findById(Long id) {

        Session session = sessionFactory.openSession();

        try {

            return session.get(Employee.class, id);

        } finally {

            session.close();
        }
    }



    // GET ALL


    @Override
    public List<Employee> findAll() {

        Session session = sessionFactory.openSession();

        try {

            Query<Employee> query =
                    session.createQuery(
                            "FROM Employee",
                            Employee.class
                    );

            return query.getResultList();

        } finally {

            session.close();
        }
    }



    // UPDATE


    @Override
    public Employee update(Employee employee) {

        Session session = sessionFactory.openSession();

        Transaction transaction = null;

        try {

            transaction = session.beginTransaction();

            Employee updatedEmployee =
                    session.merge(employee);

            transaction.commit();

            return updatedEmployee;

        } catch (Exception exception) {

            if (transaction != null) {
                transaction.rollback();
            }

            throw exception;

        } finally {

            session.close();
        }
    }



    // DELETE


    @Override
    public void delete(Long id) {

        Session session = sessionFactory.openSession();

        Transaction transaction = null;

        try {

            transaction = session.beginTransaction();

            Employee employee =
                    session.get(Employee.class, id);

            if (employee != null) {

                session.remove(employee);
            }

            transaction.commit();

        } catch (Exception exception) {

            if (transaction != null) {
                transaction.rollback();
            }

            throw exception;

        } finally {

            session.close();
        }
    }



    // SEARCH BY NAME


    @Override
    public List<Employee> searchByName(String name) {

        Session session = sessionFactory.openSession();

        try {

            Query<Employee> query =
                    session.createQuery(
                            """
                            FROM Employee e
                            WHERE LOWER(e.name)
                            LIKE LOWER(:name)
                            """,
                            Employee.class
                    );

            query.setParameter(
                    "name",
                    "%" + name + "%"
            );

            return query.getResultList();

        } finally {

            session.close();
        }
    }

    @Override
    public Employee findByEmail(String email) {

        Session session = sessionFactory.openSession();

        try {

            Query<Employee> query =
                    session.createQuery(
                            "FROM Employee e WHERE e.email = :email",
                            Employee.class
                    );

            query.setParameter("email", email);

            return query.uniqueResult();

        } finally {

            session.close();
        }
    }
}