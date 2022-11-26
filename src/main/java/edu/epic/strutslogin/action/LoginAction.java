/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.epic.strutslogin.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import edu.epic.strutslogin.bean.User;

import edu.epic.strutslogin.util.Crud;
import edu.epic.strutslogin.util.DateFactory;


import java.util.Calendar;

import java.util.HashMap;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


import org.apache.struts2.ServletActionContext;

;

/**
 *
 * @author himal
 */
public class LoginAction extends ActionSupport {

    private Map<String, String> status = new HashMap<String, String>();

    private String type;
    private List<User> allUser;
    private String userName;

    public Map<String, String> getStatus() {
        return status;
    }

    public void setStatus(Map<String, String> status) {
        this.status = status;
    }

    public String isRegisterdUser() {

        try {
            type = ServletActionContext.getRequest().getParameter("type");

            allUser = getAllUser();

            if (type.equalsIgnoreCase("username")) {

                if (!allUser.isEmpty()) {
                    for (User temp : allUser) {

                        if (temp.getUsername().equals(ServletActionContext.getRequest().getParameter("uname"))) {
                            status.put("data", "true");

                            return SUCCESS;
                        } else {
                            status.put("data", "false");
                            return SUCCESS;
                        }
                    }
                }

            } else if (type.equalsIgnoreCase("nic")) {

                if (!allUser.isEmpty()) {
                    for (User temp : allUser) {
                        if (temp.getNic().equalsIgnoreCase(ServletActionContext.getRequest().getParameter("nic"))) {
                            status.put("data", "true");

                            return SUCCESS;
                        } else {
                            status.put("data", "false");
                            return SUCCESS;
                        }
                    }
                }

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

                if (!allUser.isEmpty()) {
                    for (User temp : allUser) {
                        if (temp.getUsername().equals(ServletActionContext.getRequest().getParameter("uname"))) {
                            status.put("data", "false");

                            return SUCCESS;
                        } else {
                            status.put("data", "true");
                            return SUCCESS;
                        }
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            status.put("data", "false");
            return SUCCESS;
        }
        status.put("data", "false");
        return SUCCESS;

    }

    public String validLoginRequest() {

        try {
            userName = ServletActionContext.getRequest().getParameter("uname");

            allUser = getAllUser();

            if (!allUser.isEmpty()) {
                for (User temp : allUser) {

                    if (temp.getUsername().equals(userName) && temp.getPassword().equals(ServletActionContext.getRequest().getParameter("pwd"))) {

                        if (updateLoginInfo(userName)) {
                            ActionContext.getContext().getSession().put("username", userName);
                            ActionContext.getContext().getSession().put("user", temp);

                            status.put("data", "true");
                            return SUCCESS;
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            status.put("data", "false");
            return SUCCESS;

        }

        status.put("data", "false");
        return SUCCESS;
    }

    public String registerUser() {

        try {
            HttpServletRequest req = ServletActionContext.getRequest();

            User user = Crud.save(new User(req.getParameter("uname"), req.getParameter("pwd"), req.getParameter("fname"), req.getParameter("lname"), req.getParameter("nic"), req.getParameter("city"), DateFactory.getFormatterDate("yyyy-MM-dd", req.getParameter("dob")), req.getParameter("email"), Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), Calendar.getInstance().getTime()));

            if (user != null) {
                HttpSession sess = req.getSession(true);
                sess.putValue("username", user.getUsername());
                sess.putValue("user", user);
                status.put("data", "true");
                return SUCCESS;
            } else {

                status.put("data", "false");
                return SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            status.put("data", "false");
            return SUCCESS;

        }

    }

    private List<User> getAllUser() {

        try {
         
            return Crud.select();

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

    private boolean updateLoginInfo(String userName) {

        try {
            boolean update = Crud.updateOrDelete("UPDATE User SET acc_last_login=? WHERE username=?", Calendar.getInstance().getTime(), userName);

            return update ? true : false;
        } catch (Exception e) {
            e.printStackTrace();

        }

        return false;
    }

}
