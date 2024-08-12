<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .signup-container {
            background-color: #fff;
            padding: 20px 25px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
            text-align: center;
        }

        h2 {
            margin-bottom: 20px;
            color: #333;
        }

        .input-group {
            margin-bottom: 15px;
            text-align: left;
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
            color: #555;
        }

        input[type="text"],
        input[type="email"],
        input[type="password"],
        input[type="date"]{
            width: calc(100% - 10px);
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        input[type="text"]:focus,
        input[type="email"]:focus,
        input[type="password"]:focus,
        input[type="date"]:focus {
            border-color: #007bff;
        }
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }
        .btn {
            width: 100%;
            padding: 10px;
            border: none;
            border-radius: 5px;
            background-color: #007bff;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }

        .btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="signup-container">
        <h2>Sign Up</h2>
        <form action="/register" method="post">
            <div class="input-group">
                <label for="username">Username</label>
                <input type="text" name="userName">
            </div>
            <div class="input-group">
                <label for="name">Name</label>
                <input type="text" name="name" required>
            </div>
            <div class="input-group">
                <label for="bloodGroup">Blood Group</label>
                <select id="bloodGroup" name="bloodGroup">
                    <option value="">Select...</option>
                    <option value="A+">A+</option>
                    <option value="A-">A-</option>
                    <option value="B+">B+</option>
                    <option value="B-">B-</option>
                    <option value="AB+">AB+</option>
                    <option value="AB-">AB-</option>
                    <option value="O+">O+</option>
                    <option value="O-">O-</option>
                </select>
            </div>
            <div class="input-group">
                <label for="address">Address</label>
                <input type="text" name="address" required>
            </div>
            <div class="input-group">
                <label for="dob">DOB</label>
                <input type="date" name="dob">
            </div>

            <c:choose>
                <c:when test="${role == 'admin'}">
                    <div class="input-group">
                        <label for="commission">Commission</label>
                        <input type="text" id="commission" name="commission" required>
                    </div>
                    <!-- Admin-specific hidden fields -->
                    <input type="hidden" id="password" name="password" value="abc">
                    <input type="hidden" id="confirm-password" name="confirm-password" value="abc">
                </c:when>
                <c:when test="${role == 'agent'}">

                                    <!-- Agent-specific hidden fields -->
                                    <input type="hidden" id="password" name="password" value="abc">
                                    <input type="hidden" id="confirm-password" name="confirm-password" value="abc">
                                </c:when>
                <c:otherwise>
                    <div class="input-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" required>
                    </div>
                    <div class="input-group">
                        <label for="confirm-password">Confirm Password</label>
                        <input type="password" id="confirm-password" name="confirm-password" required>
                    </div>
                </c:otherwise>
            </c:choose>

            <button type="submit" class="btn">Sign Up</button>
        </form>
    </div>
</body>
</html>
