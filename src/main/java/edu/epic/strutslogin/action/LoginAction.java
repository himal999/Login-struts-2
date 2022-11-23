/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.epic.strutslogin.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ResolverUtil.Test;
import edu.epic.strutslogin.bean.User;
import edu.epic.strutslogin.db.DbConnection;
import edu.epic.strutslogin.listener.HibernateListener;

import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;
import javax.print.attribute.standard.Destination;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author himal
 */
public class LoginAction extends ActionSupport {

    private Map<String, String> status = new HashMap<String, String>();

    public Map<String, String> getStatus() {
        return status;
    }

    public void setStatus(Map<String, String> status) {
        this.status = status;
    }

    public String isRegisterdUser() throws ClassNotFoundException, SQLException, IOException, FileNotFoundException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {

        String type = ServletActionContext.getRequest().getParameter("type");

        List<User> allUser = getAllUser();

        if (type.equalsIgnoreCase("username")) {
            String userName = ServletActionContext.getRequest().getParameter("uname");
            if (!allUser.isEmpty()) {
                for (User temp : allUser) {
                    System.out.println(temp.getUsername());
                    System.out.println(userName);
                    if (temp.getUsername().equals(userName)) {
                        status.put("data", "true");

                        return SUCCESS;
                    }
                }
            }

            status.put("data", "false");
            return SUCCESS;
        } else if (type.equalsIgnoreCase("nic")) {

            String nic = ServletActionContext.getRequest().getParameter("nic");

            if (!allUser.isEmpty()) {
                for (User temp : allUser) {
                    if (temp.getNic().equalsIgnoreCase(nic)) {
                        status.put("data", "true");

                        return SUCCESS;
                    }
                }
            }

            status.put("data", "false");
            return SUCCESS;

        } else if (type.equalsIgnoreCase("email")) {

            String email = ServletActionContext.getRequest().getParameter("email");

            if (!allUser.isEmpty()) {
                for (User temp : allUser) {
                    if (temp.getEmail().equalsIgnoreCase(email)) {
                        status.put("data", "true");

                        return SUCCESS;
                    }
                }
            }

            status.put("data", "false");
            return SUCCESS;

        } else {
            String userName = ServletActionContext.getRequest().getParameter("uname");
            if (!allUser.isEmpty()) {
                for (User temp : allUser) {
                    if (temp.getUsername().equals(userName)) {
                        status.put("data", "false");

                        return SUCCESS;
                    }
                }
            }

            status.put("data", "true");
            return SUCCESS;

        }

    }

    public String validLoginRequest() throws ClassNotFoundException, SQLException, IOException, FileNotFoundException, RollbackException, HeuristicMixedException, HeuristicRollbackException, SecurityException, IllegalStateException, SystemException {

        String userName = ServletActionContext.getRequest().getParameter("uname");
        String password = ServletActionContext.getRequest().getParameter("pwd");

        List<User> allUser = getAllUser();

        if (!allUser.isEmpty()) {
            for (User temp : allUser) {

                if (temp.getUsername().equals(userName) && temp.getPassword().equals(password)) {

                    if (updateLoginInfo(userName)) {
                        ActionContext.getContext().getSession().put("username", userName);
                        ActionContext.getContext().getSession().put("user", temp);

                        status.put("data", "true");
                        return SUCCESS;
                    }

                }
            }
        }
        status.put("data", "false");
        return SUCCESS;
    }

    public String registerUser() throws ParseException, SQLException, ClassNotFoundException {

        HttpServletRequest req = ServletActionContext.getRequest();

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date tempDate = formatter.parse(req.getParameter("dob"));

//        LocalDate tempDob = LocalDate.parse(req.getParameter("dob"));
        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());

        User user = new User(req.getParameter("uname"), req.getParameter("pwd"), req.getParameter("fname"), req.getParameter("lname"), req.getParameter("nic"), req.getParameter("city"), tempDate, req.getParameter("email"));

        String dob = formatter.format(user.getDob());
//        Connection connection = DbConnection.getInstance().getConnection();
//        PreparedStatement pst = connection.prepareStatement("INSERT INTO `user_detail` VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
//        pst.setString(1, user.getUsername());
//        pst.setObject(2, user.getPassword());
//        pst.setString(3, user.getFname());
//        pst.setString(4, user.getLname());
//        pst.setString(5, user.getNic());
//
//        pst.setString(6, user.getAddress());
//
//        pst.setDate(7, Date.valueOf(dob));
//
//        pst.setString(8, user.getEmail());
//
//        pst.setObject(9, user.getAccCreateInfo());
//        pst.setObject(10, user.getAccUpdateInfo());
//        pst.setObject(11, user.getAccLastLoginInfo());
//        pst.setObject(12, user.getAccLastLogoutInfo());

//        Session session = HibernateListener.getInstance().getSession();
//        org.hibernate.Transaction bt = session.beginTransaction();
//        NativeQuery query = session.createSQLQuery("INSERT INTO `user_detail`(username,password,fname,lname,nic,address,dob,email,acc_create_info,acc_last_login,acc_update_info) VALUES (?,?,?,?,?,?,?,?,?,?,?)");
//        query.setString(1, user.getUsername());
//        query.setString(2, user.getPassword());
//        query.setString(3, user.getFname());
//        query.setString(4, user.getLname());
//        query.setString(5, user.getNic());
//
//        query.setString(6, user.getAddress());
//
//        query.setDate(7, Date.valueOf(dob));
//
//        query.setString(8, user.getEmail());
//
//        query.setString(9, user.getAccCreateInfo());
//        query.setString(10, user.getAccLastLoginInfo());
//        session.save(user);
        if (true) {
//            bt.commit();

            HttpSession sess = req.getSession(true);
            sess.putValue("username", user.getUsername());
            sess.putValue("user", user);
            status.put("data", "true");
            return SUCCESS;
        }

        status.put("data", "false");
        return SUCCESS;

    }

    /*3rd methods zone*/
    private List<User> getAllUser() {


        
       Session openSession = HibernateListener.getInstance().getSession();
      
      
    
        org.hibernate.Transaction t = openSession.beginTransaction();
     

        List<User> users = openSession.createQuery("from User").list();

        t.commit();

        return users;

    }

    private boolean updateLoginInfo(String userName) throws SQLException, ClassNotFoundException {

        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());
       
       Session openSession = HibernateListener.getInstance().getSession();
      
      
    
        org.hibernate.Transaction t = openSession.beginTransaction();
      
      
        Query query = openSession.createQuery("UPDATE User SET acc_last_login=:time WHERE username=:username");
        query.setParameter("time", time);
        query.setParameter("username", userName);
        
        
        if(query.executeUpdate()>0){
        
            t.commit();
            return true;
        
        }else{
        
            t.rollback();
            t.commit();
            return false;
        
        }
       
    }

}
