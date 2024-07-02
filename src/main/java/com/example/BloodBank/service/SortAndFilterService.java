package com.example.BloodBank.service;

import com.example.BloodBank.dto.UserSignUpDto;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class SortAndFilterService {
//    public List<UserSignUpDto> sortUserList(String sortBy,List<UserSignUpDto> userList){
//        System.out.println("sort method in service...."+sortBy);
//        switch(sortBy){
//            case "name":
//                return userList.stream()
//                        .sorted(Comparator.comparing(user->user.getName().toLowerCase()))
//                        .collect(Collectors.toList());
//            case "createdBy":
//                return userList.stream()
//                        .sorted(Comparator.comparing(user->user.getCreatedBy().toLowerCase()))
//                        .collect(Collectors.toList());
//            case "bloodGroup":
//                return userList.stream()
//                        .sorted(Comparator.comparing(UserSignUpDto::getBloodGroup))
//                        .collect(Collectors.toList());
//            default:
//                return userList;
//        }
//    }
    public List<UserSignUpDto> filterUserList1(String agent, List<UserSignUpDto>userList) {
        System.out.println("inside filterUserList1 method-----");
        return userList.stream()
                .filter(user -> user.getCreatedBy().equals(agent))
                .collect(Collectors.toList());
    }

    public List<UserSignUpDto> filterUserList2(String username, List<UserSignUpDto>userList) {
        System.out.println("inside filterUserList2 method-----");
        return userList.stream()
                .filter(user -> user.getUserName().equals(username))
                .collect(Collectors.toList());
    }




//    public List<UserSignUpDto> filterUserList3(Date sDate,Date eDate, List<UserSignUpDto>userList) {
//        System.out.println("inside filterUserList3 method-----"+ LocalDate.now());
////        LocalDateTime startDate=sDate.toLocalDate().atStartOfDay();
////        LocalDateTime endDate=eDate.toLocalDate().atTime(23,59,59);
//        return userList.stream()
//                .filter(user ->user.getCreatedOn().isAfter(sDate.toLocalDate().minusDays(1))&& user.getCreatedOn().isBefore(eDate.toLocalDate().plusDays(1
//                )))
//                .collect(Collectors.toList());
//    }
}