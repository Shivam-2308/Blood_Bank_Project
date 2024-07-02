package com.example.BloodBank.service;

import com.example.BloodBank.model.UserModel;
import com.example.BloodBank.repository.BloodBankRepository;
import com.example.BloodBank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

@Service    //stereotype annotation
public class AdminService {
    @Autowired
    private BloodBankRepository bloodBankRepository;

    public void setAdmin() {
        UserModel userModel = new UserModel();
        userModel.setUserName("Admin11");
        userModel.setName("Shivam");
        userModel.setRole("Admin");
        userModel.setDob(Date.valueOf("2000-08-23"));
        userModel.setBloodGroup("A+");
        userModel.setAddress("Bijnor");
        userModel.setPassword("111");
        userModel.setFirstLogin(true);
        userModel.setLoginAttempt(0);
        userModel.setLockStatus(false);
        userModel.setCommission(0);
        userModel.setCreatedOn(LocalDate.now());
        userModel.setCreatedBy("Auto");
        if (bloodBankRepository.count() == 0)
            bloodBankRepository.save(userModel);
    }


}
