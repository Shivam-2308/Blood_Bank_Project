package com.example.BloodBank.service;

import com.example.BloodBank.dto.BloodRequestDto;
import com.example.BloodBank.dto.BloodStockDto;
import com.example.BloodBank.dto.UserLoginDto;
import com.example.BloodBank.dto.UserSignUpDto;
import com.example.BloodBank.model.BloodRequestModel;
import com.example.BloodBank.model.BloodStockModel;
import com.example.BloodBank.model.UserModel;
import com.example.BloodBank.repository.BloodBankRepository;
import com.example.BloodBank.repository.BloodRequestRepo;
import com.example.BloodBank.repository.BloodStockRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.Id;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserLoginService {
    @Autowired
    private BloodBankRepository bloodBankRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BloodRequestService bloodRequestService;
    @Autowired
    private BloodStockRepo bloodStockRepo;
    @Autowired
    private BloodRequestRepo bloodRequestRepo;
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

    public BloodRequestDto convertToBloodRequestDto(BloodRequestModel bloodRequestModel) {
        return modelMapper.map(bloodRequestModel, BloodRequestDto.class);
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

    public void updateLoginStatus(UserLoginDto userLoginDto) {
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
    public void removeLogoutUser(String username) {
        loggedInUsers.remove(username);
        for (String user : loggedInUsers)
            System.out.println("in logout " + user);
    }

    public List<UserSignUpDto> getNotLoggedInUsers() {
        List<UserSignUpDto> notLoggedUsers = new ArrayList<>();
        List<UserModel> allUsers = bloodBankRepository.findAll();
        for (UserModel users : allUsers) {
            if (!loggedInUsers.contains(users.getUserName())) {
                notLoggedUsers.add(convertToDto(users));
            }
        }
        return notLoggedUsers;
    }

    public List<BloodStockDto> getBloodStock() {
        List<BloodStockDto> bloodStockDtos = new ArrayList<>();
        List<BloodStockModel> list = bloodStockRepo.findAll();
        for (BloodStockModel bloodStockModel : list) {
            bloodStockDtos.add(convertToBloodStockDto(bloodStockModel));
        }
        return bloodStockDtos;
    }

    public List<BloodRequestDto> getBloodRequestList(String role) {
        List<BloodRequestDto> bloodRequestDtos = new ArrayList<>();
        List<BloodRequestModel> list = bloodRequestRepo.findAll();
        if (role.equalsIgnoreCase("agent")) {
            for (BloodRequestModel bloodRequestModel : list) {
                if ((!bloodRequestModel.getAgent().equalsIgnoreCase("admin")) && (!bloodRequestModel.getAgent().equalsIgnoreCase("self")))
                    bloodRequestDtos.add(convertToBloodRequestDto(bloodRequestModel));
            }
        } else {
            for (BloodRequestModel bloodRequestModel : list)
                bloodRequestDtos.add(convertToBloodRequestDto(bloodRequestModel));
        }
        return bloodRequestDtos;
    }

    //Getting the user list by role and type
    public List<BloodRequestDto> getBloodRequestListByTypeAndStatus(String role, String userName) {
        List<BloodRequestDto> bloodRequestDtos = new ArrayList<>();
        List<BloodRequestModel> list;
        if (role.equalsIgnoreCase("admin"))
            list = bloodRequestRepo.findByTypeAndStatus("donate", "accepted");
        else
            list = bloodRequestRepo.findByTypeAndStatusAndAgent("donate", "accepted", userName);
        for (BloodRequestModel bloodRequestModel : list) {
            bloodRequestDtos.add(convertToBloodRequestDto(bloodRequestModel));
        }
        return bloodRequestDtos;
    }

    //Here the list is returning to through the end userdashboard
    public List<BloodRequestDto> getRequestList(String userName) {
        List<BloodRequestDto> bloodRequestDtosOfUser = new ArrayList<>();
        List<BloodRequestModel> list = bloodRequestRepo.findAll();
        for (BloodRequestModel bloodRequestModel : list)
            if (bloodRequestModel.getCreatedBy().equalsIgnoreCase(userName))
                bloodRequestDtosOfUser.add(convertToBloodRequestDto(bloodRequestModel));
        return bloodRequestDtosOfUser;
    }

    public List<BloodRequestDto> getFilteredList1(List<BloodRequestDto> requestList, String status) {
        System.out.println("inside filterUserList1 method-----");
        return requestList.stream()
                .filter(request -> request.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    public List<BloodRequestDto> getFilteredList2(List<BloodRequestDto> requestList, String agent) {
        System.out.println("inside filterUserList2 method-----");
        return requestList.stream()
                .filter(request -> request.getAgent().equalsIgnoreCase(agent))
                .collect(Collectors.toList());
    }

    public List<BloodRequestDto> getFilteredList3(List<BloodRequestDto> requestList, String username) {
        System.out.println("inside filterUserList3 method-----");
        return requestList.stream()
                .filter(request -> request.getCreatedBy().equalsIgnoreCase(username))
                .collect(Collectors.toList());
    }

    public List<BloodRequestDto> getFilteredList4(List<BloodRequestDto> requestList, Date sDate, Date eDate) {
        System.out.println("inside filterUserList4 method-----");
        return requestList.stream()
                .filter(request -> request.getCreatedOn().isAfter(sDate.toLocalDate().plusDays(1)) &&
                        request.getCreatedOn().isBefore(eDate.toLocalDate().minusDays(1)))
                .collect(Collectors.toList());
    }

    //updating the blood request status to accept
    public String updateStatusForAccept(Long userId, String status) {
        BloodRequestModel bloodRequestModel = bloodRequestRepo.findById(userId).orElse(null);
        if (null == bloodRequestModel) {
            throw new NullPointerException("user of this id do not exist");
        }
        boolean check = bloodRequestService.getRequestingUserExitingDetail(bloodRequestModel.getCreatedBy());
        if (!check) {
            return "This request not accepted because this user donate blood within last 3 months";
        }
        bloodRequestModel.setStatus(status);
        bloodRequestModel.setRemark("-");
        bloodRequestModel.setUpdateAt(LocalDateTime.now());
        bloodRequestModel.setUpdatedBy("Admin");
        bloodRequestRepo.save(bloodRequestModel);
        return "Accepted";
    }

    //updating the blood request status to reject
    public void updateStatusForReject(Long userId, String status) {
        BloodRequestModel bloodRequestModel = bloodRequestRepo.findById(userId).orElse(null);
        if (null == bloodRequestModel) {
            throw new NullPointerException("user of this id do not exist");
        }
        bloodRequestModel.setStatus(status);
        bloodRequestModel.setUpdateAt(LocalDateTime.now());
        bloodRequestModel.setUpdatedBy("Admin");
        bloodRequestRepo.save(bloodRequestModel);
    }

    //Handling the remark
    public void handlingRemark(String remark, Long userId) {
        BloodRequestModel bloodRequestModel = bloodRequestRepo.findById(userId).orElse(null);
        if (null == bloodRequestModel) {
            throw new NullPointerException("user of this id do not exists");
        }
        bloodRequestModel.setRemark(remark);
        bloodRequestRepo.save(bloodRequestModel);
    }

    //Filetering the blood RequestList on the end user Dashboard
    public List<BloodRequestDto> getFilteredListByStatus1(List<BloodRequestDto> requestList, String status) {
        System.out.println("in getFilteredList method-----" + status);
        return requestList.stream()
                .filter(users -> users.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    public List<BloodRequestDto> getFilteredListByStatus2(List<BloodRequestDto> requestList, String status) {
        System.out.println("in getFilteredList method-----" + status);
        return requestList.stream()
                .filter(users -> users.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    public List<BloodRequestDto> getFilteredListByStatus3(List<BloodRequestDto> requestList, String status) {
        System.out.println("in getFilteredListByStatus method-----" + status);
        return requestList.stream()
                .filter(users -> users.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }

    public List<BloodRequestDto> getFilteredListByDate(List<BloodRequestDto> requestList, LocalDate sDate, LocalDate eDate) {
        System.out.println("in getfilteredListByDate method----------");
        return requestList.stream()
                .filter(users -> users.getCreatedOn().isAfter(sDate.minusDays(1)) && users.getCreatedOn().isBefore(eDate.plusDays(1)))
                .collect(Collectors.toList());
    }
}
