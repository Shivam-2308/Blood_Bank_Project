<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Single Message Page</title>
    <style>
        body, html {
            height: 100%;
            margin: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: #f0f0f0;
        }
        .message-container {
            width: 90%; /* This makes the div occupy more than half the page width */
            background-color: #ffffff;
            padding: 80px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            border-radius: 8px;
            text-align: center;
            border: 2px solid black;

        }
        .message {
            font-size: 35px;
            font-weight: bold;
            color: #333333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #000;
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .back-button {
                                    margin-top: 20px;
                                    width: 50px;
                                    height: 50px;
                                    border-radius: 50%;
                                    background-color: #007bff;
                                    color: white;
                                    border: none;
                                    font-size: 24px;
                                    display: flex;
                                    align-items: center;
                                    justify-content: center;
                                    cursor: pointer;
                                }
                                .back-button:hover {
                                    background-color: #0056b3;
                                }
                                .back-button:focus {
                                    outline: none;
                                }
                                .arrow {
                                    display: inline-block;
                                    transform: rotate(0deg);
        }
    </style>
</head>
<body>
    <div class="message-container">
        <p class="message">All Users list!!</p>
        <div class="input-group">
        <form action="/sortAndFilter" method="post">
                                <select id="sortBy" name="sortBy">
                                   <option value="select">Sort By</option>
                                    <option value="name">by Name</option>
                                    <option value="createdBy">by CreatedBy</option>
                                    <option value="bloodGroup">by BloodGroup</option>
                                </select>
                                <button type="submit" class="action-btn">Sort</button>
                </div>
               <!-- Filtering options -->
                                       <select id="filterBy" name="filterBy">
                                           <option value="select" >FilterBy</option>
                                           <option value="notLoggedIn">Not Logged In Users</option>
                                           <option value="byAgent">By Agent</option>
                                           <option value="byUsername">By Username</option>
                                           <option value="createdBetween">By Created Between</option>
                                       </select>
                                       <input type="text" id="username" name="username" placeholder="Enter Username" style="display: none;">
                                       <input type="date" id="startDate" name="startDate" placeholder="Start Date" style="display: none;">
                                       <input type="date" id="endDate" name="endDate" placeholder="End Date" style="display: none;">
                                       <button type="submit">Filter</button>
        <button class="back-button" onclick="goBack()">
            <span class="arrow">&larr;</span>
        </button>
        </form>
        <table>
            <thead>
                <tr>
                    <th>Serial No.</th>
                    <th>User Name</th>
                    <th>Name</th>
                    <th>Role</th>
                    <th>DOB</th>
                    <th>Blood Group</th>
                    <th>Created By</th>
                    <th>Created On</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td>${user.userName}</td>
                        <td>${user.name}</td>
                        <td>${user.role}</td>
                        <td>${user.dob}</td>
                        <td>${user.bloodGroup}</td>
                        <td>${user.createdBy}</td>
                        <td>${user.createdOn}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>

    <!-- Font Awesome JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/js/all.min.js"></script>
         <script>
                function goBack() {
                             window.history.back();
                }
                document.getElementById("filterBy").addEventListener("change", function() {
                    var selectedOption = this.value;
                    var usernameInput = document.getElementById("username");
                    var startDateInput = document.getElementById("startDate");
                    var endDateInput = document.getElementById("endDate");

                    if (selectedOption === "byUsername" || selectedOption==="byAgent") {
                        usernameInput.style.display = "inline-block";
                        startDateInput.style.display = "none";
                        endDateInput.style.display = "none";
                    } else if (selectedOption === "createdBetween") {
                        startDateInput.style.display = "inline-block";
                        endDateInput.style.display = "inline-block";
                        usernameInput.style.display = "none";
                    } else {
                        startDateInput.style.display = "none";
                        endDateInput.style.display = "none";
                        usernameInput.style.display = "none";
                    }
                });
            </script>
</body>
</html>
