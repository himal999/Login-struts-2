/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.epic.strutslogin.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import edu.epic.strutslogin.bean.User;
import edu.epic.strutslogin.db.DbConnection;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

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

    public String isRegisterdUser() throws ClassNotFoundException, SQLException {

        String type = ServletActionContext.getRequest().getParameter("type");

        List<User> allUser = getAllUser();

        if (type.equalsIgnoreCase("username")) {
            String userName = ServletActionContext.getRequest().getParameter("uname");
            if (!allUser.isEmpty()) {
                for (User temp : allUser) {
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

    public String validLoginRequest() throws ClassNotFoundException, SQLException {

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

        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());

        User user = new User(req.getParameter("uname"), req.getParameter("pwd"), req.getParameter("fname"), req.getParameter("lname"), req.getParameter("nic"), req.getParameter("city"), tempDate, req.getParameter("email"), time, time, time, null);

        String dob = formatter.format(user.getDob());
        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("INSERT INTO `user_detail` VALUES (?,?,?,?,?,?,?,?,?,?,?,?)");
        pst.setString(1, user.getUsername());
        pst.setObject(2, user.getPassword());
        pst.setString(3, user.getFname());
        pst.setString(4, user.getLname());
        pst.setString(5, user.getNic());

        pst.setString(6, user.getAddress());

        pst.setDate(7, Date.valueOf(dob));

        pst.setString(8, user.getEmail());

        pst.setObject(9, user.getAccCreateInfo());
        pst.setObject(10, user.getAccUpdateInfo());
        pst.setObject(11, user.getAccLastLoginInfo());
        pst.setObject(12, user.getAccLastLogoutInfo());

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

    


    /*3rd methods zone*/
    private List<User> getAllUser() throws ClassNotFoundException, SQLException {

        List<User> users = new ArrayList();

        try {
            Connection connection = DbConnection.getInstance().getConnection();

            PreparedStatement pst = connection.prepareStatement("SELECT * FROM `user_detail`");

            ResultSet rst = pst.executeQuery();

            while (rst.next()) {
                Blob by = rst.getBlob("password");
                byte[] bytes = by.getBytes(1, (int) by.length());

                users.add(new User(rst.getString("username"), new String(bytes), rst.getString("fname"), rst.getString("lname"), rst.getString("nic"), rst.getString("address"), rst.getDate("dob"), rst.getString("email")));
            }

        } catch (ClassNotFoundException | SQLException e) {

            System.out.println(e.getMessage());
        }

        return users;

    }

    private boolean updateLoginInfo(String userName) throws SQLException, ClassNotFoundException {

        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());

        Connection connection = DbConnection.getInstance().getConnection();
        PreparedStatement pst = connection.prepareStatement("UPDATE `user_detail` SET acc_last_login=? WHERE username=?");
        pst.setObject(1, time);
        pst.setObject(2, userName);

        if (pst.executeUpdate() > 0) {
            return true;
        }
        return false;
    }

}
