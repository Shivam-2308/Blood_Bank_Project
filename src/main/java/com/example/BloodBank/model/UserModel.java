package com.example.BloodBank.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blood_bank_id;
    private String userName;
    private String name;
    private String role;
    private Date dob;
    private float commission;
    private String bloodGroup;
    private String address;
    private String password;
    private LocalDate createdOn;
    private String createdBy;
    private int loginAttempt;
    private boolean lockStatus;
    private boolean isFirstLogin;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    public float getCommission() {
        return commission;
    }

    public void setCommission(float commission) {
        this.commission = commission;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }


    public boolean isLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(boolean lockStatus) {
        this.lockStatus = lockStatus;
    }

    public int getLoginAttempt() {
        return loginAttempt;
    }

    public void setLoginAttempt(int loginAttempt) {
        this.loginAttempt = loginAttempt;
    }

    public boolean isFirstLogin() {
        return isFirstLogin;
    }

    public void setFirstLogin(boolean isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }
}



