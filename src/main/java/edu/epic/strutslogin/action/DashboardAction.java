/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.epic.strutslogin.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import edu.epic.strutslogin.bean.User;
import edu.epic.strutslogin.db.DbConnection;
import edu.epic.strutslogin.listener.HibernateListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Session;
import org.hibernate.query.Query;

/**
 *
 * @author himal
 */
public class DashboardAction extends ActionSupport {

    private Map<String, String> status = new HashMap<String, String>();

    public Map<String, String> getStatus() {
        return status;
    }

    public void setStatus(Map<String, String> status) {
        this.status = status;
    }

    public String updateUser() throws ParseException, SQLException, ClassNotFoundException {
        HttpServletRequest req = ServletActionContext.getRequest();

        String userName = ActionContext.getContext().getSession().get("username").toString();

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date tempDate = formatter.parse(req.getParameter("dob"));

        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());

        java.util.Date dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(time);

        User user = new User(req.getParameter("uname"), req.getParameter("pwd"), req.getParameter("fname"), req.getParameter("lname"), req.getParameter("nic"), req.getParameter("city"), tempDate, req.getParameter("email"), dateTime, dateTime, dateTime);

        Session openSession = HibernateListener.getInstance().getSession();

        org.hibernate.Transaction t = openSession.beginTransaction();
        Query query = openSession.createQuery("UPDATE User SET username=:newUser,fname=:fname,lname=:lname,nic=:nic,address=:address,dob=:dob,email=:email,acc_update_info=:time WHERE username=:username");
        query.setParameter("newUser", user.getUsername());
        query.setParameter("fname", user.getFname());
        query.setParameter("lname", user.getLname());
        query.setParameter("nic", user.getNic());
        query.setParameter("address", user.getAddress());
        query.setParameter("dob", user.getDob());
        query.setParameter("email", user.getEmail());

        query.setParameter("time", time);
        query.setParameter("username", userName);

        if (query.executeUpdate() > 0) {

            t.commit();
            HttpSession sess = req.getSession();
            sess.putValue("username", user.getUsername());
            sess.putValue("user", user);
            status.put("data", "true");
            return SUCCESS;

        } else {
            t.rollback();
            t.commit();
            status.put("data", "false");
            return SUCCESS;
        }

    }

    public String dropUser() throws SQLException, ClassNotFoundException {
        HttpServletRequest req = ServletActionContext.getRequest();
        HttpSession session = req.getSession();
        String userName = (String) session.getAttribute("username");

        Session openSession = HibernateListener.getInstance().getSession();

        org.hibernate.Transaction t = openSession.beginTransaction();

        Query query = openSession.createQuery("delete User where username = :username");
        query.setParameter("username", userName);
        if (query.executeUpdate() > 0) {
            t.commit();
            session.removeAttribute("username");
            session.invalidate();

            status.put("data", "true");
            return SUCCESS;
        }
        
        t.rollback();
        t.commit();
        status.put("data", "false");
        return SUCCESS;
    }

    public String logOutRequest() throws ClassNotFoundException, SQLException {

        String userName = ActionContext.getContext().getSession().get("username").toString();

        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());

        Session openSession = HibernateListener.getInstance().getSession();

        org.hibernate.Transaction t = openSession.beginTransaction();
        Query query = openSession.createQuery("UPDATE User SET acc_last_logout=:time WHERE username=:username");
        query.setParameter("time", time);
        query.setParameter("username", userName);

        if (query.executeUpdate() > 0) {

            t.commit();
            HttpServletRequest req = ServletActionContext.getRequest();
            HttpSession session = req.getSession();

            session.removeAttribute("username");
            session.invalidate();

            status.put("data", "true");
            return SUCCESS;

        } else {

            t.rollback();
            t.commit();
            status.put("data", "false");
            return SUCCESS;

        }

    }
}
