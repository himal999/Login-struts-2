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
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
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

        
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        
        java.util.Date tempDate = formatter.parse(req.getParameter("dob"));

        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());

        User user = new User(req.getParameter("uname"), req.getParameter("pwd"), req.getParameter("fname"), req.getParameter("lname"), req.getParameter("nic"), req.getParameter("city"), tempDate, req.getParameter("email"), time);

        String dob = formatter.format(user.getDob());

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("UPDATE `user_detail` SET username=?,fname=?,lname=?,nic=?,address=?,dob=?,email=?,acc_update_info=? WHERE username=?");
        
        pst.setString(1, user.getUsername());
        pst.setString(2, user.getFname());
        pst.setString(3, user.getLname());
        pst.setString(4, user.getNic());
        pst.setString(5, user.getAddress());

        pst.setDate(6, Date.valueOf(dob));

        pst.setString(7, user.getEmail());

        pst.setObject(8, user.getAccUpdateInfo());
        pst.setString(9, String.valueOf(req.getSession().getAttribute("username")));

        if (pst.executeUpdate() > 0) {
            HttpSession sess = req.getSession(true);
            sess.putValue("username", user.getUsername());
            sess.putValue("user", user);

            status.put("data", "true");
            return SUCCESS;
        }

        status.put("data", "false");
        return SUCCESS;
    }

    public String dropUser() throws SQLException, ClassNotFoundException {
        HttpServletRequest req = ServletActionContext.getRequest();
        HttpSession session = req.getSession();
        String userName = (String) session.getAttribute("username");

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("DELETE FROM `user_detail` WHERE username = ?");
        pst.setObject(1, userName);
        if (pst.executeUpdate() > 0) {

            session.removeAttribute("username");
            session.invalidate();

            status.put("data", "true");
            return SUCCESS;
        }
        status.put("data", "false");
        return SUCCESS;
    }

    public String logOutRequest() throws ClassNotFoundException, SQLException {

        String userName = ActionContext.getContext().getSession().get("username").toString();

        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pst = connection.prepareStatement("UPDATE `user_detail` SET acc_last_logout=? WHERE username=?");
        pst.setObject(1, time);
        pst.setObject(2, userName);

        if (pst.executeUpdate() > 0) {
            HttpServletRequest req = ServletActionContext.getRequest();
            HttpSession session = req.getSession();

            session.removeAttribute("username");
            session.invalidate();

            status.put("data", "true");
            return SUCCESS;
        }
        status.put("data", "false");
        return SUCCESS;

    }
}
