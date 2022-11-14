/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.epic.strutslogin.action;

import com.opensymphony.xwork2.ActionSupport;
import edu.epic.strutslogin.bean.User;
import edu.epic.strutslogin.db.DbConnection;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        String userName = ServletActionContext.getRequest().getParameter("uname");

        List<User> allUser = getAllUser();

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

    }

    public String validLoginRequest() throws ClassNotFoundException, SQLException {

        String userName = ServletActionContext.getRequest().getParameter("uname");
        String password = ServletActionContext.getRequest().getParameter("pwd");

        List<User> allUser = getAllUser();

        if (!allUser.isEmpty()) {
            for (User temp : allUser) {
                if (temp.getUsername().equals(userName) && temp.getPassword().equals(password)) {
                    status.put("data", "true");
                    return SUCCESS;
                }
            }
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

}
