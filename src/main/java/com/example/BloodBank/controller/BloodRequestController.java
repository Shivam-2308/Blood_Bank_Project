package com.example.BloodBank.controller;

import com.example.BloodBank.dto.BloodRequestDto;
import com.example.BloodBank.dto.UserSignUpDto;
import com.example.BloodBank.service.BloodRequestService;
import com.example.BloodBank.service.BloodStockService;
import com.example.BloodBank.service.UserLoginService;
import org.apache.tomcat.jni.Local;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class BloodRequestController {
    @Autowired
    private BloodRequestService bloodRequestService;
    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private BloodStockService bloodStockService;

    @RequestMapping("/bloodrequest")
    public String bloodRequest() {
        return "bloodRequestForm";
    }

    @RequestMapping("/requestsubmit")
    @ResponseBody
    public String setTypeAndQuantity(@ModelAttribute @Validated BloodRequestDto bloodRequestDto, @RequestParam(value = "bloodGroup", required = false) String bloodGroup1, HttpServletRequest request) {
        String userName = (String) request.getSession().getAttribute("userName");
        String bloodGroup2 = (String) request.getSession().getAttribute("bloodGroup");
        Date dob = (Date) request.getSession().getAttribute("dob");
        String createdBy = (String) request.getSession().getAttribute("createdBy");
        String bloodGroup = "";
        //if the user send the request for receiving the blood,then the blood that the user wants should be fill in the field blood group in table.
        if ("receive".equalsIgnoreCase(bloodRequestDto.getType())) {
            if (bloodGroup1 != null && !bloodGroup1.isEmpty())
                bloodGroup = bloodGroup1;
        } else
            bloodGroup = bloodGroup2;
        long dobYear = dob.toLocalDate().getYear();
        LocalDate currentDate = LocalDate.now();
        long currentYear = currentDate.getYear();
        long age = currentYear - dobYear;
        return bloodRequestService.makingRequest(bloodRequestDto, userName, bloodGroup, age, dob.toLocalDate(), createdBy);
    }

    @RequestMapping("/requestlist")
    public String getResuestList(Model model, HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("role");
        List<BloodRequestDto> bloodRequestList = userLoginService.getBloodRequestList(role);
        model.addAttribute("bloodRequestList", bloodRequestList);
        model.addAttribute("role", role);
        return "bloodRequestCreated";
    }

    //List requested by the end user
    @RequestMapping("/requestlistByUser")
    public String userRequestingList(HttpServletRequest request, Model model) {
        String userName = (String) request.getSession().getAttribute("userName");
        List<BloodRequestDto> requestList = userLoginService.getRequestList(userName);
        System.out.println("------users------------");
        model.addAttribute("bloodRequestList", requestList);
        return "endUserRequest";
    }

    //to filter the blood request list
    @RequestMapping("/bloodRequestFilter")
    public String filterBloodRequest(@RequestParam(required = false) String filterBy,
                                     @RequestParam(required = false) String status,
                                     @RequestParam(required = false) Date date,
                                     @RequestParam(required = false) Date startDate,
                                     @RequestParam(required = false) Date endDate,
                                     @RequestParam(required = false) String agent,
                                     @RequestParam(required = false) String username,
                                     Model model, HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("role");
        List<BloodRequestDto> bloodRequestList = userLoginService.getBloodRequestList(role);
        request.getSession().getAttribute("");
        for (BloodRequestDto users : bloodRequestList) {
            System.out.println(users.getId());
        }
        if (filterBy != null) {
            switch (filterBy) {
                case "byStatus":
                    List<BloodRequestDto> filteredListByStatus = userLoginService.getFilteredList1(bloodRequestList, status);
                    model.addAttribute("bloodRequestList", filteredListByStatus);
                    break;
                case "byAgent":
                    List<BloodRequestDto> filteredListByAgent = userLoginService.getFilteredList2(bloodRequestList, agent);
                    for (int i = 0; i < filteredListByAgent.size(); i++) {
                        System.out.println(filteredListByAgent.get(i));
                        System.out.println(i);
                    }
                    model.addAttribute("bloodRequestList", filteredListByAgent);
                    break;
                case "byUsername":
                    List<BloodRequestDto> filteredListByUsername = userLoginService.getFilteredList3(bloodRequestList, username);
                    model.addAttribute("bloodRequestList", filteredListByUsername);
                    break;
                case "byDate":
                    List<BloodRequestDto> filteredListByDate = userLoginService.getFilteredList4(bloodRequestList, startDate, endDate);
                    System.out.println("----===List===--");
                    model.addAttribute("bloodRequestList", filteredListByDate);
                    break;
                default:
                    model.addAttribute("bloodRequestList", bloodRequestList);
            }
        }
        return "bloodRequestCreated";

    }

    //Handling the accept request by the admin
    @RequestMapping("/accept")
    public String handleAccept(@RequestParam(required = false) Long userId,
                               @RequestParam(required = false) String status,
                               @RequestParam(required = false) String userName,
                               Model model, HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("role");
        String reponseMsg = userLoginService.updateStatusForAccept(userId, status);
        List<BloodRequestDto> bloodRequestList = userLoginService.getBloodRequestList(role);
        model.addAttribute("bloodRequestList", bloodRequestList);
        model.addAttribute("role", role);
        model.addAttribute("message", reponseMsg);
        return "bloodRequestCreated";
    }

    //Handling the reject request by the admin
    @RequestMapping("/reject")
    public String handleReject(@RequestParam(required = false) String status,
                               @RequestParam(required = false) Long userId,
                               @RequestParam(required = false) String userName,
                               HttpServletRequest request,
                               Model model) {
        System.out.println("-------userId---" + userId);
        String role = (String) request.getSession().getAttribute("role");
        userLoginService.updateStatusForReject(userId, status);
        model.addAttribute("role", role);
        model.addAttribute("userId", userId);
        model.addAttribute("userName", userName);
        return "requestRemark";
    }

    //Handling the remark
    @RequestMapping("/handleRemark")
    public String remarkHandling(@RequestParam(required = false) String remark,
                                 @RequestParam(required = false) Long userId,
                                 Model model,
                                 HttpServletRequest request) {
        String role = (String) request.getSession().getAttribute("role");
        userLoginService.handlingRemark(remark, userId);
        List<BloodRequestDto> bloodRequestList = userLoginService.getBloodRequestList(role);
        model.addAttribute("bloodRequestList", bloodRequestList);
        model.addAttribute("role", role);
        return "bloodRequestCreated";

    }

    //Getting user list from the user dashboard by clicking the button show blood request

    @RequestMapping("/requestListByUserDashboard")
    public String userReuestingList(@RequestParam(required = false) String filterBy,
                                    @RequestParam(required = false) String status,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                    @RequestParam(required = false) String accepted,
                                    @RequestParam(required = false) String rejected,
                                    @RequestParam(required = false) String pending,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                    Model model,
                                    HttpServletRequest request) {
        String userName = (String) request.getSession().getAttribute("userName");
        List<BloodRequestDto> requestList = userLoginService.getRequestList(userName);
        if (filterBy != null) {
            switch (filterBy) {
                case "byStatus":
                    switch (status) {
                        case "accepted":
                            List<BloodRequestDto> filteredListByStatus1 = userLoginService.getFilteredListByStatus1(requestList, status);
                            model.addAttribute("bloodRequestList", filteredListByStatus1);
                            break;
                        case "rejected":
                            List<BloodRequestDto> filteredListByStatus2 = userLoginService.getFilteredListByStatus2(requestList, status);
                            model.addAttribute("bloodRequestList", filteredListByStatus2);
                            break;
                        case "pending":
                            List<BloodRequestDto> filteredListByStatus3 = userLoginService.getFilteredListByStatus3(requestList, status);
                            model.addAttribute("bloodRequestList", filteredListByStatus3);
                            break;
                        default:
                            model.addAttribute("bloodRequestList", requestList);
                    }
                    break;
                case "byDate":
                    List<BloodRequestDto> filteredListByDate = userLoginService.getFilteredListByDate(requestList, startDate, endDate);
                    model.addAttribute("bloodRequestList", filteredListByDate);
                    break;
                default:
                    model.addAttribute("bloodRequestList", requestList);
            }
        }
        return "endUserRequest";
    }

    //Handling coin values for admin ,agent and end user
    @RequestMapping("/coinreport")
    public String manageCoins(HttpServletRequest request, Model model) {
        String role = (String) request.getSession().getAttribute("role");
        String userName = (String) request.getSession().getAttribute("userName");
        List<BloodRequestDto> bloodRequestList1 = userLoginService.getBloodRequestListByTypeAndStatus(role, userName);
        List<BloodRequestDto> bloodRequestList = bloodStockService.handleCoins(bloodRequestList1, role);
        model.addAttribute("bloodRequestList", bloodRequestList);
        if (role.equalsIgnoreCase("admin"))
            return "coinReportAdmin";
        else
            return "coinReportAgent";
    }

    //Requesting the blood report
    @RequestMapping("/requestreport")
    public String requestingReport(Model model) {
        System.out.println("in requestReport handler method-----");
        HashMap<String, List<Long>> stringListHashMap = bloodStockService.gettingReport();
        model.addAttribute("bloodStockList", stringListHashMap);
        return "report";
    }

}
