<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<link rel="stylesheet" type="text/css" href="CSS/BookDisplayMenu.css">
	
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Books</title>
</head>
<body>
    <h3>View Books Menu</h3>
    <form action="BookDisplayS" method="get">
        <button name="choiceWhileBookSearch" value="1">Fiction</button><br>
        <button name="choiceWhileBookSearch" value="2">Non-Fiction</button><br>
        <button name="choiceWhileBookSearch" value="3">Computer-Science</button><br>
        <button name="choiceWhileBookSearch" value="4">Information Technology</button><br>
        <button name="choiceWhileBookSearch" value="5">Electronics and Communication</button><br> 
        <button name="choiceWhileBookSearch" value="6">Custom Search</button><br>   
    </form>
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