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
            border: solid black 2px;
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
        input[type="password"] {
            width: calc(100% - 10px);
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        input[type="text"]:focus,
        input[type="email"]:focus,
        input[type="password"]:focus {
            border-color: #007bff;
        }

        .btn {
            width: 30%;
            padding: 10px;
            border: none;
            border-radius: 5px;
            background-color: #007bff;
            color: blue;
            font-size: 16px;
            cursor: pointer;
        }

         .btn:hover {
                    background-color: #0056b3;
                }

                a {
                    text-decoration: none;
                    color: inherit;
                }

                a.signup-link {
                    color: #007bff;
                }

                a.signup-link:hover {
                    color: #0056b3;
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
                .logout-btn {
                    margin-top: 20px;
                    width: 100%;
                    padding: 10px;
                    border: none;
                    border-radius: 5px;
                    background-color: #dc3545;
                    color: white;
                    font-size: 16px;
                    cursor: pointer;
                }

                .logout-btn:hover {
                    background-color: #c82333;
                }

    </style>
</head>
<body>
    <div class="signup-container">
        <h2>Welcome To Agent Dashboard</h2>

        <button class="back-button" onclick="goBack()">
                                 <span class="arrow">&larr;</span>
         </button>
        <button><a href="/signup" class="signup-link" style=none>Create End User</a></button>
        <button><a href="/allusers" class="signup-link" style=none>Users List</a></button>
        <form action="register" method="post">
            <div class="input-group">
                <label for="username">Username</label>
                ${dto.userName}
            </div>
            <div class="input-group">
                <label for="name">Name</label>
                ${dto.name}
            </div>
            <div class="input-group">
                <label for="role">Role</label>
                ${dto.role}
            </div>
            <div class="input-group">
                <label for="bloodGroup">Blood Group</label>
                ${dto.bloodGroup}
            </div>
            <div class="input-group">
                <label for="address">Address</label>
                ${dto.address}
            </div>
            <div class="input-group">
                <label for="dob">DOB</label>
                ${dto.dob}
            </div>
            <button class="logout-btn"><a href="/logout" class="signup-link" style=none>Logout</a></button>
        </form>
    </div>
    <script>
          function goBack() {
                 window.history.back();
          }
    </script>
</body>
</html>
