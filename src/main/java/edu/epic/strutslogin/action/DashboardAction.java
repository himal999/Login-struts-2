/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.epic.strutslogin.action;

import static com.opensymphony.xwork2.Action.SUCCESS;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import edu.epic.strutslogin.bean.User;

import edu.epic.strutslogin.util.Crud;
import edu.epic.strutslogin.util.DateFactory;


import java.util.Calendar;
import java.util.Date;
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
    private String userName;

    private Date dob;
    private User user;

    private HttpSession sess;
    private HttpServletRequest req;

    public Map<String, String> getStatus() {
        return status;
    }

    public void setStatus(Map<String, String> status) {
        this.status = status;
    }

    public String updateUser() {

        try {
            req = ServletActionContext.getRequest();

            this.userName = ActionContext.getContext().getSession().get("username").toString();

            dob = DateFactory.getFormatterDate("yyyy-MM-dd", req.getParameter("dob"));

            this.user = new User(req.getParameter("uname"), req.getParameter("pwd"), req.getParameter("fname"), req.getParameter("lname"), req.getParameter("nic"), req.getParameter("city"), this.dob, req.getParameter("email"), Calendar.getInstance().getTime(), Calendar.getInstance().getTime(), Calendar.getInstance().getTime());

            boolean update = Crud.updateOrDelete("UPDATE User SET username=?,fname=?,lname=?,nic=?,address=?,dob=?,email=?,acc_update_info=? WHERE username=?", user.getUsername(), user.getFname(), user.getLname(), user.getNic(), user.getAddress(), user.getDob(), user.getEmail(), user.getAccUpdateInfo(), userName);

            if (update) {
                sess = req.getSession();
                sess.putValue("username", user.getUsername());
                sess.putValue("user", user);

                status.put("data", "true");
                return SUCCESS;

            } else {

                status.put("data", "false");
                return SUCCESS;
            }
        } catch (Exception e) {

            System.out.println(e.getMessage());
            status.put("data", "false");
            return SUCCESS;
        }

    }

    public String dropUser() {

        try {
            req = ServletActionContext.getRequest();
            sess = req.getSession();

            boolean update = Crud.updateOrDelete("delete User where username = ?", (String) sess.getAttribute("username"));

            if (update) {

                sess.removeAttribute("username");
                sess.invalidate();

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

    public String logOutRequest() {

        try {
            req = ServletActionContext.getRequest();
            boolean update = Crud.updateOrDelete("UPDATE User SET acc_last_logout=? WHERE username=?", Calendar.getInstance().getTime(), ActionContext.getContext().getSession().get("username").toString());

            if (update) {

                sess = req.getSession();

                sess.removeAttribute("username");
                sess.invalidate();

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
}
