package com.example.BloodBank.controller;

import com.example.BloodBank.dto.BloodStockDto;
import com.example.BloodBank.dto.UserLoginDto;
import com.example.BloodBank.dto.UserSignUpDto;
import com.example.BloodBank.model.UserModel;
import com.example.BloodBank.repository.BloodBankRepository;
import com.example.BloodBank.service.SortAndFilterService;
import com.example.BloodBank.service.UserLoginService;
import com.example.BloodBank.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Controller
public class BloodBankController {
    @Autowired
    private UserService userService;
    @Autowired
    private SortAndFilterService sortAndFilterService;
    @Autowired
    private UserLoginService userLoginService;

    List<String> loggedInUsers=new ArrayList<>();

    //to open login page
    @RequestMapping("/")
    public String home() {
        return "login";
    }

    //to show successfully sign in message
    @RequestMapping("/signin")
//    @ResponseBody
    public String userSignIn(@ModelAttribute @Validated UserLoginDto userLoginDto, Model model, HttpServletRequest request) {   //yha login form fields ki value userLoginDto
        //class ke fields mai set ho jayengi
        //checkUserExit method UserLoginService class mai bnaya hai

        UserSignUpDto userExist = userLoginService.checkUserExist(userLoginDto);
        model.addAttribute("userName", userLoginDto.getUserName());
        if (userExist != null && userExist.getLoginAttempt() == 0) {
            HttpSession session = request.getSession();
            session.setAttribute("userName", userExist.getUserName());
            session.setAttribute("role", userExist.getRole());
            System.out.println(userExist.getUserName() + " login--------------");
            System.out.println("users loggedin list");
            //calling method to get the list of loggedin users
            loggedInUsers = userLoginService.getLoggedInUsers();
            for (String user: loggedInUsers){
                System.out.println("logged in "+user);
            }

            if (userExist.isFirstLogin() && !userExist.getCreatedBy().equalsIgnoreCase("self")
                    && !userExist.getCreatedBy().equalsIgnoreCase("auto")) {
                return "updatePassword";
            }
            else {
                switch (userExist.getRole().toLowerCase()) {
                    case ("admin"):
                        if (userExist.isFirstLogin())
                            userLoginService.updateLoginStatus(userLoginDto);
                        model.addAttribute("dto", userExist);
                        return "adminDashboard";

                    case ("end user"):
                        if (userExist.isFirstLogin())
                            userLoginService.updateLoginStatus(userLoginDto);
                        model.addAttribute("dto", userExist);
                        return "userDashboard";

                    case ("agent"):
                        model.addAttribute("dto", userExist);
                        return "agentDashboard";

                    default:
                        return "error";
                }
            }
        } else if (userExist != null && userExist.isLockStatus()) {
            return "block";
        } else if (userExist != null) {
            model.addAttribute("remainingAttempt", userExist.getLoginAttempt());
            return "remainingAttempts";
        } else
            return "error";
    }

    //to open signUp page
    @RequestMapping("/signup")
    public String userSignUp() {
        return "signUp";
    }

    //to show successfully signUp message
    @RequestMapping("/register")
    @ResponseBody
    public String register(@ModelAttribute @Validated UserSignUpDto userSignUpDto, HttpServletRequest request) {   //yha signUp form fields ki value userSignUpDto
        //class ke fields mai set ho jayengi
        //calling createSignUpModel method of service class
        String userName = (String) request.getSession().getAttribute("userName");
        String role = (String) request.getSession().getAttribute("role");
        System.out.println("role " + role);

        if (role != null) {
            if (role.equalsIgnoreCase("Admin")) {
                userSignUpDto.setPassword(String.valueOf(userSignUpDto.getDob()));
                userSignUpDto.setCreatedBy(userName);
                userSignUpDto.setRole("Agent");
            } else if (role.equalsIgnoreCase("Agent")) {
                userSignUpDto.setPassword(String.valueOf(userSignUpDto.getDob()));
                userSignUpDto.setCreatedBy(userName);
                userSignUpDto.setRole("End User");
            }
        } else {
            userSignUpDto.setRole("End User");
            userSignUpDto.setCreatedBy("Self");
        }
        if (userService.create(userSignUpDto))
            return "You have successfully signed up!!";
        else
            return "Username already exist please try with different Username";
    }

    @RequestMapping("/update")
    @ResponseBody
    public String updatePasswordFrom(@ModelAttribute UserLoginDto userLoginDto) {
        if (userLoginService.updatePassword(userLoginDto))
            return "Password successfully updated!!";
        else
            return "You Enter wrong password";
    }

    //to show all users list
    @RequestMapping("/allusers")
    public String viewAllUsers(Model model, HttpServletRequest request) {
        System.out.println("in allusers handler method");
        HttpSession session = request.getSession();
        String loggedInUserName = (String) session.getAttribute("userName");
        List<UserSignUpDto> allUsers = userLoginService.getAllUsers(loggedInUserName);
        model.addAttribute("users", allUsers);
        return "userslist";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String logoutUserName = (String) session.getAttribute("userName");
        System.out.println("logged out user --"+logoutUserName);
        userLoginService.removeLogoutUser(logoutUserName);
        session.invalidate();
        //redirect is a way to instruct the browser to make a new request to a different URL.
        return "redirect:/";
    }

    //to sort the list in which the agents are created by Admin or the agent
    @RequestMapping("/sortAndFilter")
    public String sortingUsers(@RequestParam String sortBy,
                               @RequestParam String filterBy,
                               @RequestParam String username,
//                               @RequestParam Date startDate,
//                               @RequestParam Date endDate,
                               HttpServletRequest request , Model model){
        System.out.println("in sort and filter handler");
        System.out.println("filterby "+filterBy);
        System.out.println("sortby "+sortBy);
        HttpSession session=request.getSession();
        String loggedInUserName = (String) session.getAttribute("userName");
        List<UserSignUpDto> allUsers = userLoginService.getAllUsers(loggedInUserName);
        //it is to sort the list
//        if (sortBy!=null) {
//            System.out.println("calling sort method...."+sortBy);
//            List<UserSignUpDto> sortedUserList = sortAndFilterService.sortUserList(sortBy, allUsers);
//            model.addAttribute("users", sortedUserList);
//        }
        //it is to filter the list
        if (filterBy!=null ) {
            System.out.println("in filter handler "+filterBy);
            switch (filterBy){
                case "byAgent":
                List<UserSignUpDto> filteredUserListByAgent = sortAndFilterService.filterUserList1(username, allUsers);
                model.addAttribute("users", filteredUserListByAgent);
                break;

                case "byUsername":
                List<UserSignUpDto> filteredUserListByUserName = sortAndFilterService.filterUserList2(username, allUsers);
                model.addAttribute("users", filteredUserListByUserName);
                break;

//                case "createdBetween":
//                    List<UserSignUpDto> filteredUserList = sortAndFilterService.filterUserList3(startDate, endDate, allUsers);
//                    model.addAttribute("users", filteredUserList);
                case "notLoggedIn":
                    List<UserSignUpDto> notLoggedInUsers = userLoginService.getNotLoggedInUsers();
                    model.addAttribute("users",notLoggedInUsers);


            }

        }
        return "userslist";

    }

    //to show blood stock
    @RequestMapping("/bloodstock")
    public String viewBloodStock(Model model) {
        List<BloodStockDto> bloodStock = userLoginService.getBloodStock();
        model.addAttribute("users",bloodStock);
        return "bloodStock";
    }


}
