package com.example.BloodBank.repository;

import com.example.BloodBank.model.BloodRequestModel;
import com.example.BloodBank.model.BloodStockModel;
import com.example.BloodBank.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodRequestRepo extends JpaRepository<BloodRequestModel,Long>{
//    BloodStockModel findByUserName(String userName);
      BloodRequestModel findByCreatedBy(String userName);

      List<BloodRequestModel> findByCreatedByAndType(String userName, String donate);

      List<BloodRequestModel> findByType(String donate);

      List<BloodRequestModel> findByTypeAndStatus(String donate,String accepted);

      List<BloodRequestModel> findByTypeAndStatusAndAgent(String donate, String accepted, String agent);

      List<BloodRequestModel> findByCreatedByAndTypeAndStatus(String userName, String donate, String accept);
}
