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
            border: 2px solid black
        }
         p{
            font-size: 35px;
            font-weight: bold;
            color: #333333;
        }
        h3{
            font-size: 35px;
            font-weight: bold;
            color: #333333;
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
     <button class="back-button" onclick="goBack()">
                 <span class="arrow">&larr;</span>
     </button>
     <script>
             function goBack() {
                 window.history.back();
             }
     </script>
    <c:choose>
            <c:when test="${notExist}">
                <p>Username does not exist</p>
            </c:when>
            <c:otherwise>
              <h3  >Attempts remain-: ${3-remainingAttempt}</br>You have put wrong password.</br>Please enter correct password!!</h3>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
