<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Password</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .container {
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
        }
        .container h2 {
            margin-top: 0;
            text-align: center;
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
        }
        .form-group input {
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .form-group input:focus {
            border-color: #007BFF;
        }
        .form-group button {
            width: 100%;
            padding: 10px;
            background-color: #007BFF;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .form-group button:hover {
            background-color: #0056b3;
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
    <h2 style="color: green;">You have successfully login!!</h2>
    <div class="container">
        <h2>Update Password</h2>
        <button class="back-button" onclick="goBack()">
                                                 <span class="arrow">&larr;</span>
                                </button>
                                 <script>
                                             function goBack() {
                                                 window.history.back();
                                             }
                                 </script>
        <form action="/update" method="POST">
            <div class="form-group">

                <label for="user-name">User Name</label>
                <input readonly type="text" name="userName" value="${userName}">
            </div><div class="form-group">
                <label for="current-password">Current Password</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-group">
                <label for="new-password">New Password</label>
                <input type="password" id="updatePassword" name="updatePassword" required>
            </div>
            <div class="form-group">
                <label for="confirm-password">Confirm New Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required>
            </div>
            <div class="form-group">
                <button type="submit">Update Password</button>
            </div>
        </form>
    </div>
</body>
</html>
