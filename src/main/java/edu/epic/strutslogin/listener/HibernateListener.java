/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.epic.strutslogin.listener;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author himal
 */
public class HibernateListener {

    private static HibernateListener hibernateListener;
    private SessionFactory sessionFactory;

    private HibernateListener() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

    }

    public static HibernateListener getInstance() {
        return hibernateListener == null ? hibernateListener = new HibernateListener() : hibernateListener;
    }

    public Session getSession() {

        return sessionFactory.openSession();
    }

}
