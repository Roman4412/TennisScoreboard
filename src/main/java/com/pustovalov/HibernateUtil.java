package com.pustovalov;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {
    //TODO make a correct singleton for multithreading
    private static SessionFactory sessionFactory;

    private HibernateUtil() {

    }
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.configure();
            sessionFactory = configuration.buildSessionFactory();

        }
        return sessionFactory;
    }
}
