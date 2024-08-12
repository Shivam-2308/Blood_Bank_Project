package com.example.BloodBank.dto;

import javax.validation.constraints.NotBlank;
import java.security.PrivateKey;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BloodRequestDto {
    private long age;
    private LocalDate dob;

    private String bloodGroup;
    private String createdBy;
    private String agent;
    private LocalDate createdOn;
    private int quantity;
    private String remark;
    private String status;
    private String type;
    private LocalDateTime updateAt;
    private String updatedBy;
    private Long id;
    private float userCoin;
    private float adminCoins;
    private float agentCoins;

    public long getAge() {
        return age;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public String  getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getUserCoin() {
        return userCoin;
    }

    public void setUserCoin(float userCoin) {
        this.userCoin = userCoin;
    }

    public float getAdminCoins() {
        return adminCoins;
    }

    public void setAdminCoins(float adminCoins) {
        this.adminCoins = adminCoins;
    }

    public float getAgentCoins() {
        return agentCoins;
    }

    public void setAgentCoins(float agentCoins) {
        this.agentCoins = agentCoins;
    }
}
