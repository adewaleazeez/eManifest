/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.emanifest.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate Utility class with a convenient method to get Session Factory object.
 *
 * @author Adewale Azeez
 */
public final class HibernateUtil {

    private static final SessionFactory sessionFactory;

    /**
     *
     */
    public HibernateUtil() {
        super();
    // TODO Auto-generated constructor stub
    }


    static {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
