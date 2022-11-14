/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.epic.strutslogin.action;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import edu.epic.strutslogin.db.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

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

    public String logOutRequest() throws ClassNotFoundException, SQLException {

        String userName = ActionContext.getContext().getSession().get("username").toString();


        
        String time = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(Calendar.getInstance().getTime());

        Connection connection = DbConnection.getInstance().getConnection();

        PreparedStatement pst = connection.prepareStatement("UPDATE `user_detail` SET acc_last_logout=? WHERE username=?");
        pst.setObject(1, time);
        pst.setObject(2, userName);

        if (pst.executeUpdate() > 0) {

            ActionContext.getContext().getSession().remove("username");
            ActionContext.getContext().getSession().clear();

            status.put("data", "true");
            return SUCCESS;
        }
        status.put("data", "false");
        return SUCCESS;

    }
}
