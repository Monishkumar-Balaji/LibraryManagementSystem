<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet" type="text/css" href="CSS/EditProfile.css">
	
    <title>Edit Profile</title>
</head>
<body>
    <h1>Edit Profile</h1>

    <%
        // Determine the previous page based on user type
        String action = "";
        if ("student".equals(session.getAttribute("userType"))) {
            action = "StudentS";
        } else if ("faculty".equals(session.getAttribute("userType"))) {
            action = "FacultyS";
        }
    %>

    
    <form action="<%= action %>" method="post">
    	<input type="hidden" name="choice" value="4">
        <label for="newName">New Name:</label><br>
        <input type="text" id="newName" name="newName" placeholder="<%= session.getAttribute("name") %>"><br><br>

        <label for="newPassword">New Password:</label><br>
        <input type="password" id="newPassword" name="newPassword"><br><br>

        <label for="newEmail">New Email:</label><br>
        <input type="email" id="newEmail" name="newEmail" placeholder="<%= session.getAttribute("email") %>"><br><br>

        <label for="newPhoneNo">New Phone Number:</label><br>
        <input type="text" id="newPhoneNo" name="newPhoneNo" placeholder="<%= session.getAttribute("phoneNo") %>"><br><br>
	
		<label for="outStanding">OutStanding : ₹ <%= session.getAttribute("outstanding") %></label>
        <button type="submit">Update Profile</button>
    </form>

    <br>
    <%
        // Determine the previous page based on user type
        String previousPage = "";
        if ("student".equals(session.getAttribute("userType"))) {
            previousPage = "StudentMenu.jsp";
        } else if ("faculty".equals(session.getAttribute("userType"))) {
            previousPage = "FacultyMenu.jsp";
        }
    %>

    <!-- Use the resolved previousPage variable -->
    <br>
    <a href="<%= previousPage %>">Go Back</a>
</body>
</html>
