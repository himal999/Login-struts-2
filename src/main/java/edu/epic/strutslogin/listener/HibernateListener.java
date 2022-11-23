
package edu.epic.strutslogin.listener;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author himal
 */
public class HibernateListener {

   private static HibernateListener hibernateListener;
  private SessionFactory sessionFactory;

   private HibernateListener() {
        StandardServiceRegistry build = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
      
        Metadata meta = new MetadataSources(build).getMetadataBuilder().build();

       
        sessionFactory = meta.getSessionFactoryBuilder().build();

    }

    public static HibernateListener getInstance() {
        return hibernateListener == null ? hibernateListener = new HibernateListener() : hibernateListener;
    }

    public Session getSession() {

        return sessionFactory.openSession();
    }

}
