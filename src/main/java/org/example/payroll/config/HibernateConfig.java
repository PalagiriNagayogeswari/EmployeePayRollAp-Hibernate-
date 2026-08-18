package org.example.payroll.config;

import org.example.payroll.model.Employee;
import org.example.payroll.model.Payroll;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class HibernateConfig {

    @Bean
    public SessionFactory sessionFactory() {

        Configuration configuration =
                new Configuration();

        //configuring the hibernate xml file that contains details for hibernate configuration.
        configuration.configure("hibernate.cfg.xml");

        configuration.addAnnotatedClass(
                Employee.class
        );
        configuration.addAnnotatedClass(
                Payroll.class
        );

        return configuration.buildSessionFactory();
    }
}