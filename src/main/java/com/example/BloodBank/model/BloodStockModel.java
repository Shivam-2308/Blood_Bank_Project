package com.example.BloodBank.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table
public  class BloodStockModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bloodGroup;
    private int bloodUnit;
    private int coinValue;
    private LocalDateTime lastModifiedOn;

    public BloodStockModel() {
    }

    public BloodStockModel(String bloodGroup, int bloodUnit, int coinValue, LocalDateTime lastModifiedOn) {
        this.bloodGroup = bloodGroup;
        this.bloodUnit = bloodUnit;
        this.coinValue = coinValue;
        this.lastModifiedOn = lastModifiedOn;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public float getBloodUnit() {
        return bloodUnit;
    }

    public void setBloodUnit(int bloodUnit) {
        this.bloodUnit = bloodUnit;
    }

    public int getCoinValue() {
        return coinValue;
    }

    public void setCoinValue(int coinValue) {
        this.coinValue = coinValue;
    }

    public LocalDateTime getLastModifiedOn() {
        return lastModifiedOn;
    }

    public void setLastModifiedOn(LocalDateTime lastModifiedOn) {
        this.lastModifiedOn = lastModifiedOn;
    }
}