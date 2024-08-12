package com.example.BloodBank.repository;

import com.example.BloodBank.model.UserModel;
import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


//yha pr hme attributes dene padte hai..phla wala object ka data type btata hai jo hm service class se recieve kr rhe hai
//and dusra argument i(jo database mai rkhni hai) ke type ke liye dete hai
//@Repository
public interface BloodBankRepository extends JpaRepository<UserModel, Long> {
    UserModel findByUserName(String userName);

    //this native query is for checking exiting user in db
    @Query(value = "SELECT * FROM user_model WHERE user_Name=:userName", nativeQuery = true)
    UserModel checkForUserExist(String userName);

//    UserModel findByAgent(String agent);
}

