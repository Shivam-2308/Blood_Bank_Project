package com.example.BloodBank.service;

import com.example.BloodBank.dto.BloodRequestDto;
import com.example.BloodBank.dto.BloodStockDto;
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
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

import static org.hibernate.criterion.Projections.count;

@Service
public class BloodStockService {
    @Autowired
    private BloodStockRepo bloodStockRepo;
    @Autowired
    private BloodRequestRepo bloodRequestRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    BloodBankRepository bloodBankRepository;

    public void setBloodStock() {
        List<BloodStockModel> bloodStockModels = List.of(new BloodStockModel("A+", 2, 20, LocalDateTime.now()),
                new BloodStockModel("A+", 4, 10, LocalDateTime.now()),
                new BloodStockModel("A-", 3, 40, LocalDateTime.now()),
                new BloodStockModel("B+", 5, 30, LocalDateTime.now()),
                new BloodStockModel("B-", 6, 20, LocalDateTime.now()),
                new BloodStockModel("AB+", 3, 40, LocalDateTime.now()),
                new BloodStockModel("AB-", 2, 50, LocalDateTime.now()),
                new BloodStockModel("O+", 4, 20, LocalDateTime.now()),
                new BloodStockModel("O-", 3, 30, LocalDateTime.now()));
        bloodStockModels.stream()
                .forEach(bloodStockModel -> {
                    BloodStockModel byBloodGroup = bloodStockRepo.findByBloodGroup(bloodStockModel.getBloodGroup());
                    if (byBloodGroup == null)
                        bloodStockRepo.save(bloodStockModel);
                });
    }

    public BloodRequestDto convertToDto(BloodRequestModel bloodRequestModel) {
        return modelMapper.map(bloodRequestModel, BloodRequestDto.class);
    }

    //Handling the coins
    public List<BloodRequestDto> handleCoins(List<BloodRequestDto> requestList, String role) {
        System.out.println("in method HandleCoins----");
        List<BloodRequestDto> resultantList = new ArrayList<>();
        for (BloodRequestDto users : requestList) {
            int totalCoins = 0, agentCoins = 0, adminCoins = 0;
            Integer bloodCoinValue = bloodStockRepo.getCoinValue(users.getBloodGroup()).orElse(null);
            int quantity = users.getQuantity();
            if (users.getAgent().equalsIgnoreCase("self")) {
                totalCoins = quantity * bloodCoinValue;
                adminCoins = totalCoins;
            } else {
                UserModel agentModel = bloodBankRepository.findByUserName(users.getAgent());
                System.out.println(agentModel.getUserName());
                float commission = agentModel.getCommission();
                totalCoins = quantity * bloodCoinValue;
                agentCoins = (int) ((totalCoins * commission) / 100);
                adminCoins = totalCoins - agentCoins;
            }
            BloodRequestModel bloodRequestModel = bloodRequestRepo.findById(users.getId()).orElse(null);
            bloodRequestModel.setUserCoin(totalCoins);
            bloodRequestModel.setAgentCoins(agentCoins);
            bloodRequestModel.setAdminCoins(adminCoins);
            bloodRequestRepo.save(bloodRequestModel);
            resultantList.add(convertToDto(bloodRequestModel));

        }
        return resultantList;
    }

    public HashMap<String, List<Long>> gettingReport() {
        System.out.println("getting report method in BloodStock service class");
        HashMap<String, List<Long>> map = new HashMap<>();
        List<BloodRequestModel> allUsers = bloodRequestRepo.findAll();
        List<BloodStockModel> allBloodsTypes = bloodStockRepo.findAll();
        for (BloodStockModel blood : allBloodsTypes) {
            String bloodGroup = blood.getBloodGroup();

            //Total count of blood requests of particular blood group....
            long totalRequestCount = allUsers.stream()
                    .filter(user -> user.getBloodGroup().equalsIgnoreCase(bloodGroup))
                    .count();

            //Total count of blood requests with status accepted particular blood group....
            long acceptedCount = allUsers.stream()
                    .filter(user -> user.getBloodGroup().equalsIgnoreCase(bloodGroup))
                    .filter(user -> user.getStatus().equalsIgnoreCase("accepted")).count();

            //Total count of blood requests with status rejected particular blood group....
            long rejectedCount = allUsers.stream()
                    .filter(user -> user.getBloodGroup().equalsIgnoreCase(bloodGroup))
                    .filter(user -> user.getStatus().equalsIgnoreCase("rejected")).count();

            //coin value
            Stream<BloodRequestModel> bloodRequestModelStream3 = allUsers.stream()
                    .filter(user -> user.getBloodGroup().equalsIgnoreCase(bloodGroup));
            long totalAdminCoinsValue = bloodRequestModelStream3.map(BloodRequestModel::getAdminCoins)
                    .reduce(0.0f, Float::sum).longValue();
            map.put(bloodGroup, List.of(totalRequestCount, acceptedCount, rejectedCount, totalAdminCoinsValue));
        }
        return map;
    }
}
