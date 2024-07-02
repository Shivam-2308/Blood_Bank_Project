package com.example.BloodBank.service;

import com.example.BloodBank.model.BloodStockModel;
import com.example.BloodBank.repository.BloodBankRepository;
import com.example.BloodBank.repository.BloodStockRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BloodStockService {
    @Autowired
    private BloodStockRepo bloodStockRepo;
    public void setBloodStock(){
        List<BloodStockModel> bloodStockModels = List.of(new BloodStockModel("A+", 2, 20, LocalDateTime.now()),
                new BloodStockModel("A+", 4, 10,LocalDateTime.now()),
                new BloodStockModel("A-", 3, 40,LocalDateTime.now()),
                new BloodStockModel("B+", 5, 30,LocalDateTime.now()),
                new BloodStockModel("B-", 6, 20,LocalDateTime.now()),
                new BloodStockModel("AB+", 3, 40,LocalDateTime.now()),
                new BloodStockModel("AB-", 2, 50,LocalDateTime.now()),
                new BloodStockModel("O+", 4, 20,LocalDateTime.now()),
                new BloodStockModel("O-", 3, 30,LocalDateTime.now()));
        bloodStockModels.stream()
                .forEach(bloodStockModel -> {
                    BloodStockModel byBloodGroup = bloodStockRepo.findByBloodGroup(bloodStockModel.getBloodGroup());
                    if(byBloodGroup==null)
                        bloodStockRepo.save(bloodStockModel);
                });


    }
}
