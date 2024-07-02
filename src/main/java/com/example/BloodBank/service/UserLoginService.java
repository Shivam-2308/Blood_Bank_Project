package com.example.BloodBank.service;

import com.example.BloodBank.dto.BloodStockDto;
import com.example.BloodBank.dto.UserLoginDto;
import com.example.BloodBank.dto.UserSignUpDto;
import com.example.BloodBank.model.BloodStockModel;
import com.example.BloodBank.model.UserModel;
import com.example.BloodBank.repository.BloodBankRepository;
import com.example.BloodBank.repository.BloodStockRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserLoginService {
    @Autowired
    private BloodBankRepository bloodBankRepository;

    @Autowired
    private ModelMapper
            modelMapper;
    @Autowired
    private BloodStockRepo bloodStockRepo;

     static List<String> loggedInUsers = new ArrayList<>();
    public UserSignUpDto checkUserExist(UserLoginDto userLoginDto) {
        UserModel userModel = bloodBankRepository.findByUserName(userLoginDto.getUserName());
        if (userModel != null && !userModel.isLockStatus() && userLoginDto.getPassword().equals(userModel.getPassword())) {
            userModel.setLoginAttempt(0);
            //method to adding users which have logged in.....
            loggedInUsers.add(userModel.getUserName());
            UserModel save = bloodBankRepository.save(userModel);
            return convertToDto(userModel);
        } else if (userModel != null) {
            incrementLoginAttempt(userModel);
            return convertToDto(userModel);
        } else {
            return null;
        }

    }
    public List<String> getLoggedInUsers() {
        return loggedInUsers;
    }

    //Method to count invalid attempts made
    private void incrementLoginAttempt(UserModel userModel) {
        int attemptCount = userModel.getLoginAttempt();
        userModel.setLoginAttempt(attemptCount + 1);
        if (attemptCount >= 2) {
            userModel.setLockStatus(true);
        }
        bloodBankRepository.save(userModel);

    }

    //it is to convert UserModel into UserSignUpDto
    public UserSignUpDto convertToDto(UserModel userModel) {
        return modelMapper.map(userModel, UserSignUpDto.class);
    }

    //it is to convert bloodStockModel into BloodStockDto
    public BloodStockDto convertToBloodStockDto(BloodStockModel bloodStockModel) {
        return modelMapper.map(bloodStockModel, BloodStockDto.class);
    }

    public boolean updatePassword(UserLoginDto userLoginDto) {
        UserModel userModel = bloodBankRepository.findByUserName(userLoginDto.getUserName());
        if (userLoginDto.getPassword().equals(userModel.getPassword()) && userLoginDto.getUpdatePassword().equals(userLoginDto.getConfirmPassword())) {
            userModel.setPassword(userLoginDto.getUpdatePassword());
            userModel.setFirstLogin(false);
            bloodBankRepository.save(userModel);
            return true;
        } else {
            return false;
        }

    }
    public void updateLoginStatus(UserLoginDto userLoginDto){
        UserModel userModel = bloodBankRepository.findByUserName(userLoginDto.getUserName());
        userModel.setFirstLogin(false);
        bloodBankRepository.save(userModel);

    }



    //gettting the list of all users from the admin dashboard
    public List<UserSignUpDto> getAllUsers(String userName) {
        List<UserSignUpDto> userList = new ArrayList<>();
        List<UserModel> allusers = bloodBankRepository.findAll();  //allusers mai repo se objects aayenge
        if (userName.equalsIgnoreCase("Admin11")) {
            for (UserModel user : allusers) {
                if (!user.getUserName().equals("Admin11"))
                    userList.add(convertToDto(user));
            }
        } else {
            for (UserModel user : allusers) {
                if (user.getCreatedBy().equals(userName))
                    userList.add(convertToDto(user));
            }
        }
        return userList;
    }

    //implementation of method to get loggedUsers only
    public void removeLogoutUser(String username){
        loggedInUsers.remove(username);
        for(String user: loggedInUsers)
            System.out.println("in logout "+user);

    }
    public List<UserSignUpDto> getNotLoggedInUsers(){
        List<UserSignUpDto> notLoggedUsers =new ArrayList<>();
        List<UserModel> allUsers = bloodBankRepository.findAll();
        for (UserModel users: allUsers){
            if(!loggedInUsers.contains(users.getUserName())){
                notLoggedUsers.add(convertToDto(users));
            }

        }
        return notLoggedUsers;
    }

    public List<BloodStockDto> getBloodStock(){
        List<BloodStockDto>bloodStockDtos = new ArrayList<>();
        List<BloodStockModel>list = bloodStockRepo.findAll();
        for (BloodStockModel bloodStockModel: list){
            bloodStockDtos.add(convertToBloodStockDto(bloodStockModel));
        }
        return bloodStockDtos;
    }






}
