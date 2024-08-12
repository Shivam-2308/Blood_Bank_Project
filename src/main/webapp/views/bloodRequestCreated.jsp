<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Request Details</title>
<style>
    table {
        width: 100%;
        border-collapse: collapse;
    }
    th, td {
        border: 1px solid black;
        padding: 8px;
        text-align: center;
    }
    th {
        background-color: #f2f2f2;
    }
    .back-button {
        position: absolute;
        top: 10px;
        left: 10px;
        text-decoration: none;
        color: white;
        font-size: 18px;
        display: flex;
        align-items: center;
        justify-content: center;
        width: 40px;
        height: 40px;
        background-color: blue;
        border-radius: 50%;
    }
    .back-button svg {
        fill: white;
    }
    .filter-container {
        margin: 20px 0;
        text-align: center;
    }
    .filter-container select, .filter-container input {
        padding: 8px;
        font-size: 16px;
        margin-top: 10px;
    }
    .filter-container button {
        padding: 10px 20px;
        font-size: 16px;
        margin-top: 10px;
        background-color: blue;
        color: white;
        border: none;
        cursor: pointer;
    }
    .filter-container button:hover {
        background-color: darkblue;
    }
    .status-button {
        padding: 5px 10px;
        font-size: 14px;
        margin: 2px;
        border: none;
        cursor: pointer;
    }
    .accept-button {
        background-color: green;
        color: white;
    }
    .reject-button {
        background-color: red;
        color: white;
    }
</style>
<script>
    function applyFilter() {
        const filter = document.getElementById('filterBy').value;
        const inputContainer = document.getElementById('input-container');

        inputContainer.innerHTML = '';

        if (filter !== 'none') {
            if (filter === 'byDate') {
                const startDateField = document.createElement('input');
                startDateField.setAttribute('type', 'date');
                startDateField.setAttribute('name', 'startDate');
                startDateField.setAttribute('placeholder', 'Start Date');

                const endDateField = document.createElement('input');
                endDateField.setAttribute('type', 'date');
                endDateField.setAttribute('name', 'endDate');
                endDateField.setAttribute('placeholder', 'End Date');

                inputContainer.appendChild(startDateField);
                inputContainer.appendChild(endDateField);
            } else {
                const inputField = document.createElement('input');
                inputField.setAttribute('type', 'text');

                switch (filter) {
                    case 'byStatus':
                        inputField.setAttribute('name', 'status');
                        inputField.setAttribute('placeholder', 'Enter Status');
                        break;
                    case 'byAgent':
                        inputField.setAttribute('name', 'agent');
                        inputField.setAttribute('placeholder', 'Enter Agent');
                        break;
                    case 'byUsername':
                        inputField.setAttribute('name', 'username');
                        inputField.setAttribute('placeholder', 'Enter Username');
                        break;
                }
                inputContainer.appendChild(inputField);
            }
        }
    }
</script>
</head>
<body>
<a href="javascript:history.back()" class="back-button">
    <svg xmlns="http://www.w3.org/2000/svg" height="24" viewBox="0 0 24 24" width="24">
        <path d="M0 0h24v24H0z" fill="none"/>
        <path d="M19 11H7.83l5.59-5.59L12 4l-8 8 8 8 1.41-1.41L7.83 13H19v-2z"/>
    </svg>
</a>

<form action="/bloodRequestFilter" method="GET" class="filter-container">
    <label for="filterBy">Filter by: </label>
    <select id="filterBy" name="filterBy" onchange="applyFilter()">
        <option value="none">Select Filter</option>
        <option value="byStatus">Status</option>
        <option value="byDate">Date</option>
        <option value="byAgent">Agent</option>
        <option value="byUsername">Username</option>
    </select>
    <div id="input-container"></div>
    <button type="submit">Submit</button>
</form>
<h2 style="color: red">${message}</h2>
<h2 style="text-align: center;">Blood Request Details</h2>
<table>
  <tr>
    <th>Request Id</th>
    <th>Created By</th>
    <th>Agent</th>
    <th>Blood Group</th>
    <th>Quantity</th>
    <th>Type</th>
    <th>Created At</th>
    <th>DOB</th>
    <th>Age</th>
    <th>Status</th>
  </tr>
  <c:forEach var="request" items="${bloodRequestList}" varStatus="loop">
    <tr>
      <td>${request.id}</td>
      <td>${request.createdBy}</td>
      <td>${request.agent}</td>
      <td>${request.bloodGroup}</td>
      <td>${request.quantity}</td>
      <td>${request.type}</td>
      <td>${request.createdOn}</td>
      <td>${request.dob}</td>
      <td>${request.age}</td>
      <td>
          <c:choose>
              <c:when test="${role eq 'agent'}">
                  <span>${request.status}</span>
              </c:when>
              <c:otherwise>
                  <c:choose>
                      <c:when test="${request.status eq 'pending'}">
                          <div class="btn-group">
                              <form action="/accept" method="GET">
                                  <input type="hidden" name="status" value="accepted">
                                  <input type="hidden" name="userId" value="${request.id}">
                                  <input type="hidden" name="userName" value="${request.createdBy}">
                                  <input type="hidden" name="userName" value="${request.type}">
                                  <button type="submit" class="btn btn-success">Accept</button>
                              </form>
                              <form action="/reject" method="GET">
                                  <input type="hidden" name="status" value="rejected">
                                  <input type="hidden" name="userId" value="${request.id}">
                                  <input type="hidden" name="userName" value="${request.createdBy}">
                                  <button type="submit" class="btn btn-danger">Reject</button>
                              </form>
                          </div>
                      </c:when>
                      <c:when test="${request.status eq 'rejected'}">
                          <span class="btn btn-dark">Rejected</span>
                      </c:when>
                      <c:otherwise>
                          <span class="btn btn-primary">Accepted</span>
                      </c:otherwise>
                  </c:choose>
              </c:otherwise>
          </c:choose>
      </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
