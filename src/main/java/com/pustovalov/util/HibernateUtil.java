package com.pustovalov.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {

    private static volatile SessionFactory sessionFactory;

    private HibernateUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            synchronized (HibernateUtil.class) {
                if (sessionFactory == null) {
                    Configuration configuration = new Configuration();
                    configuration.configure();
                    sessionFactory = configuration.buildSessionFactory();
                }
            }
        }
        return sessionFactory;
    }
}
