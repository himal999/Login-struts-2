/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.epic.strutslogin.util;

import edu.epic.strutslogin.bean.User;
import edu.epic.strutslogin.db.SessionConnector;

import java.util.List;
import org.hibernate.Session;

import org.hibernate.query.Query;

/**
 *
 * @author himal
 */
public class Crud {

    private static Session openSession;
    private static org.hibernate.Transaction t;

    public static boolean updateOrDelete(String query, Object... args) {

        try {

            openSession = SessionConnector.getInstance().getSession();
            Query tempQuery = openSession.createQuery(query);

            t = openSession.beginTransaction();

            for (int i = 0; i < args.length; i++) {

                tempQuery.setParameter(i, args[i]);

            }

            if (tempQuery.executeUpdate() > 0) {

                t.commit();
                return true;

            } else {

                t.rollback();
                return false;
            }
        } catch (Exception e) {
            t.rollback();
            e.printStackTrace();
            return false;
        } finally {

            openSession.close();

        }

    }

    public static List<User> select() {

        try {
            openSession = SessionConnector.getInstance().getSession();

            t = openSession.beginTransaction();
            List<User> list = openSession.createQuery("from User").list();
           
            return list;
        } catch (Exception e) {
            e.printStackTrace();
       
            return null;
        } finally {
            openSession.close();
        }

    }

    public static User save(User user) {

        try {
            openSession = SessionConnector.getInstance().getSession();

            t = openSession.beginTransaction();

            openSession.save(user);
            t.commit();
            return user;
        } catch (Exception e) {
            e.printStackTrace();
            t.rollback();
            return null;
        } finally {
            openSession.close();
        }

    }

}
