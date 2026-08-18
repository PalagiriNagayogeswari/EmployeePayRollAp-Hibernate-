package org.example.payroll.dao;

import org.example.payroll.model.Payroll;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;


//Payroll DAO implementation implementing all the payroll DAO methods.
@Repository
public class PayrollDAOImpl implements PayrollDAO {

    private final SessionFactory sessionFactory;

    public PayrollDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Payroll save(Payroll payroll) {

        Session session = sessionFactory.openSession();

        Transaction transaction =
                session.beginTransaction();

        session.persist(payroll);

        transaction.commit();

        session.close();

        return payroll;
    }

    @Override
    public Payroll findById(Long id) {

        Session session = sessionFactory.openSession();

        Payroll payroll =
                session.get(Payroll.class, id);

        session.close();

        return payroll;
    }

    @Override
    public List<Payroll> findByEmployeeId(Long employeeId) {

        Session session = sessionFactory.openSession();

        List<Payroll> payrollList =
                session.createQuery(
                                "FROM Payroll p WHERE p.employee.id = :employeeId",
                                Payroll.class
                        )
                        .setParameter("employeeId", employeeId)
                        .getResultList();

        session.close();

        return payrollList;
    }

    @Override
    public List<Payroll> findAll() {

        Session session = sessionFactory.openSession();

        List<Payroll> payrollList =
                session.createQuery(
                                "FROM Payroll",
                                Payroll.class
                        )
                        .getResultList();

        session.close();

        return payrollList;
    }

    @Override
    public boolean existsByEmployeeAndMonth(
            Long employeeId,
            LocalDate month) {

        Session session = sessionFactory.openSession();

        Long count =
                session.createQuery(
                                "SELECT COUNT(p) " +
                                        "FROM Payroll p " +
                                        "WHERE p.employee.id = :employeeId " +
                                        "AND p.month = :month",
                                Long.class
                        )
                        .setParameter("employeeId", employeeId)
                        .setParameter("month", month)
                        .uniqueResult();

        session.close();

        return count > 0;
    }

    @Override
    public void delete(Long id) {

        Session session = sessionFactory.openSession();

        Transaction transaction =
                session.beginTransaction();

        Payroll payroll =
                session.get(Payroll.class, id);

        if (payroll != null) {
            session.remove(payroll);
        }

        transaction.commit();

        session.close();
    }
}