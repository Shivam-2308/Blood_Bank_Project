package com.example.BloodBank.repository;

import com.example.BloodBank.model.BloodStockModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodStockRepo extends JpaRepository <BloodStockModel,Long>{
    public BloodStockModel findByBloodGroup(String bloodGroup);
}
