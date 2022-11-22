/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.epic.strutslogin.bean;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author himal
 */
public class Temp {
    
    private String username;
    private String password;
    private String fname;
    private String lname;
    private String nic;
    private String address;
    private LocalDate dob;
    private String email;
    private Date accCreateInfo;
    private Date accUpdateInfo;
    private Date accLastLoginInfo;
    private Date accLastLogoutInfo;

    public Temp() {
    }

    public Temp(String username, String password, String fname, String lname, String nic, String address, LocalDate dob, String email, Date accCreateInfo, Date accUpdateInfo, Date accLastLoginInfo, Date accLastLogoutInfo) {
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.nic = nic;
        this.address = address;
        this.dob = dob;
        this.email = email;
        this.accCreateInfo = accCreateInfo;
        this.accUpdateInfo = accUpdateInfo;
        this.accLastLoginInfo = accLastLoginInfo;
        this.accLastLogoutInfo = accLastLogoutInfo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getAccCreateInfo() {
        return accCreateInfo;
    }

    public void setAccCreateInfo(Date accCreateInfo) {
        this.accCreateInfo = accCreateInfo;
    }

    public Date getAccUpdateInfo() {
        return accUpdateInfo;
    }

    public void setAccUpdateInfo(Date accUpdateInfo) {
        this.accUpdateInfo = accUpdateInfo;
    }

    public Date getAccLastLoginInfo() {
        return accLastLoginInfo;
    }

    public void setAccLastLoginInfo(Date accLastLoginInfo) {
        this.accLastLoginInfo = accLastLoginInfo;
    }

    public Date getAccLastLogoutInfo() {
        return accLastLogoutInfo;
    }

    public void setAccLastLogoutInfo(Date accLastLogoutInfo) {
        this.accLastLogoutInfo = accLastLogoutInfo;
    }
    
    
    
}
