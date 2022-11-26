
package edu.epic.strutslogin.db;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


/**
 *
 * @author himal
 */
public class SessionConnector {

   private static SessionConnector hibernateListener;
  private SessionFactory sessionFactory;

   private SessionConnector() {
        StandardServiceRegistry build = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
      
        Metadata meta = new MetadataSources(build).getMetadataBuilder().build();

       
        sessionFactory = meta.getSessionFactoryBuilder().build();

    }

    public static SessionConnector getInstance() {
        return hibernateListener == null ? hibernateListener = new SessionConnector() : hibernateListener;
    }

    public Session getSession() {

        return sessionFactory.openSession();
    }

}
