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

    public boolean create(UserSignUpDto userSignUpDto, String userName, String role) {
        System.out.println("in service create method");
        UserModel userModel = bloodBankRepository.checkForUserExist(userSignUpDto.getUserName());
        if (userModel != null)
            return false;
        UserModel userSingUpModel = new UserModel();
        switch (role.toLowerCase()) {
            case "admin":
                userSingUpModel.setRole("Agent");
                userSingUpModel.setCreatedBy(userName);
                userSingUpModel.setPassword(String.valueOf(userSignUpDto.getDob()));
                break;
            case "agent":
                userSingUpModel.setRole("End User");
                userSingUpModel.setCreatedBy(userName);
                userSingUpModel.setPassword(String.valueOf(userSignUpDto.getDob()));
                break;
            default:
                userSingUpModel.setRole("End User");
                userSingUpModel.setCreatedBy("Self");
                userSingUpModel.setPassword(userSignUpDto.getPassword());
        }
        userSingUpModel.setUserName(userSignUpDto.getUserName());
        userSingUpModel.setName(userSignUpDto.getName());
        userSingUpModel.setAddress(userSignUpDto.getAddress());
        userSingUpModel.setDob(userSignUpDto.getDob());
        userSingUpModel.setBloodGroup(userSignUpDto.getBloodGroup());
        userSingUpModel.setCreatedOn(LocalDate.now());
        userSingUpModel.setCommission(userSignUpDto.getCommission());
        userSingUpModel.setLoginAttempt(0);
        userSingUpModel.setLockStatus(false);
        userSingUpModel.setFirstLogin(true);
        Object save = bloodBankRepository.save(userSingUpModel);   //repo ke pass inbuilt method hote hai
        return true;
    }
}
