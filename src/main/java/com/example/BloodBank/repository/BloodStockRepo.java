package com.example.BloodBank.repository;

import com.example.BloodBank.model.BloodStockModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BloodStockRepo extends JpaRepository <BloodStockModel,Long>{
     @Query("SELECT coinValue from BloodStockModel where bloodGroup = :bloodGroup")
     Optional<Integer> getCoinValue(@Param("bloodGroup") String bloodGroup);

     BloodStockModel findByBloodGroup(String bloodGroup);
}
