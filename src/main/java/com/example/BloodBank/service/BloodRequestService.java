package com.example.BloodBank.service;

import com.example.BloodBank.dto.BloodRequestDto;
import com.example.BloodBank.dto.UserLoginDto;
import com.example.BloodBank.model.BloodRequestModel;
import com.example.BloodBank.model.UserModel;
import com.example.BloodBank.repository.BloodRequestRepo;
import com.example.BloodBank.repository.BloodBankRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BloodRequestService {
    @Autowired
    private BloodRequestRepo bloodRequestRepo;
    @Autowired
    private BloodBankRepository bloodBankRepository;

    public String makingRequest(BloodRequestDto bloodRequestDto, String username, String bloodGroup, long age,LocalDate dob,String createdBy) {
        System.out.println("In BloodRequestService class method makingRequest");
        if(bloodRequestDto.getType().equalsIgnoreCase("donate") ){
            boolean requestingUserExitingDetail = getRequestingUserExitingDetail(username);
            if(!requestingUserExitingDetail){
                return "user can only donate blood after three months";
            }
        }
        UserModel userModel = bloodBankRepository.findByUserName(username);
        BloodRequestModel bloodRequestModel = new BloodRequestModel();
        bloodRequestModel.setAge(age);
        bloodRequestModel.setDob(dob);
        bloodRequestModel.setBloodGroup(bloodGroup);
        bloodRequestModel.setCreatedBy(username);
        bloodRequestModel.setAgent(createdBy);
        bloodRequestModel.setCreatedOn(LocalDate.now());
        bloodRequestModel.setQuantity(bloodRequestDto.getQuantity());
        bloodRequestModel.setRemark(bloodRequestDto.getRemark());
        bloodRequestModel.setStatus("pending");
        bloodRequestModel.setRemark("-");
        bloodRequestModel.setType(bloodRequestDto.getType());
        bloodRequestModel.setUpdateAt(LocalDateTime.now());
        bloodRequestModel.setUpdatedBy("-");
        bloodRequestModel.setUserCoin(0.0f);
        bloodRequestModel.setAdminCoins(0.0f);
        bloodRequestModel.setAgentCoins(0.0f);
        bloodRequestModel.setUser(userModel);
        BloodRequestModel save = bloodRequestRepo.save(bloodRequestModel);
        return "Your request has been succesfully sent!!";

    }

    public boolean getRequestingUserExitingDetail(String userName) {
        List<BloodRequestModel> donate = bloodRequestRepo.findByCreatedByAndTypeAndStatus(userName, "donate","accepted");
        if (donate.isEmpty())
            return true;
        else {
            BloodRequestModel first = donate.stream()
                    .sorted(Comparator.comparing(BloodRequestModel::getUpdateAt, Comparator.reverseOrder()))
                    .findFirst().orElse(null);
            LocalDate updatedAt = first.getUpdateAt().toLocalDate();
            LocalDate localDate = LocalDate.now().minusDays(91);
            if (updatedAt.isBefore(localDate)) {
                return true;
            }
        }

        return false;
    }

//    public boolean handlingPendingRequests(String userName){
//        return getRequestingUserExitingDetail(userName);
//    }

}
