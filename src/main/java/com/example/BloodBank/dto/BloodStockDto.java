package com.example.BloodBank.dto;

import java.sql.Date;
import java.time.LocalDateTime;

public class BloodStockDto {
    private String bloodGroup;
    private int bloodUnit;
    private int coinValue;
    private LocalDateTime lastModifiedOn;

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public int getBloodUnit() {
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
