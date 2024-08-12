<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Blood Donation Form</title>
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

        .form-container {
            background-color: #fff;
            padding: 30px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 450px;
            text-align: center;
            border: solid black 2px;
            position: relative; /* Added position relative for positioning the back button */
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

        select, input[type="number"], input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
            box-sizing: border-box;
        }

        select:focus, input[type="number"]:focus, input[type="text"]:focus {
            border-color: #007bff;
            outline: none;
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

        /* Styling for the back button */
        .back-button {
            position: absolute;
            top: 10px;
            left: 10px;
            background-color: #007bff;
            color: white;
            border: none;
            border-radius: 50%; /* Make it circular */
            padding: 10px;
            cursor: pointer;
            font-size: 18px; /* Increased font size for better visibility */
            line-height: 0; /* Ensures the arrow is vertically centered */
        }

        .back-button:hover {
            background-color: #0056b3;
        }

        /* Arrow style */
        .back-button::before {
            content: "\2190"; /* Unicode character for left arrow */
        }
    </style>
</head>
<body>
    <div class="form-container">
        <button class="back-button" onclick="goBack()"></button>
        <h2>Blood Request Form</h2>
        <form id="bloodForm" action="requestsubmit" method="post">
            <div class="input-group">
                <label for="type">Type</label>
                <select id="type" name="type">
                    <option value="">Select Type</option>
                    <option value="donate">Donate</option>
                    <option value="receive">Receive</option>
                </select>
            </div>
            <div id="bloodGroupInput" class="input-group" style="display: none;">
                <label for="bloodGroup">Blood Group</label>
                <select id="bloodGroup" name="bloodGroup">
                    <option value="">Select Blood Group</option>
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
                <label for="quantity">Quantity of Blood</label>
                <input type="number" id="quantity" name="quantity" min="1" max="100" required>
            </div>
            <button type="submit" class="btn">Submit</button>
        </form>
    </div>

    <script>
        document.getElementById("type").addEventListener("change", function() {
            var bloodGroupInput = document.getElementById("bloodGroupInput");
            if (this.value === "receive") {
                bloodGroupInput.style.display = "block";
                document.getElementById("bloodGroup").setAttribute("required", "required");
            } else {
                bloodGroupInput.style.display = "none";
                document.getElementById("bloodGroup").removeAttribute("required");
            }
        });

        function goBack() {
            window.history.back();
        }
    </script>
</body>
</html>
