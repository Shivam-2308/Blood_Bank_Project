package com.example.BloodBank.service;


import com.example.BloodBank.dto.UserSignUpDto;
import com.example.BloodBank.model.UserModel;
import com.example.BloodBank.repository.BloodBankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserService {
    @Autowired
    private BloodBankRepository bloodBankRepository;

    public boolean create(UserSignUpDto userSignUpDto) {
        System.out.println("in service create method");
        UserModel userModel = bloodBankRepository.checkForUserExist(userSignUpDto.getUserName());
        if (userModel != null)
            return false;
        UserModel userSingUpModel = new UserModel();
        userSingUpModel.setUserName(userSignUpDto.getUserName());
        userSingUpModel.setName(userSignUpDto.getName());
        userSingUpModel.setRole(userSignUpDto.getRole());
        userSingUpModel.setAddress(userSignUpDto.getAddress());
        userSingUpModel.setDob(userSignUpDto.getDob());
        userSingUpModel.setBloodGroup(userSignUpDto.getBloodGroup());
        userSingUpModel.setPassword(userSignUpDto.getPassword());
        userSingUpModel.setCreatedOn(LocalDate.now());
        userSingUpModel.setCommission(userSignUpDto.getCommission());
        userSingUpModel.setCreatedBy(userSignUpDto.getCreatedBy());
        userSingUpModel.setLoginAttempt(0);
        userSingUpModel.setLockStatus(false);
        userSingUpModel.setFirstLogin(true);
        Object save = bloodBankRepository.save(userSingUpModel);   //repo ke pass inbuilt method hote hai
        return true;
    }


}
