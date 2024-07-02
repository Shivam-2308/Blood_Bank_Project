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
        <p class="message">Blood Stocks</p>
        <button class="back-button" onclick="goBack()">
            <span class="arrow">&larr;</span>
        </button>
        <table>
            <thead>
                <tr>
                    <th>Serial No.</th>
                    <th>Blood group</th>
                    <th>Blood Units</th>
                    <th>Coin Value</th>
                    <th>Last ModifiedOn</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}" varStatus="loop">
                    <tr>
                        <td>${loop.index + 1}</td>
                        <td>${user.bloodGroup}</td>
                        <td>${user.bloodUnit}</td>
                        <td>${user.coinValue}</td>
                        <td>${user.lastModifiedOn}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
         <script>
                function goBack() {
                             window.history.back();
                }
         </script>
</body>
</html>
