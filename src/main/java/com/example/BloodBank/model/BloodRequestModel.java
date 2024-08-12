package com.example.BloodBank.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table
public class BloodRequestModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
//    @Column(name = "admin_coins", nullable = false)
    private float userCoin;
//    @Column(name = "admin_coins", nullable = false)
    private float adminCoins;
//    @Column(name = "agent_coins", nullable = false)
    private float agentCoins;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

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

    public String getBloodGroup() {
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

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDate getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDate createdOn) {
        this.createdOn = createdOn;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public float getAgentCoins() {
        return agentCoins;
    }

    public void setAgentCoins(float agentCoins) {
        this.agentCoins = agentCoins;
    }

    public float getAdminCoins() {
        return adminCoins;
    }

    public void setAdminCoins(float adminCoins) {
        this.adminCoins = adminCoins;
    }
}
